package efub.cpbr.crumble.answer.entity;

import efub.cpbr.crumble.community.post.domain.Post;
import efub.cpbr.crumble.global.domain.BaseEntity;
import efub.cpbr.crumble.question.entity.Question;
import efub.cpbr.crumble.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(toBuilder = true)
public class Answer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="answer_id")
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Boolean isPublic ;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @OneToOne(mappedBy = "answer", fetch = FetchType.LAZY)
    private Post post;

    @Column(nullable = false)
    private int likesCount; // 좋아요 수

    @Column(nullable = false)
    private int commentsCount; // 댓글 수

}
