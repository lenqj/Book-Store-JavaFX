package controller;

import database.JDBCConnectionWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.User;
import model.book.Book;
import model.book.BookInterface;
import model.validator.UserValidator;
import repository.book.BookRepository;
import repository.book.sql.BookRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;
import view.CustomerView;
import view.LoginView;

import java.sql.Connection;
import java.util.EventListener;
import java.util.List;

import static database.Constants.Schemas.PRODUCTION;

public class CustomerController {

    private final CustomerView customerView;
    private final BookService<BookInterface> bookService;


    public CustomerController(CustomerView customerView, BookService<BookInterface> bookService) {
        this.customerView = customerView;
        this.bookService = bookService;
        customerView.setTableBookList(bookService.findAll());
        customerView.addSellBookButtonButtonListener(new SellBookButtonListener());
        customerView.addLogoutButtonListener(new LogoutButtonListener());
    }
    private class LogoutButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            LoginView loginView = new LoginView(customerView.getStage());
            Connection connection = new JDBCConnectionWrapper(PRODUCTION).getConnection();
            final RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
            final UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);

            final AuthenticationService authenticationService = new AuthenticationServiceImpl(userRepository,
                    rightsRolesRepository);
            final UserValidator userValidator = new UserValidator(userRepository);

            new LoginController(loginView, authenticationService, userValidator);
        }
    }

    private class SellBookButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            BookInterface book = customerView.getSelectedBook();
            if(book != null) {
                customerView.setTextSellBook(customerView.getSelectedBook().toString());
            }
        }
    }

}