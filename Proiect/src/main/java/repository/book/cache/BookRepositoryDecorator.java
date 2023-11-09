package repository.book.cache;

import model.book.Book;
import model.book.BookInterface;
import model.book.ebook.EBook;
import model.builder.EBookBuilder;
import repository.book.BookRepository;
import repository.book.ebook.EBookRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BookRepositoryDecorator implements BookRepository {

    protected BookRepository decoratedRepository;

    public BookRepositoryDecorator(BookRepository bookRepository){
        this.decoratedRepository = bookRepository;
    }

    public static class BookRepositoryMySQL implements EBookRepository {
        private final Connection connection;
        public BookRepositoryMySQL(Connection connection){
            this.connection = connection;
        }

        public List<BookInterface> findAll() {
            String sql = "SELECT * FROM ebook;";
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
            String sql = "SELECT * from ebook where id=?;";
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
            String sql = "INSERT INTO ebook VALUES(null, ?, ?, ?, ?);";

            try{
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, book.getAuthor());
                preparedStatement.setString(2, book.getTitle());
                preparedStatement.setDate(3, Date.valueOf(book.getPublishedDate()));
                //preparedStatement.setString(4, book.getFormat());
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

        @Override
        public void removeAll() {
            String sql = "TRUNCATE TABLE ebook";
            try{
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        private EBook getBookFromResultSet(ResultSet resultSet) throws SQLException {
            return new EBookBuilder()
                    .setId(resultSet.getLong("id"))
                    .setTitle(resultSet.getString("title"))
                    .setAuthor(resultSet.getString("author"))
                    .setPublishedDate(new Date((resultSet.getDate("publishedDate")).getTime()).toLocalDate())
                    .setFormat(resultSet.getString("format"))
                    .build();
        }
    }
}
