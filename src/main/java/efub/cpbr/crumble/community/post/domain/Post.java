package efub.cpbr.crumble.community.post.domain;

import efub.cpbr.crumble.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /*
    @OneToOne(cascade = CascadeType.MERGE) // Answer 수정 시 Post에 전파
    @JoinColumn(name = "answer_id", unique = true) // UNIQUE 제약조건
    private Answer answer; */

    private String content;

    private Long viewCount = 0L;
    private Long likeCount = 0L;
    private Long commentCount = 0L;
    private Long bookmarkCount = 0L;
    /*
    public void updateContentFromAnswer() {
        if (this.answer != null) {
            this.content = answer.getContent();
        }
    }*/
}
