package efub.cpbr.crumble.shop.item.service;

import efub.cpbr.crumble.global.exception.CustomException;
import efub.cpbr.crumble.global.exception.ErrorCode;
import efub.cpbr.crumble.shop.item.dto.response.ItemDetailResponseDto;
import efub.cpbr.crumble.shop.item.dto.response.ItemResponseDto;
import efub.cpbr.crumble.shop.item.entity.Item;
import efub.cpbr.crumble.shop.item.entity.UserItem;
import efub.cpbr.crumble.shop.item.repository.ItemRepository;
import efub.cpbr.crumble.shop.item.repository.UserItemRepository;
import efub.cpbr.crumble.user.entity.User;
import efub.cpbr.crumble.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final UserItemRepository userItemRepository;

    // 아이템 목록 조회
    @Transactional(readOnly = true)
    public List<ItemResponseDto> getAllItems() {
        return itemRepository.findAll().stream()
                .map(ItemResponseDto::from)
                .toList();
    }

    // 아이템 상세 조회
    @Transactional(readOnly = true)
    public ItemDetailResponseDto getItemDetail(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));
        return ItemDetailResponseDto.from(item);
    }

    @Transactional
    public void purchaseItem(Long userId, Long itemId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));

        if (user.getPoint() < item.getPrice()) {
            throw new CustomException(ErrorCode.INSUFFICIENT_POINTS);
        }

        // 포인트 차감 및 아이템 추가
        user.addPoint(-item.getPrice());
        userItemRepository.save(new UserItem(user, item));
    }
}

