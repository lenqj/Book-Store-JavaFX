package model.validator;

import model.User;
import model.book.BookInterface;
import repository.book.BookRepository;
import repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class BookValidator {
    private final List<String> errors = new ArrayList<>();
    private final User user;
    private final BookInterface book;
    public BookValidator(User user, BookInterface book) {
        this.user = user;
        this.book = book;
    }

    public void validate() {
        errors.clear();
        validateUserMoney(user);
        validateBookStock(book);
        validateUserBuyBook(user, book);
    }

    private void validateUserMoney(User user) {
        final Long response = user.getMoney();
        if (response <= 0) {
            errors.add("User doesn't have money!");
        }
    }

    private void validateBookStock(BookInterface book) {
        final Long response = book.getStock();
        if (response <= 0) {
            errors.add("Book doesn't have stock!");
        }
    }

    private void validateUserBuyBook(User user, BookInterface book) {
        if (user.getMoney() - book.getPrice() < 0) {
            errors.add("User doesn't enough money to buy book!");
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}
