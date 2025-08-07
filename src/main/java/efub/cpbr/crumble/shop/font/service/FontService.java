package efub.cpbr.crumble.shop.font.service;

import efub.cpbr.crumble.global.exception.CustomException;
import efub.cpbr.crumble.global.exception.ErrorCode;
import efub.cpbr.crumble.shop.font.dto.response.FontResponseDto;
import efub.cpbr.crumble.shop.font.entity.Font;
import efub.cpbr.crumble.shop.font.entity.UserFont;
import efub.cpbr.crumble.shop.font.repository.FontRepository;
import efub.cpbr.crumble.shop.font.repository.UserFontRepository;
import efub.cpbr.crumble.user.entity.User;
import efub.cpbr.crumble.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FontService {
    private final FontRepository fontRepository;
    private final UserRepository userRepository;
    private final UserFontRepository userFontRepository;

    // 폰트 리스트 조회
    @Transactional(readOnly = true)
    public List<FontResponseDto> getFonts(User user) {
        List<Font> fonts = fontRepository.findAll();
        List<UserFont> userFonts = userFontRepository.findAllByUser(user);

        Set<Long> ownedFontIds = userFonts.stream()
                .map(uf -> uf.getFont().getId())
                .collect(Collectors.toSet());

        Set<Long> selectedFontIds = userFonts.stream()
                .filter(UserFont::isSelected)
                .map(uf -> uf.getFont().getId())
                .collect(Collectors.toSet());

        return fonts.stream()
                .map(font -> {
                    boolean owned = ownedFontIds.contains(font.getId());
                    boolean selected = selectedFontIds.contains(font.getId());
                    return FontResponseDto.from(font, owned, selected);
                })
                .toList();
    }

    // 폰트 상세 조회
    @Transactional(readOnly = true)
    public FontResponseDto getFontDetail(User user, Long fontId) {
        Font font = fontRepository.findById(fontId)
                .orElseThrow(() -> new CustomException(ErrorCode.FONT_NOT_FOUND));

        Optional<UserFont> userFontOpt = userFontRepository.findByUserAndFont(user, font);
        boolean isOwned = userFontOpt.isPresent();
        boolean isSelected = userFontOpt.map(UserFont::isSelected).orElse(false);

        return FontResponseDto.from(font, isOwned, isSelected);
    }

    // 폰트 구매
    @Transactional
    public void purchaseFont(User user, Long fontId) {
        Font font = fontRepository.findById(fontId)
                .orElseThrow(() -> new CustomException(ErrorCode.FONT_NOT_FOUND));

        if (userFontRepository.existsByUserAndFont(user, font)) {
            throw new CustomException(ErrorCode.ALREADY_PURCHASED);
        }

        if (user.getPoint() < font.getPrice()) {
            throw new CustomException(ErrorCode.INSUFFICIENT_POINTS);
        }

        user.addPoint(-font.getPrice());
        userRepository.save(user);
        userFontRepository.save(new UserFont(user, font, false));
    }

    // 폰트 적용
    @Transactional
    public void applyFont(User user, Long fontId) {
        Font font = fontRepository.findById(fontId)
                .orElseThrow(() -> new CustomException(ErrorCode.FONT_NOT_FOUND));

        UserFont targetFont = userFontRepository.findByUserAndFont(user, font)
                .orElseThrow(() -> new CustomException(ErrorCode.FONT_NOT_OWNED));

        // 전체 선택 해제
        List<UserFont> userFonts = userFontRepository.findAllByUser(user);
        for (UserFont uf : userFonts) {
            uf.setSelected(false);
        }

        targetFont.setSelected(true);
    }
}
