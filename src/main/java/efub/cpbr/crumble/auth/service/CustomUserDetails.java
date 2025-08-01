package efub.cpbr.crumble.auth.service; // 또는 적절한 패키지 경로

import efub.cpbr.crumble.user.entity.User; // User 엔티티 임포트
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public User getUser(){
        return user;
    }
    public Long getUserId(){return user.getUserId();}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // User 엔티티의 role 필드를 사용하여 권한을 생성
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부 (항상 true)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠금 여부 (항상 true)
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명(비밀번호) 만료 여부 (항상 true)
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }

    @Override
    public String toString() {
        return "CustomUserDetails(username=" + user.getUsername() + ", role=" + user.getRole() + ")";
    }
}