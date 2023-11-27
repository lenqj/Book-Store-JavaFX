package repository.user;

import model.User;
import model.book.BookInterface;
import model.validator.Notification;

import java.util.*;

public interface UserBooksRepository {

    List<BookInterface> findAll(User user);
<<<<<<< Updated upstream
    Notification<Boolean> sell(User user, BookInterface book);
=======
    Notification<Boolean> save(User user, BookInterface book);
>>>>>>> Stashed changes
    Notification<Boolean> buy(User user, BookInterface book);
    int deleteBook(User user, BookInterface book);
    void removeAll(User user);

}
