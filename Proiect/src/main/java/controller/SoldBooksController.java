package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.ComponentFactory;
import model.book.BookInterface;


import view.Customer.CustomerSoldBooksView;

import static launcher.ComponentFactory.loginController;

public class SoldBooksController {

    private final CustomerSoldBooksView customerSoldBooksView;

    public SoldBooksController(CustomerSoldBooksView customerSoldBooksView) {
        this.customerSoldBooksView = customerSoldBooksView;
        customerSoldBooksView.addBackButtonListener(new BackButtonButtonListener());
        customerSoldBooksView.addDeleteButtonListener(new DeleteButtonButtonListener());
    }
    private class BackButtonButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            ComponentFactory.getCustomerBooksView().clearTexts();
            ComponentFactory.getCustomerView().showPane(ComponentFactory.getCustomerBooksView().getPane());
        }
    }
    private class DeleteButtonButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            BookInterface book = customerSoldBooksView.getSelectedBook();
            ComponentFactory.getUserBooksService().deleteBook(loginController.getLoginNotification().getResult(), book);
            customerSoldBooksView.setTableBookList(ComponentFactory.getUserBooksService().findAll(loginController.getLoginNotification().getResult()));
        }
    }

}
