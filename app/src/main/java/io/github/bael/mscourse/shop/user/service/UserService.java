package io.github.bael.mscourse.shop.user.service;

import io.github.bael.mscourse.shop.user.data.UserRepository;
import io.github.bael.mscourse.shop.user.domain.UserModel;
import io.github.bael.mscourse.shop.user.entity.UserAccount;
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

    private void fillFromModel(UserAccount userAccount, UserModel model) {
        userAccount.setEmail(model.getEmail());
        userAccount.setFirstName(model.getFirstName());
        userAccount.setLastName(model.getLastName());
        userAccount.setPhone(model.getPhone());
    }

    public UserModel create(UserModel userModel) {
        Objects.requireNonNull(userModel);
        UserAccount userAccount = new UserAccount();
        fillFromModel(userAccount, userModel);
        userRepository.save(userAccount);
        return UserModel.of(userAccount);
    }

    public UserModel getById(Long id) {
        Objects.requireNonNull(id);
        UserAccount userAccount = userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Пользователь", id.toString()));
        return UserModel.of(userAccount);
    }

    public UserModel update(UserModel userModel) {
        Objects.requireNonNull(userModel);
        UserAccount userAccount = userRepository.findById(userModel.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Пользователь",
                        userModel.getId().toString()));
        fillFromModel(userAccount, userModel);
        userRepository.save(userAccount);
        return UserModel.of(userAccount);

    }

    public void delete(Long id) {
        Objects.requireNonNull(id);
        userRepository.deleteById(id);
    }
}
