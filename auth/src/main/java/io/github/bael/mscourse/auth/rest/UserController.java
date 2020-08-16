package io.github.bael.mscourse.auth.rest;

import com.google.common.hash.Hashing;
import io.github.bael.mscourse.auth.data.UserAccount;
import io.github.bael.mscourse.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequiredArgsConstructor
public class UserController {

    private static final String AUTH_SESSION_ID = "auth_session";
    private final String salt = "Lake city";

    private final UserService userService;
    private ConcurrentHashMap<String, String> sessionMap = new ConcurrentHashMap<>();

    private String encode(String input) {
        String sha256hex = Hashing.sha256()
                .hashString(input, StandardCharsets.UTF_8)
                .toString();
        return Hashing.sha256()
                .hashString(sha256hex + salt, StandardCharsets.UTF_8)
                .toString();

    }

    @PostMapping("/register")
    public UserInfo create(@RequestBody UserRegistrationInfo userRegistrationInfo) {
        // hashing password
        userRegistrationInfo.setPassword(encode(userRegistrationInfo.getPassword()));
        return userService.create(userRegistrationInfo);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@CookieValue(value = AUTH_SESSION_ID, defaultValue = "nouser") String sessionId,
                                         HttpServletResponse response) {
        if (sessionId != null) {
            sessionMap.remove(sessionId);
        }

        Cookie cookie = new Cookie(AUTH_SESSION_ID, null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return new ResponseEntity<>("OK", HttpStatus.OK);

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginMap, HttpServletResponse response) {

        // hashing password
        String login = loginMap.get("login");
        String password = loginMap.get("password");

        if (login != null && password != null) {
            UserAccount user = userService.findByLogin(login);
            if (user != null) {
                if (user.getPassword().equals(encode(password))) {
                    String sessionId = UUID.randomUUID().toString();
                    sessionMap.put(sessionId, login);
                    Cookie cookie = new Cookie(AUTH_SESSION_ID, sessionId);
                    response.addCookie(cookie);
                    return new ResponseEntity<>(user.getId().toString(), HttpStatus.OK);
                }
            }
        }

        return new ResponseEntity<>("wrong username or password", HttpStatus.UNAUTHORIZED);
    }


    @GetMapping(value = {"/signin", "/"})
    public Map<String, String> signin() {
        return Collections.singletonMap("message", "Please go to login and provide Login/Password");
    }

    private String getName(String sessionId) {
        String username = sessionMap.get(sessionId);
        return username;

    }


    @GetMapping("/auth")
    public ResponseEntity<UserInfo> auth(@CookieValue(value = AUTH_SESSION_ID, defaultValue = "nouser") String sessionId) {

        String username = getName(sessionId);
        if (username == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        UserInfo userInfo = userService.getByLogin(username);
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
                                                  @CookieValue(value = AUTH_SESSION_ID, defaultValue = "nouser") String sessionId) {

        String username = getName(sessionId);
        if (username == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        assertProfileBelongsToUser(userId, username);
        return new ResponseEntity<>(userService.updateProfile(userId, userProfile), HttpStatus.OK);
    }

    @GetMapping("/unauthorized")
    public ResponseEntity<String> unauthorized() {
        return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    private void assertProfileBelongsToUser(Long userId, String username) {
        Objects.requireNonNull(userId);
        UserInfo info = userService.getById(userId);
        Objects.requireNonNull(username);

        if (!info.getLogin().equals(username)) {
            throw new ForbiddenException("You can change only you'r own profile");
        }
    }

//    @DeleteMapping("/user/{userId}")
//    public ResponseEntity<String> delete(@PathVariable Long userId, @CookieValue(value = AUTH_SESSION_ID) String sessionId) {
//        if (Objects.isNull(authentication)) {
//            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
//        }
//        assertProfileBelongsToUser(userId, authentication);
//        userService.delete(userId);
//        return new ResponseEntity<>("OK", HttpStatus.OK);
//    }

}
