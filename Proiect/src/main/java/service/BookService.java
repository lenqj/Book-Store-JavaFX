package service;

import model.book.BookInterface;

import java.util.List;

public interface BookService {

    List<BookInterface> findAll();
    BookInterface findById(Long id);

    boolean save(BookInterface book);

    int getAgeOfBook(Long id);
}
