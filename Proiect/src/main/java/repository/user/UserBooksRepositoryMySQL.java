package repository.user;

import model.User;
import model.book.Book;
import model.book.BookInterface;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class UserBooksRepositoryMySQL implements UserBooksRepository{
    private final Connection connection;
    public UserBooksRepositoryMySQL(Connection connection){
        this.connection = connection;
    }
    public List<BookInterface> findAll(User user){
        return null;
    }
    public boolean save(User user, BookInterface book){
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO `user_books` values (null, ?, ?, ?);");
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
    public void removeAll(User user){
    }
}
