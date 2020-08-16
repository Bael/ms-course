package io.github.bael.mscourse.auth.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationInfo {
    private String firstName;
    private String lastName;
    private String login;
    private String email;
    private String phone;
    private String password;
}
