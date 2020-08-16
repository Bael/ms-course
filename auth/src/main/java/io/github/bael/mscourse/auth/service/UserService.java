package io.github.bael.mscourse.auth.service;

import io.github.bael.mscourse.auth.data.UserAccount;
import io.github.bael.mscourse.auth.data.UserRepository;
import io.github.bael.mscourse.auth.rest.UserInfo;
import io.github.bael.mscourse.auth.rest.UserProfile;
import io.github.bael.mscourse.auth.rest.UserRegistrationInfo;
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

    private void fillFromModel(UserAccount userAccount, UserInfo model) {
        userAccount.setEmail(model.getEmail());
        userAccount.setFirstName(model.getFirstName());
        userAccount.setLastName(model.getLastName());
        userAccount.setPhone(model.getPhone());
    }

    public UserInfo create(UserRegistrationInfo info) {
        Objects.requireNonNull(info);
        UserAccount userAccount = new UserAccount();
        userAccount.setPhone(info.getPhone());
        userAccount.setLastName(info.getLastName());
        userAccount.setFirstName(info.getFirstName());
        userAccount.setDisabled(false);
        userAccount.setLocked(false);
        userAccount.setPassword(info.getPassword());
        userAccount.setEmail(info.getEmail());
        userAccount.setLogin(info.getLogin());
        userRepository.save(userAccount);
        return UserInfo.of(userAccount);
    }

    public UserInfo getById(Long id) {
        return UserInfo.of(findById(id));
    }

//    public UserInfo update(UserInfo userInfo) {
//        Objects.requireNonNull(userInfo);
//        UserAccount userAccount = userRepository.findById(userInfo.getId())
//                .orElseThrow(() -> new ObjectNotFoundException("Пользователь",
//                        userInfo.getId().toString()));
//        fillFromModel(userAccount, userInfo);
//        userRepository.save(userAccount);
//        return UserInfo.of(userAccount);
//
//    }

    private UserAccount findById(Long id) {
        Objects.requireNonNull(id);

        return userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Пользователь", id.toString()));
    }

    public void delete(Long id) {
        Objects.requireNonNull(id);
        userRepository.deleteById(id);
    }

    public UserInfo updateProfile(Long userId, UserProfile userProfile) {
        UserAccount account = findById(userId);
        account.setFirstName(userProfile.getFirstName());
        account.setLastName(userProfile.getLastName());
        account.setPhone(userProfile.getPhone());
        userRepository.save(account);
        return UserInfo.of(account);
    }

    public UserInfo getByLogin(String username) {
        UserAccount account = userRepository.findByLogin(username)
                .orElseThrow(() -> new ObjectNotFoundException("Username is not founded!", username));
        return UserInfo.of(account);
    }
}
