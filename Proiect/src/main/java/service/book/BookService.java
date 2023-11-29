package service.book;

import model.book.BookInterface;
import model.validator.Notification;

import java.util.List;

public interface BookService<T> {

    List<T> findAll();
    List<T> findAllSellableBooks();
    Notification<T> findById(Long id);
    boolean save(T book);
    Notification<Boolean> sell(BookInterface book);
    int getAgeOfBook(Long id);
}
