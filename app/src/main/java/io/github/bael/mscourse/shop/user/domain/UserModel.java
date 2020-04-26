package io.github.bael.mscourse.shop.user.domain;

import io.github.bael.mscourse.shop.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder
@Getter
public class UserModel {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public static UserModel of(User user) {
        return UserModel.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .id(user.getId())
                .build();
    }
}
