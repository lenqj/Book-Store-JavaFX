package service.user;

import launcher.ComponentFactory;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validator.Notification;
import model.validator.UserValidator;
import repository.user.UserRepository;

import java.util.Collections;
import java.util.List;

import static database.Constants.Roles.ADMINISTRATOR;
import static service.user.AuthenticationServiceImpl.hashPassword;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Notification<Boolean> deleteUser(User user) {
        return userRepository.deleteUser(user);
    }

    public Notification<Boolean> createUser(String username, String password, String role, Long money) {
        Role userRole = ComponentFactory.getRightsRolesRepository().findRoleByTitle(role);
        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(userRole))
                .setMoney(money)
                .build();

        UserValidator userValidator = new UserValidator(user);
        boolean userValid = userValidator.validate();
        Notification<Boolean> userRegisterNotification = new Notification<>();
        if (!userValid) {
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
        } else {
            user.setPassword(hashPassword(password));
            Notification<Boolean> userSaveNotification = userRepository.save(user);
            if (userSaveNotification.hasErrors()) {
                userSaveNotification.getErrors().forEach(userRegisterNotification::addError);
                return userRegisterNotification;
            } else {
                userRegisterNotification.setResult(userSaveNotification.getResult());
            }
        }
        return userRegisterNotification;
    }
}
