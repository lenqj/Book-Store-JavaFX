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

<<<<<<< Updated upstream
    public boolean validate() {
=======
    public void validate() {
>>>>>>> Stashed changes
        validateUserMoney(user);
        validateBookStock(book);
        validateUserBuyBook(user, book);
        return errors.isEmpty();
    }

    private void validateUserMoney(User user) {
        if (user.getMoney() <= 0) {
            errors.add("User doesn't have enough money!");
        }
    }

    private void validateBookStock(BookInterface book) {
<<<<<<< Updated upstream
        if (book == null) {
            errors.add("You must select 1 book.");
            return;
        }
        if (book.getStock() == 0) {
=======
        if (book.getStock() == null || book.getStock() == 0) {
>>>>>>> Stashed changes
            errors.add("Book doesn't have stock!");
        }
    }

    private void validateUserBuyBook(User user, BookInterface book) {
<<<<<<< Updated upstream
        if (book == null) {
            errors.add("You must select 1 book.");
            return;
        }
        if (user.getMoney() - book.getPrice() < 0) {
=======
        if (book.getPrice() == null || (user.getMoney() - book.getPrice() < 0)) {
>>>>>>> Stashed changes
            errors.add("User doesn't enough money to buy book!");
        }
    }

    public List<String> getErrors() {
        return errors;
    }
}
