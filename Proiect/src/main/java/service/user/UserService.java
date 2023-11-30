package service.user;

import model.User;
import model.validator.Notification;

import java.util.List;

public interface UserService {
   List<User> findAll();
   Notification<Boolean> deleteUser(User user);
   Notification<Boolean> createUser(String username, String password, String role, Long money);
}