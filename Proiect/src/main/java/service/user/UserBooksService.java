package service.user;

import model.User;
import model.book.BookInterface;

import java.util.List;
import java.util.Optional;

public interface UserBooksService {
    List<BookInterface> findAll(User user);
    boolean save(User user, BookInterface book);
    int deleteBook(User user, BookInterface book);
    void removeAll(User user);
}
