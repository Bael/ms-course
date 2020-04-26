package io.github.bael.mscourse.shop.user.service;

import io.github.bael.mscourse.shop.user.data.UserRepository;
import io.github.bael.mscourse.shop.user.domain.UserModel;
import io.github.bael.mscourse.shop.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    private void fillFromModel(User user, UserModel model) {
        user.setEmail(model.getEmail());
        user.setFirstName(model.getFirstName());
        user.setLastName(model.getLastName());
        user.setPhone(model.getPhone());
    }

    public UserModel create(UserModel userModel) {
        Objects.requireNonNull(userModel);
        User user = new User();
        fillFromModel(user, userModel);
        userRepository.save(user);
        return UserModel.of(user);
    }

    public UserModel getById(Long id) {
        Objects.requireNonNull(id);
        User user = userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Пользователь", id.toString()));
        return UserModel.of(user);
    }

    public UserModel update(UserModel userModel) {
        Objects.requireNonNull(userModel);
        User user = userRepository.findById(userModel.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Пользователь",
                        userModel.getId().toString()));
        fillFromModel(user, userModel);
        userRepository.save(user);
        return UserModel.of(user);

    }

    public void delete(Long id) {
        Objects.requireNonNull(id);
        userRepository.deleteById(id);
    }
}
