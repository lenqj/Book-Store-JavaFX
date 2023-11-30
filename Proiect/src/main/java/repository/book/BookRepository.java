package repository.book;

import model.book.BookInterface;
import model.validator.Notification;

import java.util.List;
import java.util.Optional;

public interface BookRepository<T> {
    List<T> findAll();
    List<T> findAllSellableBooks(Boolean flag);
    Notification<T> findById(Long id);
    boolean save(T book);
    boolean badSave(T book);
    void removeAll();
    Notification<T> updateStock(T book, Long stock);
    void updatePrice(T book, Long Price);
    Notification<Boolean> sell(BookInterface book);
}
