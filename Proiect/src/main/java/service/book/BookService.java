package service.book;

import model.book.BookInterface;
import model.validator.Notification;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface BookService<T> {

    List<T> findAll();
    List<T> findAllSellableBooks(Boolean flag);
    Notification<T> findById(Long id);
    Notification<Boolean> save(T book);
    Notification<Boolean> sell(BookInterface book);
    Notification<Boolean> delete(BookInterface book);
    Notification<BookInterface> update(BookInterface book, String author, String title, Date publishedDate, Long stock, Long price);
    int getAgeOfBook(Long id);
}
