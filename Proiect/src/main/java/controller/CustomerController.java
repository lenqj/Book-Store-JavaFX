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
import model.validator.Notification;
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
    private Notification<Boolean> customerNotification;
    public CustomerController(CustomerView customerView) {
        this.customerView = customerView;
        customerNotification = new Notification<>();
        customerView.addSellBookButtonButtonListener(new SellBookButtonListener());
        customerView.addSoldBooksButtonButtonListener(new SoldBookButtonListener());
        customerView.addLogoutButtonListener(new LogoutButtonListener());
        customerView.addBuyBookButtonButtonListener(new BuyBookButtonListener());
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
            customerNotification = ComponentFactory.getUserBooksService().save(loginController.getLoginNotification().getResult(), book);
            BookValidator bookValidator = new BookValidator(loginController.getLoginNotification().getResult(), book);
            bookValidator.validate();
            bookValidator.getErrors().forEach(customerNotification::addError);
            if (customerNotification.hasErrors()){
                customerView.setTextSellBook("You successfully sold: " + book.toString());
            }else {
                customerView.setTextSellBook(customerNotification.getFormattedErrors());
            }
        }
    }

    private class BuyBookButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            BookInterface book = customerView.getSelectedBook();
            customerNotification = ComponentFactory.getUserBooksService().buy(loginController.getLoginNotification().getResult(), book);
            BookValidator bookValidator = new BookValidator(loginController.getLoginNotification().getResult(), book);
            bookValidator.validate();
            bookValidator.getErrors().forEach(customerNotification::addError);
            if (customerNotification.hasErrors()) {
                customerView.setTextSellBook("You successfully bought: " + book.toString());
                customerView.setTableBookList(ComponentFactory.getBookService().findAll());
                customerView.setMoneyText("Money: " + ComponentFactory.getUserRepository().findById(loginController.getLoginNotification().getResult().getId()).getMoney());
            }else {
                customerView.setTextSellBook(customerNotification.getFormattedErrors());
            }
        }
    }


}
