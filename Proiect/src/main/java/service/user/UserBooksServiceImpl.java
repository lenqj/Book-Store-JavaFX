package service.user;

import model.User;
import model.book.Book;
import model.book.BookInterface;
import model.validator.Notification;
import repository.book.BookRepository;
import repository.user.UserBooksRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserBooksServiceImpl implements UserBooksService{
    private final UserBooksRepository userBooksRepository;
    public UserBooksServiceImpl(UserBooksRepository userBooksRepository) {
        this.userBooksRepository = userBooksRepository;
    }
    public Map<Long, Map.Entry<BookInterface, Date>> findAllSoldBooks(User user){
        return userBooksRepository.findAllSoldBooks(user);
    }
    public Notification<Boolean> sell(User user, BookInterface book){
        return userBooksRepository.sell(user, book);
    }
    @Override
    public Notification<Boolean> buy(User user, BookInterface book) {
        return userBooksRepository.buy(user, book);
    }
    public Notification<Boolean> deleteBook(User user, Long bookID) {
        return userBooksRepository.deleteBook(user, bookID);
    }
    public void removeAll(User user){
    }

}
