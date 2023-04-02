package ca.com.idealimport.service.users.control.service;

import ca.com.idealimport.config.exception.IdealException;
import ca.com.idealimport.config.exception.enums.IdealResponseErrorCode;
import ca.com.idealimport.service.users.control.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var users = userRepository.findByUserName(username)
                .orElseThrow(() -> new IdealException(IdealResponseErrorCode.BAD_CREDENTIAL, "User not found with email : " + username));
        var roles = users.getRoles().stream().distinct()
                .map(e -> new SimpleGrantedAuthority(e.getName())).toList();
        return new User(username, users.getPassword(), roles);
    }
}
