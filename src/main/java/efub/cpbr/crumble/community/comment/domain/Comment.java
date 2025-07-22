package efub.cpbr.crumble.community.comment.domain;

import efub.cpbr.crumble.community.post.domain.Post;
import efub.cpbr.crumble.global.domain.BaseEntity;
import efub.cpbr.crumble.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    private User commentator;

    @ManyToOne
    @JoinColumn(name = "post_id", updatable = false)
    private Post post;

    @Builder
    public Comment(String content, User commentator, Post post) {
        this.content = content;
        this.commentator = commentator;
        this.post = post;
    }
}
