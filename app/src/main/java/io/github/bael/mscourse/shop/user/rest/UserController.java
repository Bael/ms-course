package io.github.bael.mscourse.shop.user.rest;

import io.github.bael.mscourse.shop.user.domain.UserModel;
import io.github.bael.mscourse.shop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public UserModel create(@RequestBody UserModel userModel) {
        return userService.create(userModel);
    }

    @GetMapping("/user/{userId}")
    public UserModel get(@PathVariable Long userId) {
        return userService.getById(userId);
    }

    @PutMapping("/user/{userId}")
    public UserModel update(@RequestBody UserModel userModel, @PathVariable Long userId) {
        return userService.update(userModel);
    }

    @DeleteMapping("/user/{userId}")
    public void delete(@PathVariable Long userId) {
        userService.delete(userId);
    }

}
