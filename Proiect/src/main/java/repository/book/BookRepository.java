package repository.book;

import model.book.BookInterface;

import java.util.List;
import java.util.Optional;

public interface BookRepository<T> {
    List<T> findAll();
    T findById(Long id);
    boolean save(T book);
    boolean badSave(T book);
    void removeAll();
}
