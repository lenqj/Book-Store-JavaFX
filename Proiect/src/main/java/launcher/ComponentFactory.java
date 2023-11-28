package launcher;

import controller.*;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import model.book.BookInterface;
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
import view.*;
import view.Admin.AdminView;
import view.Customer.CustomerBooksView;
import view.Customer.CustomerView;
import view.Customer.CustomerSoldBooksView;
import view.Employee.EmployeeView;

import java.sql.Connection;

public class ComponentFactory {
    private static MainView mainView;
    private static LoginView loginView;
    private static CustomerView customerView;
    private static CustomerBooksView customerBooksView;
    private static CustomerSoldBooksView customerSoldBooksView;
    private static EmployeeView employeeView;
    private static AdminView adminView;
    private static MainController mainController;
    public static LoginController loginController;
    private static CustomerController customerController;
    private static CustomerBooksController customerBooksController;
    private static SoldBooksController soldBooksController;
    private static EmployeeController employeeController;
    private static AdminController adminController;
    private static UserRepository userRepository;
    private static RightsRolesRepository rightsRolesRepository;
    private static BookRepository<BookInterface> bookRepository;
    private static UserBooksRepository userBooksRepository;
    private static AuthenticationService authenticationService;
    private static BookService<BookInterface> bookService;
    private static UserBooksService userBooksService;
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
        bookRepository = new BookRepositoryMySQL(connection);
        userBooksRepository = new UserBooksRepositoryMySQL(connection, rightsRolesRepository, bookRepository, userRepository);


        authenticationService = new AuthenticationServiceImpl(userRepository, rightsRolesRepository);
        bookService = new BookServiceImpl(bookRepository);
        userBooksService = new UserBooksServiceImpl(userBooksRepository);


        mainView = new MainView(stage);
        loginView = new LoginView();
        customerView = new CustomerView();
        customerBooksView = new CustomerBooksView();
        customerSoldBooksView = new CustomerSoldBooksView();
        employeeView = new EmployeeView();
        adminView = new AdminView();


        mainController = new MainController(mainView);
        loginController = new LoginController(loginView);
        customerController = new CustomerController(customerView);
        customerBooksController = new CustomerBooksController(customerBooksView);
        soldBooksController = new SoldBooksController(customerSoldBooksView);
        employeeController = new EmployeeController();
        adminController = new AdminController();
    }

    public static UserRepository getUserRepository(){
        return userRepository;
    }
    public static RightsRolesRepository getRightsRolesRepository(){
        return rightsRolesRepository;
    }
    public static BookRepository<BookInterface> getBookRepository(){
        return bookRepository;
    }
    public static UserBooksRepository getUserBooksRepository(){
        return userBooksRepository;
    }
    public static AuthenticationService getAuthenticationService(){
        return authenticationService;
    }
    public static BookService<BookInterface> getBookService() {
        return bookService;
    }
    public static UserBooksService getUserBooksService() {
        return userBooksService;
    }
    public static MainView getMainView(){
        return mainView;
    }
    public static LoginView getLoginView(){
        return loginView;
    }
    public static CustomerView getCustomerView(){
        return customerView;
    }
    public static CustomerBooksView getCustomerBooksView(){
        return customerBooksView;
    }
    public static CustomerSoldBooksView getSoldBooksView(){
        return customerSoldBooksView;
    }

    public static EmployeeView getEmployeeView() {
        return employeeView;
    }
    public static AdminView getAdminView() {
        return adminView;
    }
}
