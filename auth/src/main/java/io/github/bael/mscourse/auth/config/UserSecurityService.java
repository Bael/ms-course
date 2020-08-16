package io.github.bael.mscourse.auth.config;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UserSecurityService
//        implements UserDetailsService
{

//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserAccount userAccount = userRepository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException(
//                String.format("User with username %s not founded!", username)));
//        return User.builder()
//                .accountLocked(userAccount.isLocked())
//                .username(userAccount.getLogin())
//                .password(userAccount.getPassword())
//                .disabled(userAccount.isDisabled())
//                .roles("USER")
//                .build();
//    }
}
