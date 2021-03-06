package io.github.bael.mscourse.shop.user.data;

import io.github.bael.mscourse.shop.user.entity.UserAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserAccount, Long> {
    Optional<UserAccount> findByEmail(String email);
}
