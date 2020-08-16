package io.github.bael.mscourse.auth.config;


import io.github.bael.mscourse.auth.data.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig
//        extends WebSecurityConfigurerAdapter
{

    private final UserRepository userRepository;

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        return 
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("password")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/", "/health", "/signin",
//                        "/user/**", "/actuator/**", "/register", "/login").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .csrf()
//                .disable()
//                .formLogin()
//                .loginPage("/unauthorized")
//                .loginProcessingUrl("/login")
//                .defaultSuccessUrl("/auth")
//                .usernameParameter("login")
//                .permitAll()
//                .and()
//                .logout(httpSecurityLogoutConfigurer ->
//                        httpSecurityLogoutConfigurer.invalidateHttpSession(true)
//                                .logoutUrl("/logout")
//                                .logoutSuccessUrl("/unauthorized")
//                                .logoutSuccessHandler(this::logoutHandler)
//
//
//                );
//    }
//
//    private void logoutHandler(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
//        System.out.printf("Logout requested by %s! \n", authentication);
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider());
//    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        UserDetailsService service = new UserSecurityService(userRepository);
//        DaoAuthenticationProvider authProvider
//                = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(service);
//        authProvider.setPasswordEncoder(encoder());
//        return authProvider;
//    }

//    @Bean
//    public PasswordEncoder encoder() {
//        return new BCryptPasswordEncoder(11);
//    }
//
//    @Bean
//    public AuthenticationEventPublisher authenticationEventPublisher
//            (ApplicationEventPublisher applicationEventPublisher) {
//        return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
//    }
}
