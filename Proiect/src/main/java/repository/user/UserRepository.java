package repository.user;

import model.User;
import model.book.BookInterface;
import model.validator.Notification;

import java.util.*;

public interface UserRepository {

    List<User> findAll();

    Notification<User> findByUsernameAndPassword(String username, String password);

    Notification<Boolean> save(User user);

    void removeAll();

    boolean existsByUsername(String username);
    Notification<User> updateMoney(User user, Long money);
    User findById(Long id);
    Notification<Boolean> deleteUser(User user);
}
