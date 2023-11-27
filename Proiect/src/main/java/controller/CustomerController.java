package controller;

import database.JDBCConnectionWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import launcher.ComponentFactory;
import model.User;
import model.book.Book;
import model.book.BookInterface;
import model.validator.BookValidator;
import model.validator.UserValidator;
import repository.book.BookRepository;
import repository.book.sql.BookRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserBooksRepository;
import repository.user.UserBooksRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;
import service.user.UserBooksService;
import service.user.UserBooksServiceImpl;
import view.CustomerView;
import view.LoginView;
import view.SoldBooksView;

import java.sql.Connection;
import java.util.EventListener;
import java.util.List;

import static database.Constants.Schemas.PRODUCTION;
import static launcher.ComponentFactory.loginController;

public class CustomerController {

    private final CustomerView customerView;
    private User user;
    public CustomerController(CustomerView customerView) {
        this.customerView = customerView;
        //customerView.setTableBookList(ComponentFactory.getBookService().findAll());
        customerView.addSellBookButtonButtonListener(new SellBookButtonListener());
        customerView.addSoldBooksButtonButtonListener(new SoldBookButtonListener());
        customerView.addLogoutButtonListener(new LogoutButtonListener());
        customerView.addBuyBookButtonButtonListener(new BuyBookButtonListener());
    }
    public void setUser(User user){
        this.user = user;
    }
    private static class LogoutButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            ComponentFactory.getCustomerView().showStage(false);
            ComponentFactory.getLoginView().clearTexts();
            ComponentFactory.getLoginView().showStage(true);
        }
    }

    private class SoldBookButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            ComponentFactory.getCustomerView().showStage(false);
            ComponentFactory.getSoldBooksView().showStage(true);
        }
    }

    private class SellBookButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            BookInterface book = customerView.getSelectedBook();

            if (book != null && ComponentFactory.getUserBooksService().save(user, book)){
                customerView.setTextSellBook(book.toString());
            }
        }
    }

    private class BuyBookButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            BookInterface book = customerView.getSelectedBook();
            BookValidator bookValidator = new BookValidator(loginController.getLoginNotification().getResult(), book);
            bookValidator.validate();
            final List<String> errors = bookValidator.getErrors();
            if (errors.isEmpty()) {
                if (book != null && ComponentFactory.getUserBooksService().buy(loginController.getLoginNotification().getResult(), book)) {
                    customerView.setTextSellBook(book.toString());
                    customerView.setTableBookList(ComponentFactory.getBookService().findAll());
                    customerView.setMoneyText("Money: " + ComponentFactory.getUserRepository().findById(1L).getMoney());
                }
            }else {
                customerView.setTextSellBook(bookValidator.getFormattedErrors());
            }
        }
    }


}
