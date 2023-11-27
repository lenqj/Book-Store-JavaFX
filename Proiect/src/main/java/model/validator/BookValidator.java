package model.validator;

import model.User;
import model.book.BookInterface;

import java.util.ArrayList;
import java.util.List;

public class BookValidator {
    private final List<String> errors;
    private final User user;
    private final BookInterface book;
    public BookValidator(User user, BookInterface book) {
        this.user = user;
        this.book = book;
        this.errors = new ArrayList<>();
    }

    public boolean validate() {
        validateUserMoney(user);
        validateBookStock(book);
        validateUserBuyBook(user, book);
        return errors.isEmpty();
    }

    private void validateUserMoney(User user) {
        final Long response = user.getMoney();
        if (response <= 0) {
            errors.add("User doesn't have money!");
        }
    }

    private void validateBookStock(BookInterface book) {
        if (book.getStock() == null || book.getStock() == 0) {
            errors.add("Book doesn't have stock!");
        }
    }

    private void validateUserBuyBook(User user, BookInterface book) {
        if (book.getPrice() == null || (user.getMoney() - book.getPrice() < 0)) {
            errors.add("User doesn't enough money to buy book!");
        }
    }

    public List<String> getErrors() {
        return errors;
    }
}
