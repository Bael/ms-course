package io.github.bael.mscourse.shop.user.data;

import io.github.bael.mscourse.shop.user.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
