package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.ComponentFactory;
import model.book.BookInterface;


import service.user.UserBooksService;
import view.SoldBooksView;

import static launcher.ComponentFactory.loginController;

public class SoldBooksController {

    private final SoldBooksView soldBooksView;

    public SoldBooksController(SoldBooksView soldBooksView) {
        this.soldBooksView = soldBooksView;
        soldBooksView.addBackButtonListener(new BackButtonButtonListener());
        soldBooksView.addDeleteButtonListener(new DeleteButtonButtonListener());
        //soldBooksView.setTableBookList(userBooksService.findAll(loginController.getLoginNotification().getResult()));
    }
    private class BackButtonButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            soldBooksView.showStage(false);
            ComponentFactory.getCustomerView().showStage(true);
        }
    }
    private class DeleteButtonButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            BookInterface book = soldBooksView.getSelectedBook();
            ComponentFactory.getUserBooksService().deleteBook(loginController.getLoginNotification().getResult(), book);
            soldBooksView.setTableBookList(ComponentFactory.getUserBooksService().findAll(loginController.getLoginNotification().getResult()));
        }
    }

}
