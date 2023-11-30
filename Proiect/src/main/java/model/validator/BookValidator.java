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

    public boolean validateToSell() {
        if (book == null) {
            errors.add("You must select 1 book.");
        }
        return errors.isEmpty();
    }

    private void validateUserMoney(User user) {
        if (user.getMoney() <= 0) {
            errors.add("User doesn't have enough money!");
        }
    }

    private void validateBookStock(BookInterface book) {
        if (book == null) {
            errors.add("You must select 1 book.");
            return;
        }
        if (book.getStock() == 0) {
            errors.add("Book doesn't have stock!");
        }
    }

    private void validateUserBuyBook(User user, BookInterface book) {
        if (book == null) {
            errors.add("You must select 1 book.");
            return;
        }
        if (user.getMoney() - book.getPrice() < 0) {
            errors.add("User doesn't enough money to buy book!");
        }
    }

    public List<String> getErrors() {
        return errors;
    }
}
