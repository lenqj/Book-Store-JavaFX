package service.book;

import model.book.BookInterface;
import repository.book.BookRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class BookServiceImpl implements BookService<BookInterface>{
    private final BookRepository<BookInterface> bookRepository;

    public BookServiceImpl(BookRepository<BookInterface> bookRepository){
        this.bookRepository = bookRepository;
    }

    public List<BookInterface> findAll() {
        return bookRepository.findAll();
    }

    public BookInterface findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book with id: %d not found".formatted(id)));
    }

    public boolean save(BookInterface book) {
        return bookRepository.save(book);
    }

    public int getAgeOfBook(Long id) {
        BookInterface book = this.findById(id);

        LocalDate now = LocalDate.now();

        return (int) ChronoUnit.YEARS.between(book.getPublishedDate(), now);
    }
}
