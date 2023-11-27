package repository.book.audiobook;

import model.book.audiobook.AudioBook;
import model.book.audiobook.AudioBookInterface;
import model.builder.AudioBookBuilder;
import repository.book.BookRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class AudioBookRepository implements BookRepository<AudioBookInterface> {
    private final Connection connection;
    public AudioBookRepository(Connection connection){
        this.connection = connection;
    }
    public List<AudioBookInterface> findAll() {
        String sql = "SELECT * FROM audiobook;";
        List<AudioBookInterface> books = new ArrayList<>();
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
    public AudioBookInterface findById(Long bookID) {
        String sql = "SELECT * from audiobook where id=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, bookID);
            ResultSet resultSet = preparedStatement.executeQuery();
            AudioBookInterface returnBook = new AudioBook();
            while (resultSet.next()) {
                returnBook = getBookFromResultSet(resultSet);
            }
            return returnBook;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean save(AudioBookInterface book) {
        String sql = "INSERT INTO audiobook VALUES(null, ?, ?, ?, ?);";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setDate(3, Date.valueOf(book.getPublishedDate()));
            preparedStatement.setInt(4, book.getRunTime());
            int rowsInserted = preparedStatement.executeUpdate();

            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean badSave(AudioBookInterface book) {
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

    @Override
    public AudioBookInterface updateStock(AudioBookInterface book, Long stock) {

        return book;
    }

    @Override
    public void updatePrice(AudioBookInterface book, Long Price) {

    }

    private AudioBookInterface getBookFromResultSet(ResultSet resultSet) throws SQLException {
        return new AudioBookBuilder()
                .setId(resultSet.getLong("id"))
                .setTitle(resultSet.getString("title"))
                .setAuthor(resultSet.getString("author"))
                .setPublishedDate(new Date((resultSet.getDate("publishedDate")).getTime()).toLocalDate())
                .setRunTime(resultSet.getInt("runTime"))
                .build();
    }
}