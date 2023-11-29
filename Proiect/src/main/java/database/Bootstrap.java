package database;

import model.Role;
import model.User;
import model.book.BookInterface;
import model.builder.UserBuilder;
import repository.book.BookRepository;
import repository.book.sql.BookRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import model.book.Book;
import model.builder.BookBuilder;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.book.BookServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static database.Constants.Rights.RIGHTS;
import static database.Constants.Roles.*;
import static database.Constants.Schemas.PRODUCTION;
import static database.Constants.Schemas.SCHEMAS;
import static database.Constants.getRolesRights;

public class Bootstrap {

    private static RightsRolesRepository rightsRolesRepository;

    public static void main(String[] args) throws SQLException {
        dropAll();

        bootstrapTables();

        bootstrapUserData();

        bootstrapAddBooks();
        bootstrapAddDefaultUsers();
    }

    private static void dropAll() throws SQLException {
        for (String schema : SCHEMAS) {
            System.out.println("Dropping all tables in schema: " + schema);

            Connection connection = new JDBCConnectionWrapper(schema).getConnection();
            Statement statement = connection.createStatement();

            String[] dropStatements = {
                    "DROP TABLE `user_books`;",
                    "DROP TABLE `user_bought_books`;",
                    "DROP TABLE `role_right`;",
                    "DROP TABLE `user_role`;",
                    "DROP TABLE `book`, `role`, `right`, `user`;"
            };

            Arrays.stream(dropStatements).forEach(dropStatement -> {
                try {
                    statement.execute(dropStatement);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }

        System.out.println("Done table bootstrap");
    }

    private static void bootstrapTables() throws SQLException {
        SQLTableCreationFactory sqlTableCreationFactory = new SQLTableCreationFactory();

        for (String schema : SCHEMAS) {
            System.out.println("Bootstrapping " + schema + " schema");


            JDBCConnectionWrapper connectionWrapper = new JDBCConnectionWrapper(schema);
            Connection connection = connectionWrapper.getConnection();

            Statement statement = connection.createStatement();

            for (String table : Constants.Tables.ORDERED_TABLES_FOR_CREATION) {
                String createTableSQL = sqlTableCreationFactory.getCreateSQLForTable(table);
                statement.execute(createTableSQL);
            }
        }

        System.out.println("Done table bootstrap");
    }

    private static void bootstrapUserData() throws SQLException {
        for (String schema : SCHEMAS) {
            System.out.println("Bootstrapping user data for " + schema);

            JDBCConnectionWrapper connectionWrapper = new JDBCConnectionWrapper(schema);
            rightsRolesRepository = new RightsRolesRepositoryMySQL(connectionWrapper.getConnection());

            bootstrapRoles();
            bootstrapRights();
            bootstrapRoleRight();
            bootstrapUserRoles();
        }
    }

    private static void bootstrapRoles() throws SQLException {
        for (String role : ROLES) {
            rightsRolesRepository.addRole(role);
        }
    }

    private static void bootstrapRights() throws SQLException {
        for (String right : RIGHTS) {
            rightsRolesRepository.addRight(right);
        }
    }

    private static void bootstrapRoleRight() throws SQLException {
        Map<String, List<String>> rolesRights = getRolesRights();

        for (String role : rolesRights.keySet()) {
            Long roleId = rightsRolesRepository.findRoleByTitle(role).getId();

            for (String right : rolesRights.get(role)) {
                Long rightId = rightsRolesRepository.findRightByTitle(right).getId();
                rightsRolesRepository.addRoleRight(roleId, rightId);
            }
        }
    }

    private static void bootstrapUserRoles() throws SQLException {

    }
    private static void bootstrapAddBooks() throws SQLException {
        Book book = new BookBuilder()
                .setAuthor("author 1")
                .setTitle("book 1")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .setStock(3L)
                .setPrice(122L)
                .setToSell(Boolean.TRUE)
                .build();
        Book book1 = new BookBuilder()
                .setAuthor("author 1")
                .setTitle("book 1")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .setStock(4L)
                .setPrice(1L)
                .setToSell(Boolean.TRUE)
                .build();
        Book book2 = new BookBuilder()
                .setAuthor("author 2")
                .setTitle("book 2")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .setStock(5L)
                .setPrice(2L)
                .setToSell(Boolean.TRUE)
                .build();
        Connection connection = new JDBCConnectionWrapper(PRODUCTION).getConnection();
        BookRepository<BookInterface> bookRepository = new BookRepositoryMySQL(connection);
        BookServiceImpl bookService = new BookServiceImpl(bookRepository);
        bookService.save(book);
        bookService.save(book1);
        bookService.save(book2);
    }
    private static void bootstrapAddDefaultUsers() throws SQLException {
        Role customerRole = rightsRolesRepository.findRoleByTitle(CUSTOMER);

        User customer = new UserBuilder()
                .setUsername("c@c.c")
                .setPassword(AuthenticationServiceImpl.hashPassword("Qwerty1234!"))
                .setRoles(Collections.singletonList(customerRole))
                .setMoney(100L)
                .build();
        Role employeeRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);

        User employee = new UserBuilder()
                .setUsername("e@e.e")
                .setPassword(AuthenticationServiceImpl.hashPassword("Qwerty1234!"))
                .setRoles(Collections.singletonList(employeeRole))
                .setMoney(100L)
                .build();
        Role administratorRole = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR);

        User administrator = new UserBuilder()
                .setUsername("a@a.a")
                .setPassword(AuthenticationServiceImpl.hashPassword("Qwerty1234!"))
                .setRoles(Collections.singletonList(administratorRole))
                .setMoney(100L)
                .build();
        Connection connection = new JDBCConnectionWrapper(PRODUCTION).getConnection();
        UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);

        userRepository.save(customer);
        userRepository.save(employee);
        userRepository.save(administrator);

    }
}