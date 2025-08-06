package efub.cpbr.crumble.mypage.service;

import efub.cpbr.crumble.answer.repository.AnswerRepository;
import efub.cpbr.crumble.community.bookmark.domain.Bookmark;
import efub.cpbr.crumble.community.bookmark.repository.BookmarkRepository;
import efub.cpbr.crumble.community.comment.repository.CommentRepository;
import efub.cpbr.crumble.community.like.repository.LikeRepository;
import efub.cpbr.crumble.global.exception.CustomException;
import efub.cpbr.crumble.global.exception.ErrorCode;
import efub.cpbr.crumble.mypage.dto.*;
import efub.cpbr.crumble.user.entity.User;
import efub.cpbr.crumble.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final AnswerRepository answerRepository;
    private final BookmarkRepository bookmarkRepository;

    // 마이페이지 초기 화면 조회 로직
    @Transactional(readOnly = true)
    public MyPageInfoDto getMyPageInfo(Long memberId) {
        // 사용자 정보 조회
        User user = userRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        //통계 정보 조회
        Long totalLikes = likeRepository.countByLiker_UserId(memberId);
        Long totalComments = commentRepository.countByCommentator_UserId(memberId);
        Long totalWrittenAnswers = answerRepository.countByUser_UserId(memberId);

        // DTO 빌드 및 반환
        return MyPageInfoDto.builder()
                .userName(user.getNickname())       // User 엔티티의 nickname을 userName으로 사용
                .email(user.getEmail())
                .profileImageIndex(user.getProfileImageIndex())
                .totalLikes(totalLikes)
                .totalComments(totalComments)
                .totalWrittenAnswers(totalWrittenAnswers)
                .build();
    }

    // 내 정보 화면 조회 로직
    @Transactional(readOnly = true)
    public MyInfoResponse getMyProfileInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return MyInfoResponse.from(user); // User 엔티티를 DTO로 변환
    }

    // 마이페이지 프로필 수정 로직 (닉네임, 이메일, 프사 중에서 요청한 것만 업데이트. +중복검사)
    @Transactional
    public Map<String, Object> updateMyInfo(Long userId, MyPageUpdateRequest request) { // 반환 타입을 Map<String, Object>로 변경
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Map<String, Object> updatedFields = new HashMap<>(); // Object 타입으로 변경하여 다양한 값 저장 가능

        // 유저 이름 업데이트
        if (request.getNickname() != null && !request.getNickname().isEmpty()) {
            if (!user.getNickname().equals(request.getNickname())) { // 현재와 달리 수정된 경우
                Optional<User> existingUserWithNickname = userRepository.findByUsername(request.getNickname());
                if (existingUserWithNickname.isPresent() && !existingUserWithNickname.get().getUserId().equals(userId)) {
                    throw new CustomException(ErrorCode.DUPLICATE_NICKNAME); // 닉네임 중복
                }
                user.updateNickname(request.getNickname());
                updatedFields.put("nickname", request.getNickname());
            }
        }

        // 이메일 업데이트
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            if (!user.getEmail().equals(request.getEmail())) {
                Optional<User> existingUserWithEmail = userRepository.findByEmail(request.getEmail());
                if (existingUserWithEmail.isPresent() && !existingUserWithEmail.get().getUserId().equals(userId)) {
                    throw new CustomException(ErrorCode.DUPLICATE_EMAIL); // 이메일 중복
                }
                user.updateEmail(request.getEmail());
                updatedFields.put("email", request.getEmail());
            }
        }

        // 프로필 사진 인덱스 업데이트
        if (request.getProfileImageIndex() != null) {
            if (user.getProfileImageIndex() != request.getProfileImageIndex()) { // 현재와 달리 수정된 경우
                user.updateProfileImageIndex(request.getProfileImageIndex());
                updatedFields.put("profileImageIndex", request.getProfileImageIndex());
            }
        }
        return updatedFields;
    }

    // 내 기록 - 작성한 답변, 글자수 조회 로직
    @Transactional(readOnly = true)
    public MyRecordsResponse getMyRecords(Long userId) {
        // 사용자 정보는 이미 인증되었으므로 별도 조회는 생략 가능하지만,
        // 혹시 모를 에러 방지를 위해 Optional로 감싸는 것이 좋습니다.
        userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 작성한 답변 총 개수 조회
        Long totalAnswersCount = answerRepository.countByUser_UserId(userId);

        // 작성한 답변들의 글자수 총합 조회
        Long totalCharacterCount = answerRepository.sumCharacterCountByUserId(userId);

        return MyRecordsResponse.builder()
                .totalAnswersCount(totalAnswersCount)
                .totalCharacterCount(totalCharacterCount)
                .build();
    }

    // 북마크한 게시물 리스트
    @Transactional(readOnly = true)
    public BookmarkedAnswersResponse getBookmarkedAnswers(Long userId, int page, int size, String sortBy) {
        userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Sort sort;
        if ("popular".equals(sortBy)) {
            // 정렬 기준을 'post'를 거쳐 'answer'의 'likesCount'로 수정
            sort = Sort.by(Sort.Direction.DESC, "post.answer.likesCount");
        } else {
            // 북마크 생성일 기준 최신순
            sort = Sort.by(Sort.Direction.DESC, "createdAt");
        }
        PageRequest pageable = PageRequest.of(page, size, sort);

        Page<Bookmark> bookmarkPage = bookmarkRepository.findByBookmarker_UserId(userId, pageable);

        // DTO 변환 로직 수정: bookmark -> post -> answer 순서로 접근
        Page<BookmarkedAnswerDto> bookmarkedDtoPage = bookmarkPage.map(bookmark -> BookmarkedAnswerDto.from(bookmark.getPost().getAnswer()));

        return BookmarkedAnswersResponse.builder()
                .totalCount(bookmarkPage.getTotalElements())
                .totalPages(bookmarkPage.getTotalPages())
                .currentPage(bookmarkPage.getNumber())
                .pageSize(bookmarkPage.getSize())
                .sortBy(sortBy)
                .bookmarkedAnswers(bookmarkedDtoPage.getContent())
                .build();
    }
}
