package efub.cpbr.crumble.community.user.dto.response;

import efub.cpbr.crumble.answer.entity.Answer;
import efub.cpbr.crumble.question.entity.Question;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DiaryPreviewDto(Long diaryId,
                              Question question,
                              String answer,
                              Long likeCount,
                              Long commentCount,
                              LocalDateTime createdAt,
                              boolean isPublic) {
    public static DiaryPreviewDto from(Answer diary) {
        return DiaryPreviewDto.builder()
                .diaryId(diary.getId())
                .question(diary.getQuestion())
                .answer(diary.getContent())
                .likeCount(diary.getPost().getLikeCount())
                .commentCount(diary.getPost().getCommentCount())
                .createdAt(diary.getCreatedAt())
                .isPublic(diary.getIsPublic())
                .build();
    }
}
