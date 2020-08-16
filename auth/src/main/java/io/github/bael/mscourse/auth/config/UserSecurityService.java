package io.github.bael.mscourse.auth.config;

import io.github.bael.mscourse.auth.data.UserAccount;
import io.github.bael.mscourse.auth.data.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userRepository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User with username %s not founded!", username)));
        return User.builder()
                .accountLocked(userAccount.isLocked())
                .username(userAccount.getLogin())
                .password(userAccount.getPassword())
                .disabled(userAccount.isDisabled())
                .roles("USER")
                .build();
    }
}
