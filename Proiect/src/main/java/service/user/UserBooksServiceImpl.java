package service.user;

import model.User;
import model.book.BookInterface;
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
    public boolean save(User user, BookInterface book){
        return userBooksRepository.save(user, book);
    }

    @Override
    public int deleteBook(User user, BookInterface book) {
        return userBooksRepository.deleteBook(user, book);
    }

    public void removeAll(User user){
    }

}
