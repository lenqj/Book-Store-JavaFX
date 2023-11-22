package service.user;

import model.User;
import model.book.BookInterface;
import repository.user.UserBooksRepository;

import java.util.List;

public class UserBooksServiceImpl implements UserBooksService{
    private final UserBooksRepository userBooksRepository;

    public UserBooksServiceImpl(UserBooksRepository userBooksRepository) {
        this.userBooksRepository = userBooksRepository;
    }
    public List<BookInterface> findAll(User user){

        return null;
    }
    public boolean save(User user, BookInterface book){
        return userBooksRepository.save(user, book);
    }
    public void removeAll(User user){
    }

}
