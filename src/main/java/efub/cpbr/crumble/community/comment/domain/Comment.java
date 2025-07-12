package efub.cpbr.crumble.community.comment.domain;

import efub.cpbr.crumble.community.post.domain.Post;
import efub.cpbr.crumble.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", updatable = false)
//    private User user;

    // 회원에 추가되어야되는 코드
//    @OneToMany(mappedBy = "commentator", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Comment> CommentList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "post_id", updatable = false)
    private Post post;

    @Builder
    public Comment(String content, Post post) {
        this.content = content;
        // this.commentator = commentator
        this.post = post;
    }
}
