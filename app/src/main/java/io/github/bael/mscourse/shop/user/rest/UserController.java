package io.github.bael.mscourse.shop.user.rest;

import io.github.bael.mscourse.shop.user.domain.UserModel;
import io.github.bael.mscourse.shop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1")
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
        UserModel model = UserModel.builder().id(userId)
                .email(userModel.getEmail())
                .firstName(userModel.getFirstName())
                .lastName(userModel.getLastName())
                .phone(userModel.getPhone()).build();
        return userService.update(model);
    }

    @DeleteMapping("/user/{userId}")
    public void delete(@PathVariable Long userId) {
        userService.delete(userId);
    }

}
