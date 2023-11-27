package repository.user;

import model.User;
import model.book.BookInterface;
import model.builder.UserBuilder;
import repository.book.BookRepository;
import repository.security.RightsRolesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<BookInterface> findAll(User user){
        List<BookInterface> books = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM " + USER_BOOKS + " where user_id = ?;");
            preparedStatement.setLong(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                books.add(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    public boolean save(User user, BookInterface book){
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO " + USER_BOOKS + " values (null, ?, ?, ?);");
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setLong(2, book.getId());
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            int rowsInserted = preparedStatement.executeUpdate();

            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean buy(User user, BookInterface book) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO " + USER_BOUGHT_BOOKS + " values (null, ?, ?, ?);");
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setLong(2, book.getId());
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            int rowsInserted = preparedStatement.executeUpdate();
            book = bookRepository.updateStock(book, (book.getStock() - 1));
            user = userRepository.updateMoney(user, (user.getMoney() - book.getPrice()));
            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int deleteBook(User user, BookInterface book) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM " + USER_BOOKS + " WHERE user_id = ? and book_id = ?;");
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setLong(2, book.getId());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void removeAll(User user){
    }
    private BookInterface getBookFromResultSet(ResultSet resultSet) throws SQLException {
            return bookRepository.findById(resultSet.getLong(3));
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
