package repository.user;

import model.User;
import model.book.BookInterface;
import model.validator.Notification;

import java.util.*;

public interface UserBooksRepository {

    List<BookInterface> findAll(User user);
    Notification<Boolean> save(User user, BookInterface book);
    Notification<Boolean> buy(User user, BookInterface book);
    int deleteBook(User user, BookInterface book);
    void removeAll(User user);

}
