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
        customerBooksView.addSellBookButtonButtonListener(new SellBookButtonListener());
        customerBooksView.addSoldBooksButtonButtonListener(new SoldBookButtonListener());
        customerBooksView.addLogoutButtonListener(new LogoutButtonListener());
        customerBooksView.addBuyBookButtonButtonListener(new BuyBookButtonListener());
    }
    private static class LogoutButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            ComponentFactory.getMainView().showScene(ComponentFactory.getLoginView().getScene());
            ComponentFactory.getLoginView().clearTexts();
        }
    }

    private static class SoldBookButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            ComponentFactory.getCustomerView().showPane(ComponentFactory.getEmployeeSoldBooksView().getPane());
            ComponentFactory.getEmployeeSoldBooksView().setTableBookList(ComponentFactory.getUserBooksService().findAll(ComponentFactory.getLoginController().getLoginNotification().getResult()));
            ComponentFactory.getEmployeeSoldBooksView().setSceneTitle("Sold Books for: " + ComponentFactory.getLoginController().getLoginNotification().getResult().getUsername());

        }
    }

    private class SellBookButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            BookInterface book = customerBooksView.getSelectedBook();
            customerNotification = ComponentFactory.getUserBooksService().sell(ComponentFactory.getLoginController().getLoginNotification().getResult(), book);
            if (!customerNotification.hasErrors()) {
                customerBooksView.setTextSellBook("You successfully sold: " + book.toString());
            }else {
                customerBooksView.setTextSellBook(customerNotification.getFormattedErrors());
            }
        }
    }

    private class BuyBookButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            BookInterface book = customerBooksView.getSelectedBook();
            customerNotification = ComponentFactory.getUserBooksService().buy(ComponentFactory.getLoginController().getLoginNotification().getResult(), book);
            if (!customerNotification.hasErrors()) {
                customerBooksView.setTextSellBook("You successfully bought: " + book.toString());
                customerBooksView.setTableBookList(ComponentFactory.getBookService().findAll());
                customerBooksView.setMoneyText("Money: " + ComponentFactory.getLoginController().getLoginNotification().getResult().getMoney());
            }else {
                customerBooksView.setTextSellBook(customerNotification.getFormattedErrors());
            }
        }
    }


}
