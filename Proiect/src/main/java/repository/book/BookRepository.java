package repository.book;

import model.book.BookInterface;
import model.validator.Notification;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookRepository<T> {
    List<T> findAll();
    List<T> findAllSellableBooks(Boolean flag);
    Notification<T> findById(Long id);
    Notification<Boolean> save(T book);
    boolean badSave(T book);
    void removeAll();
    Notification<T> updateStock(T book, Long stock);
    void updatePrice(T book, Long Price);
    Notification<Boolean> sell(BookInterface book);
    Notification<Boolean> delete(BookInterface book);
    Notification<BookInterface> update(BookInterface book, String author, String title, Date publishedDate, Long stock, Long price);
}
