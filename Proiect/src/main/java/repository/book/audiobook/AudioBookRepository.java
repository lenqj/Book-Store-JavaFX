package repository.book;

import model.book.audiobook.AudioBook;
import model.book.Book;
import model.book.BookInterface;
import model.builder.AudioBookBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class AudioBookRepository implements BookRepository {
    private final Connection connection;
    public AudioBookRepository(Connection connection){
        this.connection = connection;
    }
    public List<BookInterface> findAll() {
        String sql = "SELECT * FROM audiobook;";
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
    public Optional<BookInterface> findById(Long bookID) {
        String sql = "SELECT * from audiobook where id=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, bookID);
            ResultSet resultSet = preparedStatement.executeQuery();
            BookInterface returnBook = new Book();
            while (resultSet.next()) {
                returnBook = getBookFromResultSet(resultSet);
            }
            return Optional.ofNullable(returnBook);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    public boolean save(BookInterface book) {
        String sql = "INSERT INTO audiobook VALUES(null, ?, ?, ?, ?);";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setDate(3, Date.valueOf(book.getPublishedDate()));
            //preparedStatement.setInt(4, book.getRunTime());
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
        String sql = "INSERT INTO book VALUES(" + null + ", " + "'" + authorSave + "'" + ", " + "'" + titleSave + "'" + ", " + "'" + dateSave + "'" + ");";

        try{
            Statement statement = connection.createStatement();
            int rowsInserted = statement.executeUpdate(sql);

            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void removeAll() {
        String sql = "TRUNCATE TABLE audiobook";
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private AudioBook getBookFromResultSet(ResultSet resultSet) throws SQLException {
        return new AudioBookBuilder()
                .setId(resultSet.getLong("id"))
                .setTitle(resultSet.getString("title"))
                .setAuthor(resultSet.getString("author"))
                .setPublishedDate(new Date((resultSet.getDate("publishedDate")).getTime()).toLocalDate())
                .setRunTime(resultSet.getInt("runTime"))
                .build();
    }
}