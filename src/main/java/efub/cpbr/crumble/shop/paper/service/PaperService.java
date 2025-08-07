package efub.cpbr.crumble.shop.paper.service;

import efub.cpbr.crumble.global.exception.CustomException;
import efub.cpbr.crumble.global.exception.ErrorCode;
import efub.cpbr.crumble.shop.paper.dto.response.PaperResponseDto;
import efub.cpbr.crumble.shop.paper.entity.Paper;
import efub.cpbr.crumble.shop.paper.entity.UserPaper;
import efub.cpbr.crumble.shop.paper.repository.PaperRepository;
import efub.cpbr.crumble.shop.paper.repository.UserPaperRepository;
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
public class PaperService {
    private final PaperRepository paperRepository;
    private final UserRepository userRepository;
    private final UserPaperRepository userPaperRepository;

    // 종이 테마 리스트 조회
    @Transactional(readOnly = true)
    public List<PaperResponseDto> getPapers(User user) {
        List<Paper> papers = paperRepository.findAll();
        List<UserPaper> userPapers = userPaperRepository.findAllByUser(user);

        Set<Long> ownedPaperIds = userPapers.stream()
                .map(uf -> uf.getPaper().getId())
                .collect(Collectors.toSet());

        Set<Long> selectedPaperIds = userPapers.stream()
                .filter(UserPaper::isSelected)
                .map(uf -> uf.getPaper().getId())
                .collect(Collectors.toSet());

        return papers.stream()
                .map(paper -> {
                    boolean owned = ownedPaperIds.contains(paper.getId());
                    boolean selected = selectedPaperIds.contains(paper.getId());
                    return PaperResponseDto.from(paper, owned, selected);
                })
                .toList();
    }

    // 종이 테마 상세 조회
    @Transactional(readOnly = true)
    public PaperResponseDto getPaperDetail(User user, Long paperId) {
        Paper paper = paperRepository.findById(paperId)
                .orElseThrow(() -> new CustomException(ErrorCode.PAPER_NOT_FOUND));

        Optional<UserPaper> userPaper = userPaperRepository.findByUserAndPaper(user, paper);
        boolean isOwned = userPaper.isPresent();
        boolean isSelected = userPaper.map(UserPaper::isSelected).orElse(false);

        return PaperResponseDto.from(paper, isOwned, isSelected);
    }

    // 종이 테마 구매
    @Transactional
    public void purchasePaper(User user, Long paperId) {
        Paper paper = paperRepository.findById(paperId)
                .orElseThrow(() -> new CustomException(ErrorCode.PAPER_NOT_FOUND));

        if (userPaperRepository.existsByUserAndPaper(user, paper)) {
            throw new CustomException(ErrorCode.ALREADY_PURCHASED);
        }

        if (user.getPoint() < paper.getPrice()) {
            throw new CustomException(ErrorCode.INSUFFICIENT_POINTS);
        }

        user.addPoint(-paper.getPrice());
        userRepository.save(user);
        userPaperRepository.save(new UserPaper(user, paper, false));
    }

    // 종이 테마 적용
    @Transactional
    public void applyPaper(User user, Long paperId) {
        Paper paper = paperRepository.findById(paperId)
                .orElseThrow(() -> new CustomException(ErrorCode.PAPER_NOT_FOUND));

        UserPaper targetPaper = userPaperRepository.findByUserAndPaper(user, paper)
                .orElseThrow(() -> new CustomException(ErrorCode.PAPER_NOT_FOUND));

        // 전체 선택 해제
        List<UserPaper> userPapers = userPaperRepository.findAllByUser(user);
        for (UserPaper uf : userPapers) {
            uf.setSelected(false);
        }

        targetPaper.setSelected(true);
    }
}
