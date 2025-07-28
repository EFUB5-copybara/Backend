//package efub.cpbr.crumble.auth.service;
//
//import efub.cpbr.crumble.user.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class UserDetailsServiceImpl implements UserDetailsService {
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // 사용자 이름으로 DB에서 사용자 엔티티를 찾고, CustomUserDetails로 변환하여 반환
//        return userRepository.findByUsername(username)
//                .map(CustomUserDetails::new) // User 엔티티를 CustomUserDetails로 변환
//                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
//    }
//}