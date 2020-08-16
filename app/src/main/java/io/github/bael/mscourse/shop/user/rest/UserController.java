package io.github.bael.mscourse.shop.user.rest;

import io.github.bael.mscourse.shop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
//@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/users/me")
    public Map<String, String> getInfo(@RequestHeader("X-UserId") String id,
                                       @RequestHeader("X-User") String login,
                                       @RequestHeader("X-Email") String email,
                                       @RequestHeader("X-First-Name") String firstName,
                                       @RequestHeader("X-Last-Name") String lastName) {

        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("login", login);
        map.put("email", email);
        map.put("firstName", firstName);
        map.put("lastName", lastName);
        return map;
    }

//    @PostMapping("/user")
//    public UserModel create(@RequestBody UserModel userModel) {
//        return userService.create(userModel);
//    }
//
//    @GetMapping("/user/{userId}")
//    public UserModel get(@PathVariable Long userId) {
//        return userService.getById(userId);
//    }
//
//
//    @PostMapping("/user/{userId}")
//    public UserModel update(@RequestBody UserModel userModel, @PathVariable Long userId) {
//        UserModel model = UserModel.builder().id(userId)
//                .email(userModel.getEmail())
//                .firstName(userModel.getFirstName())
//                .lastName(userModel.getLastName())
//                .phone(userModel.getPhone()).build();
//        return userService.update(model);
//    }
//
//    @DeleteMapping("/user/{userId}")
//    public void delete(@PathVariable Long userId) {
//        userService.delete(userId);
//    }

}
