package service.user;

import model.User;
import model.validator.Notification;
import repository.user.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static database.Constants.Tables.USER_BOOKS;

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
}
