package launcher;

import controller.*;
import controller.Admin.AdminBooksController;
import controller.Admin.AdminController;
import controller.Admin.AdminCreateUserController;
import controller.Admin.AdminUsersController;
import controller.Customer.CustomerBooksController;
import controller.Customer.CustomerController;
import controller.Employee.EmployeeBooksController;
import controller.Employee.EmployeeSoldBooksController;
import controller.Employee.EmployeeController;
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
import service.user.*;
import view.*;
import view.Admin.AdminBooksView;
import view.Admin.AdminCreateUserView;
import view.Admin.AdminUsersView;
import view.Admin.AdminView;
import view.Customer.CustomerBooksView;
import view.Customer.CustomerView;
import view.Employee.EmployeeBooksView;
import view.Employee.EmployeeSoldBooksView;
import view.Employee.EmployeeView;

import java.sql.Connection;

public class ComponentFactory {
    private static MainView mainView;
    private static LoginView loginView;
    private static CustomerView customerView;
    private static CustomerBooksView customerBooksView;
    private static EmployeeView employeeView;
    private static EmployeeBooksView employeeBooksView;
    private static EmployeeSoldBooksView employeeSoldBooksView;
    private static AdminView adminView;
    private static AdminUsersView adminUsersView;
    private static AdminCreateUserView adminCreateUserView;
    private static AdminBooksView adminBooksView;
    private static MainController mainController;
    private static LoginController loginController;
    private static CustomerController customerController;
    private static CustomerBooksController customerBooksController;
    private static EmployeeController employeeController;
    private static EmployeeBooksController employeeBooksController;
    private static EmployeeSoldBooksController employeeSoldBooksController;
    private static AdminController adminController;
    private static AdminUsersController adminUsersController;
    private static AdminCreateUserController adminCreateUserController;
    private static AdminBooksController adminBooksController;
    private static UserRepository userRepository;
    private static RightsRolesRepository rightsRolesRepository;
    private static BookRepository<BookInterface> bookRepository;
    private static UserBooksRepository userBooksRepository;
    private static AuthenticationService authenticationService;
    private static BookService<BookInterface> bookService;
    private static UserBooksService userBooksService;
    private static UserService userService;
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
        userService = new UserServiceImpl(userRepository);


        mainView = new MainView(stage);
        loginView = new LoginView();


        customerView = new CustomerView();
        customerBooksView = new CustomerBooksView();


        employeeView = new EmployeeView();
        employeeSoldBooksView = new EmployeeSoldBooksView();
        employeeBooksView = new EmployeeBooksView();


        adminView = new AdminView();
        adminUsersView = new AdminUsersView();
        adminCreateUserView = new AdminCreateUserView();
        adminBooksView = new AdminBooksView();


        mainController = new MainController(mainView);
        loginController = new LoginController(loginView);


        customerController = new CustomerController(customerView);
        customerBooksController = new CustomerBooksController(customerBooksView);


        employeeController = new EmployeeController(employeeView);
        employeeSoldBooksController = new EmployeeSoldBooksController(employeeSoldBooksView);
        employeeBooksController = new EmployeeBooksController(employeeBooksView);

        adminController = new AdminController(adminView);
        adminUsersController = new AdminUsersController(adminUsersView);
        adminCreateUserController = new AdminCreateUserController(adminCreateUserView);
        adminBooksController = new AdminBooksController(adminBooksView);
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
    public static UserService getUserService() {
        return userService;
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
    public static EmployeeBooksView getEmployeeBooksView(){
        return employeeBooksView;
    }
    public static EmployeeSoldBooksView getEmployeeSoldBooksView(){
        return employeeSoldBooksView;
    }
    public static EmployeeView getEmployeeView() {
        return employeeView;
    }
    public static AdminView getAdminView() {
        return adminView;
    }
    public static AdminUsersView getAdminUsersView() {
        return adminUsersView;
    }
    public static AdminCreateUserView getAdminCreateUserView(){
        return adminCreateUserView;
    }
    public static AdminBooksView getAdminBooksView() {
        return adminBooksView;
    }
    public static MainController getMainController() {
        return mainController;
    }
    public static LoginController getLoginController() {
        return loginController;
    }
    public static CustomerController getCustomerController() {
        return customerController;
    }
    public static CustomerBooksController getCustomerBooksController() {
        return customerBooksController;
    }
    public static EmployeeSoldBooksController getEmployeeSoldBooksController() {
        return employeeSoldBooksController;
    }
    public static EmployeeController getEmployeeController() {
        return employeeController;
    }

    public static AdminController getAdminController() {
        return adminController;
    }

    public static AdminUsersController getAdminUsersController() {
        return adminUsersController;
    }
}
