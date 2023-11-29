package controller.Employee;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.ComponentFactory;
import model.book.BookInterface;
import model.validator.Notification;
import view.Employee.EmployeeBooksView;


public class EmployeeBooksController {
    private final EmployeeBooksView employeeBooksView;
    private Notification<Boolean> employeeBooksNotification;
    public EmployeeBooksController(EmployeeBooksView employeeBooksView) {
        this.employeeBooksView = employeeBooksView;
        employeeBooksNotification = new Notification<>();
        employeeBooksView.addSellBookButtonButtonListener(new SellBookButtonListener());
        employeeBooksView.addBuyBookButtonButtonListener(new BuyBookButtonListener());
    }
    private class SellBookButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            BookInterface book = employeeBooksView.getSelectedBook();
            employeeBooksNotification = ComponentFactory.getUserBooksService().sell(ComponentFactory.getLoginController().getLoginNotification().getResult(), book);
            if (!employeeBooksNotification.hasErrors()) {
                employeeBooksView.setTextSellBook("You successfully sold: " + book.toString());
            }else {
                employeeBooksView.setTextSellBook(employeeBooksNotification.getFormattedErrors());
            }
            ComponentFactory.getEmployeeBooksView().setUsernameText(ComponentFactory.getLoginController().getLoginNotification().getResult().getUsername());
            ComponentFactory.getEmployeeBooksView().setMoneyText("Money: " + ComponentFactory.getLoginController().getLoginNotification().getResult().getMoney());
            ComponentFactory.getEmployeeBooksView().setTableBookList(ComponentFactory.getBookService().findAllSellableBooks());
        }
    }

    private class BuyBookButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            BookInterface book = employeeBooksView.getSelectedBook();
            employeeBooksNotification = ComponentFactory.getUserBooksService().buy(ComponentFactory.getLoginController().getLoginNotification().getResult(), book);
            if (!employeeBooksNotification.hasErrors()) {
                employeeBooksView.setTextSellBook("You successfully bought: " + book.toString());
                employeeBooksView.setTableBookList(ComponentFactory.getBookService().findAll());
                employeeBooksView.setMoneyText("Money: " + ComponentFactory.getLoginController().getLoginNotification().getResult().getMoney());
            }else {
                employeeBooksView.setTextSellBook(employeeBooksNotification.getFormattedErrors());
            }
        }
    }


}
