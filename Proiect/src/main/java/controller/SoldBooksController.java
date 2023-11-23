package controller;

import database.JDBCConnectionWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.User;
import model.book.Book;
import model.book.BookInterface;
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

public class SoldBooksController {

    private final SoldBooksView soldBooksView;
    private final UserBooksService userBooksService;
    private final User user;

    public SoldBooksController(SoldBooksView soldBooksView, UserBooksService userBooksService, User user) {
        this.soldBooksView = soldBooksView;
        this.userBooksService = userBooksService;
        this.user = user;
        soldBooksView.addBackButtonListener(new BackButtonButtonListener());
        soldBooksView.addDeleteButtonListener(new DeleteButtonButtonListener());
        soldBooksView.setTableBookList(userBooksService.findAll(user));
    }
    private class BackButtonButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            soldBooksView.showStage(false);
        }
    }
    private class DeleteButtonButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            BookInterface book = soldBooksView.getSelectedBook();
            userBooksService.deleteBook(user, book);
            soldBooksView.setTableBookList(userBooksService.findAll(user));
        }
    }

}
