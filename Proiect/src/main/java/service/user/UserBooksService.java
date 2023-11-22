package service.user;

import model.User;
import model.book.BookInterface;

import java.util.List;

public interface UserBooksService {
    List<BookInterface> findAll(User user);
    boolean save(User user, BookInterface book);
    void removeAll(User user);
}
