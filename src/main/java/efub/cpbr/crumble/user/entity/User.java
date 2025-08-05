package efub.cpbr.crumble.user.entity;

import efub.cpbr.crumble.community.comment.domain.Comment;
import efub.cpbr.crumble.global.domain.BaseEntity;
import efub.cpbr.crumble.shop.font.entity.UserFont;
import efub.cpbr.crumble.shop.item.entity.UserItem;
import efub.cpbr.crumble.shop.paper.entity.UserPaper;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @Column(nullable = false, unique = true)
    private String username; // 로그인 아이디

    @Column(nullable = false)
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;

    @Email
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private int point = 0;

    @Column(nullable = false)
    private boolean isActive = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    private int profileImageId;

    public void addPoint(Long point) {
        this.point += point;
    }

    @Builder
    public User(Long userId, String username, String password, String email, String nickname,
                int point, boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt, RoleType role, int profileImageId) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.point = (point == 0) ? 0 : point; // 기본값 처리
        this.isActive = isActive;
        this.role = (role == null) ? RoleType.USER : role; // 기본 역할 처리
        this.profileImageId = profileImageId;
    }

    /*public void deactivate() { // 사용자 탈퇴
        this.isActive = false;
    }*/

    // 댓글 작성자
    @OneToMany(mappedBy = "commentator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    // 보유 폰트
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserFont> userFonts = new ArrayList<>();

    // 보유 아이템
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserItem> userItems = new ArrayList<>();

    // 보유 테마
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserPaper> userPapers = new ArrayList<>();

    // 유저 활동
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserStat userStat;

}
