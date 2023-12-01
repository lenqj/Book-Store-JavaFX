package repository.user;

import model.User;
import model.book.BookInterface;
import model.validator.Notification;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public interface UserBooksRepository {

    Map<Long, Map.Entry<BookInterface, Date>> findAllSoldBooks(User user);
    Notification<Boolean> sell(User user, BookInterface book);
    Notification<Boolean> buy(User user, BookInterface book);
    Notification<Boolean> deleteBook(User user, Long bookID);
    void removeAll(User user);

}
