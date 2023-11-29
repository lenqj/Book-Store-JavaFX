package repository.user;

import model.User;
import model.book.BookInterface;
import model.validator.Notification;

import java.util.*;

public interface UserBooksRepository {

    Map<Long, BookInterface> findAll(User user);
    Notification<Boolean> sell(User user, BookInterface book);
    Notification<Boolean> buy(User user, BookInterface book);
    int deleteBook(User user, Long bookID);
    void removeAll(User user);

}
