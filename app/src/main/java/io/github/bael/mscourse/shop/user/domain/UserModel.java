package io.github.bael.mscourse.shop.user.domain;

import io.github.bael.mscourse.shop.user.entity.UserAccount;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserModel {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public static UserModel of(UserAccount userAccount) {
        return UserModel.builder()
                .email(userAccount.getEmail())
                .firstName(userAccount.getFirstName())
                .lastName(userAccount.getLastName())
                .phone(userAccount.getPhone())
                .id(userAccount.getId())
                .build();
    }
}
