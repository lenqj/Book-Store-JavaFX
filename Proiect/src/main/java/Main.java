import controller.LoginController;
import database.DatabaseConnectionFactory;
import database.JDBCConnectionWrapper;
import javafx.application.Application;
import javafx.stage.Stage;
import model.book.Book;
import model.book.BookInterface;
import model.builder.BookBuilder;
import model.validator.UserValidator;
import repository.Cache;
import repository.book.BookRepository;
import repository.book.cache.BookRepositoryCacheDecorator;
import repository.book.sql.BookRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;
import view.LoginView;

import java.sql.Connection;
import java.time.LocalDate;

import static database.Constants.Schemas.PRODUCTION;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);

    }
    @Override
    public void start(Stage primaryStage) {
        final Connection connection = new JDBCConnectionWrapper(PRODUCTION).getConnection();

        final RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        final UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);

        final AuthenticationService authenticationService = new AuthenticationServiceImpl(userRepository,
                rightsRolesRepository);

        final LoginView loginView = new LoginView(primaryStage);

        final UserValidator userValidator = new UserValidator(userRepository);

        new LoginController(loginView, authenticationService, userValidator);
    }
}