package controller.Customer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.ComponentFactory;
import model.book.BookInterface;
import model.validator.Notification;
import view.Customer.CustomerBooksView;


public class CustomerBooksController {
    private final CustomerBooksView customerBooksView;
    private Notification<Boolean> customerNotification;
    public CustomerBooksController(CustomerBooksView customerBooksView) {
        this.customerBooksView = customerBooksView;
        customerNotification = new Notification<>();
        customerBooksView.addBuyBookButtonButtonListener(new BuyBookButtonListener());
    }
    private class BuyBookButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            customerNotification = new Notification<>();
            BookInterface book = customerBooksView.getSelectedBook();
            customerNotification = ComponentFactory.getUserBooksService().buy(ComponentFactory.getLoginController().getLoginNotification().getResult(), book);
            if (!customerNotification.hasErrors()) {
                customerBooksView.setTextSellBook("You successfully bought: " + book.toString());
                customerBooksView.setTableBookList(ComponentFactory.getBookService().findAllSellableBooks(Boolean.FALSE));
                customerBooksView.setMoneyText("Money: " + ComponentFactory.getLoginController().getLoginNotification().getResult().getMoney());
            }else {
                ComponentFactory.getMainView().getAlert().setContentText(customerNotification.getFormattedErrors());
                ComponentFactory.getMainView().getAlert().showAndWait();
            }
        }
    }


}
