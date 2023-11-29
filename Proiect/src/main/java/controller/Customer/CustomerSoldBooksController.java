package controller.Customer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.ComponentFactory;
import model.book.BookInterface;
import view.Customer.CustomerSoldBooksView;

import java.util.Map;


public class CustomerSoldBooksController {
    private final CustomerSoldBooksView customerSoldBooksView;
    public CustomerSoldBooksController(CustomerSoldBooksView customerSoldBooksView) {
        this.customerSoldBooksView = customerSoldBooksView;
        customerSoldBooksView.addBackButtonListener(new BackButtonButtonListener());
        customerSoldBooksView.addDeleteButtonListener(new DeleteButtonButtonListener());
    }
    private class BackButtonButtonListener implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            ComponentFactory.getCustomerBooksView().clearTexts();
            ComponentFactory.getCustomerView().showPane(ComponentFactory.getCustomerBooksView().getPane());
        }
    }
    private class DeleteButtonButtonListener implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            Long selectedBookID = customerSoldBooksView.getSelectedBook();
            ComponentFactory.getUserBooksService().deleteBook(ComponentFactory.getLoginController().getLoginNotification().getResult(), selectedBookID);
            customerSoldBooksView.setTableBookList(ComponentFactory.getUserBooksService().findAll(ComponentFactory.getLoginController().getLoginNotification().getResult()));
        }
    }

}
