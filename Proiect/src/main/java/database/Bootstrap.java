package database;

import model.book.BookInterface;
import repository.book.BookRepository;
import repository.book.sql.BookRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import model.book.Book;
import model.builder.BookBuilder;
import service.book.BookServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static database.Constants.Rights.RIGHTS;
import static database.Constants.Roles.ROLES;
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
    }

    private static void dropAll() throws SQLException {
        for (String schema : SCHEMAS) {
            System.out.println("Dropping all tables in schema: " + schema);

            Connection connection = new JDBCConnectionWrapper(schema).getConnection();
            Statement statement = connection.createStatement();

            String[] dropStatements = {
                    "DROP TABLE `user_books`;",
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
                .build();
        Book book1 = new BookBuilder()
                .setAuthor("author 1")
                .setTitle("book 1")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .build();
        Book book2 = new BookBuilder()
                .setAuthor("author 2")
                .setTitle("book 2")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .build();
        Connection connection = new JDBCConnectionWrapper(PRODUCTION).getConnection();
        BookRepository<BookInterface> bookRepository = new BookRepositoryMySQL(connection);
        BookServiceImpl bookService = new BookServiceImpl(bookRepository);
        bookService.save(book);
        bookService.save(book1);
        bookService.save(book2);
    }
}