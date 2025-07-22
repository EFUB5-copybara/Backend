package efub.cpbr.crumble.community.bookmark.domain;

import efub.cpbr.crumble.community.post.domain.Post;
import efub.cpbr.crumble.global.domain.BaseEntity;
import efub.cpbr.crumble.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User bookmarker;

    public Bookmark(Post post, User bookmarker) {
        this.post = post;
        this.bookmarker = bookmarker;
    }
}
