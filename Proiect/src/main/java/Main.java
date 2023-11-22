import controller.LoginController;
import database.JDBCConnectionWrapper;
import javafx.application.Application;
import javafx.stage.Stage;
import model.validator.UserValidator;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;
import view.LoginView;

import java.sql.Connection;

import static database.Constants.Schemas.PRODUCTION;

public class Main extends Application {
    public static void main(String[] args) {
        /*System.out.println("Hello world!");

        Book badBook = new BookBuilder()
                .setTitle("Fram Ursul Polar")
                .setAuthor("', ' ', null); DROP TABLE book; -- ")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .build();

        Book book = new BookBuilder()
                .setTitle("Fram Ursul Polar")
                .setAuthor("Cezar Petrescu")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .build();

        AudioBook audioBook = new AudioBookBuilder()
                .setTitle("Fram Ursul Polar AUDIO")
                .setAuthor("Cezar Petrescu")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .setRunTime(10)
                .build();
        EBook ebook = new EBookBuilder()
                .setTitle("Fram Ursul Polar PDF")
                .setAuthor("Cezar Petrescu")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .setFormat("PDF")
                .build();

        JDBCConnectionWrapper connectionWrapper = DatabaseConnectionFactory.getConnectionWrapper(true);
        BookRepository<BookInterface> bookRepository = new BookRepositoryCacheDecorator<>(
                new BookRepositoryMySQL(connectionWrapper.getConnection()),
                new Cache<>()
        );

        bookRepository.removeAll();

        bookRepository.save(book);
        //bookRepository.save(audioBook);
        //bookRepository.save(ebook);

        for(BookInterface bookPrint: bookRepository.findAll()){
            System.out.println(bookPrint);
        }*/

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