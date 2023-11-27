package service.user;

import model.User;
import model.book.Book;
import model.book.BookInterface;
import model.validator.Notification;
import repository.book.BookRepository;
import repository.user.UserBooksRepository;

import java.util.List;
import java.util.Optional;

public class UserBooksServiceImpl implements UserBooksService{
    private final UserBooksRepository userBooksRepository;

    public UserBooksServiceImpl(UserBooksRepository userBooksRepository) {
        this.userBooksRepository = userBooksRepository;
    }
    public List<BookInterface> findAll(User user){

        return userBooksRepository.findAll(user);
    }
<<<<<<< Updated upstream
    public Notification<Boolean> sell(User user, BookInterface book){
        return userBooksRepository.sell(user, book);
=======
    public Notification<Boolean> save(User user, BookInterface book){
        return userBooksRepository.save(user, book);
>>>>>>> Stashed changes
    }

    @Override
    public Notification<Boolean> buy(User user, BookInterface book) {
        return userBooksRepository.buy(user, book);
    }

    @Override
    public int deleteBook(User user, BookInterface book) {
        return userBooksRepository.deleteBook(user, book);
    }

    public void removeAll(User user){
    }

}
