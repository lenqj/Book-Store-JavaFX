package service.user;

import model.Role;
import model.User;
import model.validator.Notification;

import java.util.List;

public interface AuthenticationService {
    Notification<Boolean> register(String username, String password);
    Notification<User> login(String username, String password);
    boolean logout(User user);
    List<Role> findAllRoles();
}
