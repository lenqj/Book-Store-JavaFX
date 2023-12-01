package repository.book.sql;

import model.Role;
import model.User;
import model.validator.Notification;
import repository.book.BookRepository;
import model.book.Book;
import model.book.BookInterface;
import model.builder.BookBuilder;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.BOOK;
import static database.Constants.Tables.USER;

public class BookRepositoryMySQL implements BookRepository<BookInterface> {
    private final Connection connection;
    public BookRepositoryMySQL(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<BookInterface> findAll() {
        String sql = "SELECT * FROM " + BOOK + ";";
        List<BookInterface> books = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                books.add(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    public List<BookInterface> findAllSellableBooks(Boolean flag) {
        String sql = "SELECT * FROM " + BOOK + " where toSell = " + flag + ";";
        List<BookInterface> books = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                books.add(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    @Override
    public Notification<BookInterface> findById(Long bookID) {
        Notification<BookInterface> bookInterfaceNotification = new Notification<>();
        String sql = "SELECT * from " + BOOK + " where id = ?;";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, bookID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                BookInterface book = getBookFromResultSet(resultSet);
                bookInterfaceNotification.setResult(book);
            } else {
                bookInterfaceNotification.addError("Invalid username or password!");
                return bookInterfaceNotification;
            }

        } catch (SQLException e) {
            bookInterfaceNotification.addError("Something is wrong with the Database!");
        }
        return bookInterfaceNotification;
    }

    public Notification<Boolean> save(BookInterface book) {
        Notification<Boolean> saveNotification = new Notification<>();
        String sql = "INSERT INTO " + BOOK + " VALUES(null, ?, ?, ?, ?, ?, ?);";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setObject(3, book.getPublishedDate());
            preparedStatement.setObject(4, book.getStock());
            preparedStatement.setObject(5, book.getPrice());
            preparedStatement.setObject(6, book.getToSell());

            if(preparedStatement.executeUpdate() == 1){
                saveNotification.setResult(Boolean.TRUE);
            }else{
                saveNotification.addError("Something went wrong.");
                return saveNotification;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            saveNotification.addError("Something is wrong with the Database!");
        }
        return saveNotification;
    }

    public boolean badSave(BookInterface book) {
        String authorSave = book.getAuthor();
        String titleSave = book.getTitle();
        Date dateSave = Date.valueOf(book.getPublishedDate());
        String sql = "INSERT INTO book VALUES(" + null + "," + "'" + authorSave + "'" + ", " + "'" + titleSave + "'" + ", " + "'" + dateSave + "'" + ");";
        try{
            Statement statement = connection.createStatement();
            int rowsInserted = statement.executeUpdate(sql);

            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        String sql = "TRUNCATE TABLE book";
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Notification<BookInterface> updateStock(BookInterface book, Long stock) {
        String sql = "UPDATE " + BOOK + " SET `stock`= ? WHERE id = ?;";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, stock);
            preparedStatement.setLong(2, book.getId());
            preparedStatement.executeUpdate();
            return findById(book.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updatePrice(BookInterface book, Long price) {

    }

    @Override
    public Notification<Boolean> sell(BookInterface book) {
        String sql = "UPDATE " + BOOK + " SET `toSell`= ? WHERE id = ?;";
        Notification<Boolean> sellNotification = new Notification<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBoolean(1, !book.getToSell());
            preparedStatement.setLong(2, book.getId());
            preparedStatement.executeUpdate();
            sellNotification.setResult(Boolean.TRUE);
            return sellNotification;
        } catch (SQLException e) {
            sellNotification.addError("Something is wrong with the Database!");
            e.printStackTrace();
        }
        return sellNotification;
    }

    @Override
    public Notification<Boolean> delete(BookInterface book) {
        String sql = "DELETE FROM " + BOOK + " WHERE id = ?;";
        Notification<Boolean> deleteNotification = new Notification<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, book.getId());
            if(preparedStatement.executeUpdate() == 1) {
                deleteNotification.setResult(Boolean.TRUE);
            }else{
                deleteNotification.addError("Something went wrong!");
                return deleteNotification;
            }
        } catch (SQLException e) {
            deleteNotification.addError("Something is wrong with the Database!");
            e.printStackTrace();
        }
        return deleteNotification;
    }

    public Notification<BookInterface> update(BookInterface book, String author, String title, Date publishedDate, Long stock, Long price) {
        Notification<BookInterface> updateBookNotification = new Notification<>();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE " + BOOK + " SET `author` = COALESCE(?, `author`), `title` = COALESCE(?, `title`), `publishedDate` = COALESCE(?, `publishedDate`), `stock` = COALESCE(?, `stock`), `price` = COALESCE(?, `price`) WHERE id = ?;");
            preparedStatement.setString(1, author);
            preparedStatement.setString(2, title);
            preparedStatement.setDate(3, publishedDate);
            preparedStatement.setObject(4, stock);
            preparedStatement.setObject(5, price);
            preparedStatement.setLong(6, book.getId());
            if(preparedStatement.executeUpdate() == 1){
                Notification<BookInterface> findBookNotification = findById(book.getId());
                if(findBookNotification.hasErrors()){
                    findBookNotification.getErrors().forEach(updateBookNotification::addError);
                    return updateBookNotification;
                }else {
                    updateBookNotification.setResult(findBookNotification.getResult());
                }
            }else{
                updateBookNotification.addError("User can't be updated.");
                return updateBookNotification;
            }
        } catch (SQLException e) {
            updateBookNotification.addError("Something is wrong with the Database!");
            e.printStackTrace();
        }
        return updateBookNotification;
    }

    private BookInterface getBookFromResultSet(ResultSet resultSet) throws SQLException {
        return new BookBuilder()
                .setId(resultSet.getLong("id"))
                .setTitle(resultSet.getString("title"))
                .setAuthor(resultSet.getString("author"))
                .setPublishedDate(new java.sql.Date((resultSet.getDate("publishedDate")).getTime()).toLocalDate())
                .setStock(resultSet.getLong("stock"))
                .setPrice(resultSet.getLong("price"))
                .setToSell(resultSet.getBoolean("toSell"))
                .build();
    }
}
