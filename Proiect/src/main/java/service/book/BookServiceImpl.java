package service.book;

import model.book.Book;
import model.book.BookInterface;
import model.validator.Notification;
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
    public List<BookInterface> findAllSellableBooks(Boolean flag) {
        return bookRepository.findAllSellableBooks(flag);
    }


    public Notification<BookInterface> findById(Long id) {
        return bookRepository.findById(id);
                //.orElseThrow(() -> new IllegalArgumentException("Book with id: %d not found".formatted(id)));
    }

    public boolean save(BookInterface book) {
        return bookRepository.save(book);
    }

    @Override
    public Notification<Boolean> sell(BookInterface book) {
        return bookRepository.sell(book);
    }

    public int getAgeOfBook(Long id) {
        BookInterface book = this.findById(id).getResult();

        LocalDate now = LocalDate.now();

        return (int) ChronoUnit.YEARS.between(book.getPublishedDate(), now);
    }
}
