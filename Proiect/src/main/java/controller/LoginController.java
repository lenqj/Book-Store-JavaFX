package controller;

import database.JDBCConnectionWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.ComponentFactory;
import model.User;
import model.book.BookInterface;
import model.validator.Notification;
import model.validator.UserValidator;
import repository.book.BookRepository;
import repository.book.sql.BookRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserBooksRepository;
import repository.user.UserBooksRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.user.AuthenticationService;
import service.user.UserBooksService;
import service.user.UserBooksServiceImpl;
import view.CustomerView;
import view.LoginView;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Schemas.PRODUCTION;

public class LoginController {

    private final LoginView loginView;
    private Notification<User> loginNotification;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        this.loginNotification = new Notification<>();
        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
        loginView.showStage(true);
    }
    public Notification<User> getLoginNotification(){
        return loginNotification;
    }

    private class LoginButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            loginNotification = ComponentFactory.getAuthenticationService().login(username, password);

            if (loginNotification.hasErrors()){
                loginView.setActionTargetText(loginNotification.getFormattedErrors());
            }else{
                loginView.setActionTargetText("LogIn Successfull!");
                ComponentFactory.getCustomerView().showStage(true);
                ComponentFactory.getCustomerView().setTableBookList(ComponentFactory.getUserBooksService().findAll(loginNotification.getResult()));
                ComponentFactory.getCustomerView().setUsernameText(loginNotification.getResult().getUsername());
                ComponentFactory.getCustomerView().setMoneyText(String.valueOf(loginNotification.getResult().getMoney()));
            }
        }
    }
    private class RegisterButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();
            Notification<Boolean> registerNotification = ComponentFactory.getAuthenticationService().register(username, password);

            if (registerNotification.hasErrors()) {
                loginView.setActionTargetText(registerNotification.getFormattedErrors());
            } else {
                loginView.setActionTargetText("Register successful!");
            }
        }
    }
}
