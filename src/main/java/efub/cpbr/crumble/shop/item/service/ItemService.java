package efub.cpbr.crumble.shop.item.service;

import efub.cpbr.crumble.global.exception.CustomException;
import efub.cpbr.crumble.global.exception.ErrorCode;
import efub.cpbr.crumble.shop.item.dto.ItemDetailResponseDto;
import efub.cpbr.crumble.shop.item.dto.ItemResponseDto;
import efub.cpbr.crumble.shop.item.entity.Item;
import efub.cpbr.crumble.shop.item.entity.UserItem;
import efub.cpbr.crumble.shop.item.repository.ItemRepository;
import efub.cpbr.crumble.shop.item.repository.UserItemRepository;
import efub.cpbr.crumble.user.entity.User;
import efub.cpbr.crumble.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    // 아이템 구매
    @Transactional
    public void purchaseItem(User user, Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));

        // 해당 유저가 이미 아이템을 갖고 있으면 수량 +1
        Optional<UserItem> optionalUserItem = userItemRepository.findByUserAndItem(user, item);

        if (optionalUserItem.isPresent()) {
            UserItem userItem = optionalUserItem.get();
            userItem.increaseQuantity(); // 수량 +1
        } else { // 갖고 있지 않으면 수량 1로 구매 생성
            UserItem userItem = new UserItem(user, item, 1);  // 수량 1로 생성자 초기화
            userItemRepository.save(userItem);
        }

        user.addPoint(-item.getPrice());
        userRepository.save(user);
    }
}

