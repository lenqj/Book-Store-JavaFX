package repository.book.ebook;

import model.book.ebook.EBook;
import model.book.ebook.EBookInterface;
import model.builder.EBookBuilder;
import repository.book.BookRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class EBookRepository implements BookRepository<EBookInterface> {
    private final Connection connection;
    public EBookRepository(Connection connection){
        this.connection = connection;
    }
    public List<EBookInterface> findAll() {
        String sql = "SELECT * FROM ebook;";
        List<EBookInterface> books = new ArrayList<>();
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
    public Optional<EBookInterface> findById(Long bookID) {
        String sql = "SELECT * from ebook where id=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, bookID);
            ResultSet resultSet = preparedStatement.executeQuery();
            EBookInterface returnBook = new EBook();
            while (resultSet.next()) {
                returnBook = getBookFromResultSet(resultSet);
            }
            return Optional.ofNullable(returnBook);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    public boolean save(EBookInterface book) {
        String sql = "INSERT INTO ebook VALUES(null, ?, ?, ?, ?);";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setDate(3, Date.valueOf(book.getPublishedDate()));
            preparedStatement.setString(4, book.getFormat());
            int rowsInserted = preparedStatement.executeUpdate();

            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean badSave(EBookInterface book) {
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
        String sql = "TRUNCATE TABLE ebook";
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private EBookInterface getBookFromResultSet(ResultSet resultSet) throws SQLException {
        return new EBookBuilder()
                .setId(resultSet.getLong("id"))
                .setTitle(resultSet.getString("title"))
                .setAuthor(resultSet.getString("author"))
                .setPublishedDate(new Date((resultSet.getDate("publishedDate")).getTime()).toLocalDate())
                .setFormat(resultSet.getString("format"))
                .build();
    }
}