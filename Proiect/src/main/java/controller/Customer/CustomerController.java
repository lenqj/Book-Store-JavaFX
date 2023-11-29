package controller.Customer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.ComponentFactory;
import view.Customer.CustomerView;

public class CustomerController {
    private final CustomerView customerView;

    public CustomerController(CustomerView customerView) {
        this.customerView = customerView;
        customerView.showPane(ComponentFactory.getCustomerBooksView().getPane());
        customerView.addBooksMenuListener(new BooksMenuListener());
        customerView.addLogoutMenuListener(new LogoutMenuListener());
    }

    private static class BooksMenuListener implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            ComponentFactory.getCustomerView().showPane(ComponentFactory.getCustomerBooksView().getPane());
            ComponentFactory.getCustomerBooksView().setUsernameText(ComponentFactory.getLoginController().getLoginNotification().getResult().getUsername());
            ComponentFactory.getCustomerBooksView().setMoneyText("Money: " + ComponentFactory.getLoginController().getLoginNotification().getResult().getMoney());
            ComponentFactory.getCustomerBooksView().setTableBookList(ComponentFactory.getBookService().findAll());
        }
    }

    private static class LogoutMenuListener implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            ComponentFactory.getMainView().showScene(ComponentFactory.getLoginView().getScene());
            ComponentFactory.getLoginView().clearTexts();
            ComponentFactory.getMainView().setStageTitle("Welcome to our Book Store");
        }
    }
}
