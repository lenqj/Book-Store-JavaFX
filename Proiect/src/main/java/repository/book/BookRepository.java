package repository.book;

import model.book.BookInterface;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<BookInterface> findAll();
    Optional<BookInterface> findById(Long id);
    boolean save(BookInterface book);
    boolean badSave(BookInterface book);
    void removeAll();
}
