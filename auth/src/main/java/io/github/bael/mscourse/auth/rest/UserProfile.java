package io.github.bael.mscourse.auth.rest;

import io.github.bael.mscourse.auth.data.UserAccount;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
public class UserProfile {
    private String firstName;
    private String lastName;
    private String phone;

}
