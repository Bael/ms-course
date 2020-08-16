package io.github.bael.mscourse.auth.rest;

import io.github.bael.mscourse.auth.data.UserAccount;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserInfo {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String login;
    private String phone;

    public static UserInfo of(UserAccount userAccount) {
        return builder()
                .email(userAccount.getEmail())
                .firstName(userAccount.getFirstName())
                .lastName(userAccount.getLastName())
                .login(userAccount.getLogin())
                .phone(userAccount.getPhone())
                .id(userAccount.getId())
                .build();
    }
}
