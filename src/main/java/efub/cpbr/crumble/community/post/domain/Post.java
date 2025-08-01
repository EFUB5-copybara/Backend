package efub.cpbr.crumble.community.post.domain;

import efub.cpbr.crumble.answer.entity.Answer;
import efub.cpbr.crumble.community.bookmark.domain.Bookmark;
import efub.cpbr.crumble.community.comment.domain.Comment;
import efub.cpbr.crumble.community.like.domain.Like;
import efub.cpbr.crumble.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE) // Answer 수정 시 Post에 전파
    @JoinColumn(name = "answer_id", unique = true) // UNIQUE 제약조건
    private Answer answer;

    private String content;

    private Long viewCount = 0L;
    private Long likeCount = 0L;
    private Long commentCount = 0L;
    private Long bookmarkCount = 0L;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likeList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarkList = new ArrayList<>();

    public void updateContentFromAnswer() {
        if (this.answer != null) {
            this.content = answer.getContent();
        }
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void increaseCommentCount() {
        this.commentCount++;
    }

    public void increaseBookmarkCount() {
        this.bookmarkCount++;
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

    public void decreaseLikeCount() {
        if (this.likeCount > 0) this.likeCount--;
    }

    public void decreaseCommentCount() {
        if (this.commentCount > 0) this.commentCount--;
    }

    public void decreaseBookmarkCount() {
        if (this.bookmarkCount > 0) this.bookmarkCount--;
    }

}
