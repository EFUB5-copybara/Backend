package efub.cpbr.crumble.user.entity;

import efub.cpbr.crumble.community.comment.domain.Comment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @OneToMany(mappedBy = "commentator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    private String username;

    private Long point;

    public void addPoint(Long point) {
        if (this.point == null) {
            this.point = 0L;
        }
        this.point += point;
    }
}
