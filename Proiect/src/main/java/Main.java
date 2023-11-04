import database.JDBCConnectionWrapper;
import model.Book;
import model.builder.BookBuilder;
import repository.book.BookRepository;
import repository.book.BookRepositoryMock;
import repository.book.BookRepositoryMySQL;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        Book badBook = new BookBuilder()
                .setTitle("Fram Ursul Polar")
                .setAuthor("', ' ', null); DROP TABLE book; -- ")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .build();

        Book book = new BookBuilder()
                .setTitle("Fram Ursul Polar")
                .setAuthor("Cezar Petrescu")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .build();
        JDBCConnectionWrapper connectionWrapper = new JDBCConnectionWrapper("test_library");
        BookRepository bookRepository = new BookRepositoryMySQL(connectionWrapper.getConnection());
        bookRepository.save(book);
        bookRepository.save(book);
        bookRepository.save(book);
        bookRepository.save(book);

        System.out.println(bookRepository.findAll());
        System.out.println(bookRepository.findById(3L));

        System.out.println("-----------------------");
        bookRepository.save(badBook);
        System.out.println(bookRepository.findAll());

        System.out.println("-----------------------");
        //bookRepository.badSave(badBook);
        System.out.println(bookRepository.findAll());

    }
}