package io.github.bael.mscourse.auth.rest;

import io.github.bael.mscourse.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder encoder;

    @PostMapping("/register")
    public UserInfo create(@RequestBody UserRegistrationInfo userRegistrationInfo) {
        // hashing password
        userRegistrationInfo.setPassword(encoder.encode(userRegistrationInfo.getPassword()));
        return userService.create(userRegistrationInfo);
    }


    @GetMapping(value = {"/signin", "/"})
    public Map<String, String> signin() {
        return Collections.singletonMap("message", "Please go to login and provide Login/Password");
    }

    private void checkAuth(Principal principal) {

    }
    @GetMapping("/auth")
    public ResponseEntity<UserInfo> auth(Principal principal) {
        if (Objects.isNull(principal)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        UserInfo userInfo = userService.getByLogin(principal.getName());
        MultiValueMap<String, String> map = new HttpHeaders();
        map.put("X-UserId", Collections.singletonList(userInfo.getId().toString()));
        map.put("X-User", Collections.singletonList(userInfo.getLogin()));
        map.put("X-Email", Collections.singletonList(userInfo.getEmail()));
        map.put("X-First-Name", Collections.singletonList(userInfo.getFirstName()));
        map.put("X-Last-Name", Collections.singletonList(userInfo.getLastName()));
        return new ResponseEntity<>(userInfo, map, HttpStatus.OK);
    }



    @PutMapping("/user/{userId}")
    public ResponseEntity<UserInfo> updateProfile(@RequestBody UserProfile userProfile, @PathVariable Long userId,
                                  Authentication authentication) {
        if (Objects.isNull(authentication)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        assertProfileBelongsToUser(userId, authentication);
        return new ResponseEntity<>(userService.updateProfile(userId, userProfile), HttpStatus.OK);
    }

    @GetMapping("/unauthorized")
    public ResponseEntity<String> unauthorized() {
        return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    private void assertProfileBelongsToUser(Long userId, Authentication authentication) {
        Objects.requireNonNull(authentication);
        UserInfo info = userService.getById(userId);
        Objects.requireNonNull(authentication.getPrincipal());
        User user = (User) authentication.getPrincipal();
        if (!info.getLogin().equals(user.getUsername())) {
            throw new ForbiddenException("You can change only you'r own profile");
        }
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> delete(@PathVariable Long userId, Authentication authentication) {
        if (Objects.isNull(authentication)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        assertProfileBelongsToUser(userId, authentication);
        userService.delete(userId);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}
