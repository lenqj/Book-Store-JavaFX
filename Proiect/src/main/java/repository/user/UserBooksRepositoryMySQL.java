package repository.user;

import model.User;
import model.book.Book;
import model.book.BookInterface;
import model.builder.UserBuilder;
import model.validator.BookValidator;
import model.validator.Notification;
import repository.book.BookRepository;
import repository.security.RightsRolesRepository;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static database.Constants.Tables.USER_BOOKS;
import static database.Constants.Tables.USER_BOUGHT_BOOKS;

public class UserBooksRepositoryMySQL implements UserBooksRepository{
    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;
    private final BookRepository<BookInterface> bookRepository;
    private final UserRepository userRepository;
    public UserBooksRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository, BookRepository<BookInterface> bookRepository, UserRepository userRepository){
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }
    public Map<Long, Map.Entry<BookInterface, Date>> findAllSoldBooks(User user){
        Map<Long, Map.Entry<BookInterface, Date>> books = new TreeMap<>();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM " + USER_BOOKS + " where user_id = ?;");
            preparedStatement.setLong(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                books.put(resultSet.getLong("id"), new AbstractMap.SimpleEntry<>(getBookFromResultSet(resultSet).getResult(), resultSet.getDate("date"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    public Notification<Boolean> sell(User user, BookInterface book){
        Notification<Boolean> sellNotification = new Notification<>();
        try {
            BookValidator bookValidator = new BookValidator(user, book);
            if (bookValidator.validateToSell()){
                Notification<Boolean> sellBookNotification = bookRepository.sell(book);
                if(sellBookNotification.hasErrors()){
                    sellBookNotification.getErrors().forEach(sellNotification::addError);
                    return sellNotification;
                }else {
                    sellNotification.setResult(Boolean.TRUE);
                    PreparedStatement preparedStatement = connection
                            .prepareStatement("INSERT INTO " + USER_BOOKS + " values (null, ?, ?, ?);");
                    preparedStatement.setLong(1, user.getId());
                    preparedStatement.setLong(2, book.getId());
                    preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                    preparedStatement.executeUpdate();
                }
            }else{
                bookValidator.getErrors().forEach(sellNotification::addError);
                return sellNotification;
            }
        } catch (SQLException e) {
            sellNotification.addError("Something is wrong with the Database!");
            e.printStackTrace();
        }
        return sellNotification;
    }

    @Override
    public Notification<Boolean> buy(User user, BookInterface book) {
        Notification<Boolean> buyNotification = new Notification<>();
        try {
            BookValidator bookValidator = new BookValidator(user, book);
            if (bookValidator.validate()){
                Notification<BookInterface> bookUpdateNotification = bookRepository.updateStock(book, (book.getStock() - 1));
                if(bookUpdateNotification.hasErrors()){
                    bookUpdateNotification.getErrors().forEach(buyNotification::addError);
                    return buyNotification;
                }else{
                    book = bookUpdateNotification.getResult();
                }
                Notification<User> userUpdateNotification = userRepository.updateMoney(user, (user.getMoney() - book.getPrice()));
                if(userUpdateNotification.hasErrors()){
                    userUpdateNotification.getErrors().forEach(buyNotification::addError);
                    return buyNotification;
                }else{
                    user = userUpdateNotification.getResult();
                }
                PreparedStatement preparedStatement = connection
                        .prepareStatement("INSERT INTO " + USER_BOUGHT_BOOKS + " values (null, ?, ?, ?);");
                preparedStatement.setLong(1, user.getId());
                preparedStatement.setLong(2, book.getId());
                preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                int rs = preparedStatement.executeUpdate();
                if (rs == 0){
                    buyNotification.addError("Can't update this book!");
                }else {
                    buyNotification.setResult(Boolean.TRUE);
                }
            }else{
                bookValidator.getErrors().forEach(buyNotification::addError);
                return buyNotification;
            }
        } catch (SQLException e) {
            buyNotification.addError("Something is wrong with the Database!");
            e.printStackTrace();
        }
        return buyNotification;
    }

    @Override
    public Notification<Boolean> deleteBook(User user, Long bookID) {
        Notification<Boolean> deleteBookNotification = new Notification<>();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM " + USER_BOOKS + " WHERE user_id = ? and id = ?;");
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setLong(2, bookID);
            if(preparedStatement.executeUpdate() == 1){
                deleteBookNotification.setResult(Boolean.TRUE);
            }else{
                deleteBookNotification.addError("Something went wrong!");
                return deleteBookNotification;
            }
        } catch (SQLException e) {
            deleteBookNotification.addError("Something is wrong with the Database!");
        }
        return deleteBookNotification;
    }

    public void removeAll(User user){
    }
    private Notification<BookInterface> getBookFromResultSet(ResultSet resultSet) throws SQLException {
            return bookRepository.findById(resultSet.getLong("book_id"));
    }
    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        return new UserBuilder()
                .setId(resultSet.getLong("id"))
                .setUsername(resultSet.getString("username"))
                .setPassword(resultSet.getString("password"))
                .setRoles(rightsRolesRepository.findRolesForUser(resultSet.getLong("id")))
                .setMoney(resultSet.getLong("money"))
                .build();
    }
}
