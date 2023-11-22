package repository.user;

import model.User;
import model.book.Book;
import model.book.BookInterface;

import java.util.*;

public interface UserBooksRepository {

    List<BookInterface> findAll(User user);
    boolean save(User user, BookInterface book);
    void removeAll(User user);

}
