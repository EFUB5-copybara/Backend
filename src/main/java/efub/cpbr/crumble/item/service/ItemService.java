package efub.cpbr.crumble.item.service;

import efub.cpbr.crumble.answer.entity.Answer;
import efub.cpbr.crumble.answer.repository.AnswerRepository;
import efub.cpbr.crumble.calendar.repository.CalendarRepository;
import efub.cpbr.crumble.global.exception.CustomException;
import efub.cpbr.crumble.global.exception.ErrorCode;
import efub.cpbr.crumble.item.entity.Item;
import efub.cpbr.crumble.item.entity.ItemType;
import efub.cpbr.crumble.item.entity.UserItem;
import efub.cpbr.crumble.item.repository.ItemRepository;
import efub.cpbr.crumble.item.repository.UserItemRepository;
import efub.cpbr.crumble.user.entity.User;
import efub.cpbr.crumble.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static efub.cpbr.crumble.global.exception.ErrorCode.ITEM_NOT_FOUND;
import static efub.cpbr.crumble.global.exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final UserItemRepository userItemRepository;
    private final ShieldService shieldService;

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

    private void useEraser(User user) {
        // 답변 수정 가능
    }

    private void useKey(User user) {
        // 다른 사람의 답변 보기
    }

}
