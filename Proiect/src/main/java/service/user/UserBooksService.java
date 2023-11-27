package service.user;

import model.User;
import model.book.BookInterface;
import model.validator.Notification;

import java.util.List;
import java.util.Optional;

public interface UserBooksService {
    List<BookInterface> findAll(User user);
    Notification<Boolean> sell(User user, BookInterface book);
    Notification<Boolean> buy(User user, BookInterface book);
    int deleteBook(User user, BookInterface book);
    void removeAll(User user);
}
