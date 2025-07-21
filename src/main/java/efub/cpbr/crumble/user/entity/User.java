package efub.cpbr.crumble.user.entity;

import efub.cpbr.crumble.community.comment.domain.Comment;
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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username; // 로그인 아이디

    @Column(nullable = false)
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;

    @Email
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private int point = 0;

    @Column(nullable = false)
    private boolean isActive = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Builder
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.point = 0;
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }

    @OneToMany(mappedBy = "commentator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    public void addPoint(Long point) {
        this.point += point;
    }

    // 보유 폰트
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserFont> userFonts = new ArrayList<>();

    // 보유 아이템
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserItem> userItems = new ArrayList<>();

    // 보유 종이 테마
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserPaper> userPapers = new ArrayList<>();
}
