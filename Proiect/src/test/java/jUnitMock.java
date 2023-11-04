import model.Book;
import model.builder.BookBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.book.BookRepository;
import repository.book.BookRepositoryMock;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class jUnitMock {
    Book book;
    Book book1;
    Book book2;
    Book book3;
    Book book4;
    BookRepository bookRepository;
    @BeforeEach
    void setUp(){
        bookRepository = new BookRepositoryMock();
        this.book = new BookBuilder()
                .setId(1L)
                .setTitle("TITLU1")
                .setAuthor("AUTOR1")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .build();
        this.book1 = new BookBuilder()
                .setId(2L)
                .setTitle("TITLU2")
                .setAuthor("AUTOR2")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .build();
        this.book2 = new BookBuilder()
                .setId(3L)
                .setTitle("TITLU3")
                .setAuthor("AUTOR3")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .build();
        this.book3 = new BookBuilder()
                .setId(4L)
                .setTitle("TITLU4")
                .setAuthor("AUTOR4")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .build();
        this.book4 = new BookBuilder()
                .setId(5L)
                .setTitle("TITLU5")
                .setAuthor("AUTOR5")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .build();
        bookRepository.save(book);
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        bookRepository.save(book4);
    }
    @Test
    void testSave() {
        bookRepository.removeAll();
        assert(bookRepository.save(book));
        assert(bookRepository.save(book1));
        assert(bookRepository.save(book2));
        assert(bookRepository.save(book3));
        assert(bookRepository.save(book4));
    }
    @Test
    void testFindById() {
        Optional<Book> newBook = bookRepository.findById(3L);
        assertEquals("Optional[#3 - Book author: AUTOR3 | title: TITLU3 | Published Date: 2010-06-02.]", newBook.toString(), "Findbyid work");
    }

    @Test
    void testFindAll() {
        List<Book> allBooks = bookRepository.findAll();
        
        //assert(allBooks)
    }
}
