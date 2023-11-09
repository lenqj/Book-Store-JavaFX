import database.DatabaseConnectionFactory;
import database.JDBCConnectionWrapper;
import model.book.audiobook.AudioBook;
import model.book.Book;
import model.book.BookInterface;
import model.book.ebook.EBook;
import model.builder.AudioBookBuilder;
import model.builder.BookBuilder;
import model.builder.EBookBuilder;
import repository.Cache;
import repository.book.BookRepository;
import repository.book.cache.BookRepositoryCacheDecorator;
import repository.book.sql.BookRepositoryMySQL;

import java.time.LocalDate;

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

        AudioBook audioBook = new AudioBookBuilder()
                .setTitle("Fram Ursul Polar AUDIO")
                .setAuthor("Cezar Petrescu")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .setRunTime(10)
                .build();
        EBook ebook = new EBookBuilder()
                .setTitle("Fram Ursul Polar PDF")
                .setAuthor("Cezar Petrescu")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .setFormat("PDF")
                .build();

        JDBCConnectionWrapper connectionWrapper = DatabaseConnectionFactory.getConnectionWrapper(true);
        BookRepository<BookInterface> bookRepository = new BookRepositoryCacheDecorator<>(
                new BookRepositoryMySQL(connectionWrapper.getConnection()),
                new Cache<>()
        );

        //bookRepository.removeAll();

        bookRepository.save(book);
        bookRepository.save(audioBook);
        bookRepository.save(ebook);

        for(BookInterface bookPrint: bookRepository.findAll()){
            System.out.println(bookPrint);
        }

    }
}