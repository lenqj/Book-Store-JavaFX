package controller;

import database.JDBCConnectionWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.User;
import model.book.BookInterface;
import model.validator.UserValidator;
import repository.book.BookRepository;
import repository.book.sql.BookRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserBooksRepository;
import repository.user.UserBooksRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.user.AuthenticationService;
import service.user.UserBooksService;
import service.user.UserBooksServiceImpl;
import view.CustomerView;
import view.LoginView;

import java.sql.Connection;
import java.util.List;

import static database.Constants.Schemas.PRODUCTION;

public class LoginController {

    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final UserValidator userValidator;


    public LoginController(LoginView loginView, AuthenticationService authenticationService, UserValidator userValidator) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.userValidator = userValidator;

        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            User user = authenticationService.login(username, password);

            if (user == null){
                loginView.setActionTargetText("Invalid Username or password!");
            }else{
                loginView.setActionTargetText("LogIn Successfull!");
                CustomerView customerView = new CustomerView(loginView.getStage(), user);
                Connection connection = new JDBCConnectionWrapper(PRODUCTION).getConnection();
                BookRepository<BookInterface> bookRepository = new BookRepositoryMySQL(connection);
                RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
                UserBooksRepository userBooksRepository = new UserBooksRepositoryMySQL(connection, rightsRolesRepository, bookRepository);
                BookService<BookInterface> bookService = new BookServiceImpl(bookRepository);
                UserBooksService userBooksService = new UserBooksServiceImpl(userBooksRepository);
                new CustomerController(customerView, bookService, userBooksService, user);
            }

        }
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            userValidator.validate(username, password);
            final List<String> errors = userValidator.getErrors();
            if (errors.isEmpty()) {
                if (authenticationService.register(username, password)){
                    loginView.setActionTargetText("Register successfull!");
                }else{
                    loginView.setActionTargetText("Register NOT successfull!");
                }
            } else {
                loginView.setActionTargetText(userValidator.getFormattedErrors());
            }
        }
    }
}
