package service;

import model.book.BookInterface;

import java.util.List;

public interface BookService<T> {

    List<T> findAll();
    T findById(Long id);

    boolean save(T book);

    int getAgeOfBook(Long id);
}
