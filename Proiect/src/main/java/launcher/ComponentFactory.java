package launcher;

import controller.CustomerController;
import controller.LoginController;
import controller.SoldBooksController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import model.User;
import model.book.BookInterface;
import model.builder.UserBuilder;
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
import service.user.AuthenticationServiceImpl;
import service.user.UserBooksService;
import service.user.UserBooksServiceImpl;
import view.CustomerView;
import view.LoginView;
import view.SoldBooksView;

import java.sql.Connection;

public class ComponentFactory {
    private static LoginView loginView;
    private static CustomerView customerView;
    private static SoldBooksView soldBooksView;
    public static LoginController loginController;
    private static CustomerController customerController;
    private static SoldBooksController soldBooksController;
    private static AuthenticationService authenticationService;
    private static UserRepository userRepository;
    private static RightsRolesRepository rightsRolesRepository;
    private static BookRepository<BookInterface> bookRepository;
    private static BookService<BookInterface> bookService;
    private static UserBooksService userBooksService;
    private static UserBooksRepository userBooksRepository;
    private static ComponentFactory instance;


    public static ComponentFactory getInstance(Boolean componentsForTests, Stage stage){
        if (instance == null){
            instance = new ComponentFactory(componentsForTests, stage);
        }

        return instance;
    }

    public ComponentFactory(Boolean componentsForTests, Stage stage){
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTests).getConnection();
        rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        authenticationService = new AuthenticationServiceImpl(userRepository, rightsRolesRepository);
        loginView = new LoginView(stage);
        loginController = new LoginController(loginView);


        bookRepository = new BookRepositoryMySQL(connection);
        bookService = new BookServiceImpl(bookRepository);
        userBooksRepository = new UserBooksRepositoryMySQL(connection, rightsRolesRepository, bookRepository, userRepository);
        userBooksService = new UserBooksServiceImpl(userBooksRepository);
        customerView = new CustomerView(stage);
        customerController = new CustomerController(customerView);


        soldBooksView = new SoldBooksView(stage);
        soldBooksController = new SoldBooksController(soldBooksView);

    }

    public static AuthenticationService getAuthenticationService(){
        return authenticationService;
    }

    public static UserRepository getUserRepository(){
        return userRepository;
    }

    public static RightsRolesRepository getRightsRolesRepository(){
        return rightsRolesRepository;
    }

    public static LoginView getLoginView(){
        return loginView;
    }

    public static BookRepository<BookInterface> getBookRepository(){
        return bookRepository;
    }

    public static BookService<BookInterface> getBookService() {
        return bookService;
    }

    public static LoginController getLoginController(){
        return loginController;
    }

    public static UserBooksService getUserBooksService() {
        return userBooksService;
    }
    public static CustomerView getCustomerView(){
        return customerView;
    }
    public static SoldBooksView getSoldBooksView(){
        return soldBooksView;
    }
    public static CustomerController getCustomerController() {
        return customerController;
    }
}
