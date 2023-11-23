package repository.book.sql;

import repository.book.BookRepository;
import model.book.Book;
import model.book.BookInterface;
import model.builder.BookBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static database.Constants.Tables.BOOK;

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

    @Override
    public BookInterface findById(Long bookID) {
        String sql = "SELECT * from " + BOOK + " where id = ?;";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, bookID);
            ResultSet resultSet = preparedStatement.executeQuery();
            BookInterface returnBook = new Book();
            while(resultSet.next()){
                returnBook = getBookFromResultSet(resultSet);
            }
            return returnBook;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean save(BookInterface book) {
        String sql = "INSERT INTO " + BOOK + " VALUES(null, ?, ?, ?);";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setDate(3, Date.valueOf(book.getPublishedDate()));
            int rowsInserted = preparedStatement.executeUpdate();

            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
    private BookInterface getBookFromResultSet(ResultSet resultSet) throws SQLException {
        return new BookBuilder()
                .setId(resultSet.getLong("id"))
                .setTitle(resultSet.getString("title"))
                .setAuthor(resultSet.getString("author"))
                .setPublishedDate(new java.sql.Date((resultSet.getDate("publishedDate")).getTime()).toLocalDate())
                .build();
    }
}
