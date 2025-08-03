package efub.cpbr.crumble.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userStatId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int totalAnswers;

    @Column(nullable = false)
    private int currentStreak;

    @Column(nullable = false)
    private int longestStreak;

    @Column(nullable = false)
    private int totalLikesReceived;

    @Column(nullable = false)
    private int totalCommentsReceived;

    public void increaseLikeCount() {
        this.totalLikesReceived++;
    }

    public void increaseCommentCount() {
        this.totalCommentsReceived++;
    }

    public void decreaseLikeCount() {
        if (this.totalLikesReceived > 0) this.totalLikesReceived--;
    }

    public void decreaseCommentCount() {
        if (this.totalCommentsReceived > 0) this.totalCommentsReceived--;
    }

}
