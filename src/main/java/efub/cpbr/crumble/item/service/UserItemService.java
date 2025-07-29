package efub.cpbr.crumble.item.service;

import efub.cpbr.crumble.global.exception.CustomException;
import efub.cpbr.crumble.global.exception.ErrorCode;
import efub.cpbr.crumble.item.dto.ItemCountResponse;
import efub.cpbr.crumble.item.entity.ItemType;
import efub.cpbr.crumble.item.entity.UserItem;
import efub.cpbr.crumble.item.repository.UserItemRepository;
import efub.cpbr.crumble.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserItemService {

    private final UserItemRepository userItemRepository;
    private final ShieldService shieldService;

    // 아이템 사용
    public void useItem(User user, ItemType type) {
        // 유저가 해당 타입의 아이템을 보유 중인지 확인
        UserItem userItem = userItemRepository.findByUserAndItemType(user, type)
                .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_OWNED));

        if (userItem.getQuantity() <= 0) {
            throw new CustomException(ErrorCode.ITEM_QUANTITY_ZERO);
        }

        // 아이템 기능 실행
        switch (type) {
            case ERASER -> useEraser(user);
            case SHIELD -> shieldService.execute(user);
            case KEY    -> useKey(user);
            default -> throw new CustomException(ErrorCode.UNSUPPORTED_ITEM_TYPE);
        }

        // 수량 감소 처리
        userItem.decreaseQuantity();

        if (userItem.getQuantity() == 0) {
            userItemRepository.delete(userItem);
        } else {
            userItemRepository.save(userItem);
        }
    }

    // 보유 아이템 개수 조회
    public List<ItemCountResponse> getUserItemCounts(User user) {
        // 유저가 가진 UserItem들 조회
        List<UserItem> userItems = userItemRepository.findByUser(user);

        // Map<ItemType, Integer> 으로 매핑
        Map<ItemType, Integer> ownedMap = userItems.stream()
                .collect(Collectors.toMap(
                        ui -> ui.getItem().getType(),
                        UserItem::getQuantity
                ));

        // 모든 ItemType에 대해 결과 만들기 (없으면 0으로)
        return Arrays.stream(ItemType.values())
                .map(type -> new ItemCountResponse(
                        type,
                        ownedMap.getOrDefault(type, 0)
                ))
                .toList();
    }


    private void useEraser(User user) {
        // 답변 수정 가능
    }

    private void useKey(User user) {
        // 다른 사람의 답변 보기
    }

}
