package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.ComponentFactory;
import model.book.BookInterface;
import model.validator.Notification;
import view.CustomerView;
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
            ComponentFactory.getSoldBooksView().setTableBookList(ComponentFactory.getUserBooksService().findAll(loginController.getLoginNotification().getResult()));
        }
    }

    private class SellBookButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            BookInterface book = customerView.getSelectedBook();
            customerNotification = ComponentFactory.getUserBooksService().sell(loginController.getLoginNotification().getResult(), book);
            if (!customerNotification.hasErrors()) {
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
            if (!customerNotification.hasErrors()) {
                customerView.setTextSellBook("You successfully bought: " + book.toString());
                customerView.setTableBookList(ComponentFactory.getBookService().findAll());
                customerView.setMoneyText("Money: " + ComponentFactory.getUserRepository().findById(loginController.getLoginNotification().getResult().getId()).getMoney());
            }else {
                customerView.setTextSellBook(customerNotification.getFormattedErrors());
            }
        }
    }


}
