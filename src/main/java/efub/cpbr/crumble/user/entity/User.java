package efub.cpbr.crumble.user.entity;

import efub.cpbr.crumble.community.comment.domain.Comment;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails; // import 추가


@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
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
    public User(Long userId, String username, String password, String email, String nickname,
                int point, boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt, RoleType role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.point = (point == 0) ? 0 : point; // 기본값 처리
        this.isActive = isActive;
        this.createdAt = (createdAt == null) ? LocalDateTime.now() : createdAt; // 기본값 처리
        this.updatedAt = (updatedAt == null) ? LocalDateTime.now() : updatedAt; // 기본값 처리
        this.role = (role == null) ? RoleType.USER : role; // 기본 역할 처리
    }

    /*public void deactivate() { // 사용자 탈퇴
        this.isActive = false;
    }*/

    @OneToMany(mappedBy = "commentator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    public void addPoint(Long point) {
        this.point += point;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    public String getPassword() {
        return password; // User 엔티티의 password 필드 반환
    }

    public String getUsername() {
        return username; // User 엔티티의 username 필드 반환 (로그인 ID)
    }

    public boolean isEnabled() {
        return isActive; // User 엔티티의 isActive 필드 반환 (계정 활성화 여부)
    }
}
