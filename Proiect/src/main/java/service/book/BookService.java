package service.book;

import model.validator.Notification;

import java.util.List;

public interface BookService<T> {

    List<T> findAll();
    Notification<T> findById(Long id);

    boolean save(T book);

    int getAgeOfBook(Long id);
}
