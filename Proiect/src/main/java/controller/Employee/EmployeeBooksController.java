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
        employeeBooksView.addCreateBookButtonButtonListener(new CreateBookButtonListener());
    }
    private class SellBookButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            employeeBooksNotification = new Notification<>();
            BookInterface book = employeeBooksView.getSelectedBook();
            employeeBooksNotification = ComponentFactory.getUserBooksService().sell(ComponentFactory.getLoginController().getLoginNotification().getResult(), book);
            if (!employeeBooksNotification.hasErrors()) {
                employeeBooksView.setTextSellBook("You successfully sold: " + book.toString());
                ComponentFactory.getEmployeeBooksView().setUsernameText(ComponentFactory.getLoginController().getLoginNotification().getResult().getUsername());
                ComponentFactory.getEmployeeBooksView().setMoneyText("Money: " + ComponentFactory.getLoginController().getLoginNotification().getResult().getMoney());
                ComponentFactory.getEmployeeBooksView().setTableBookList(ComponentFactory.getBookService().findAllSellableBooks(Boolean.TRUE));
            }else {
                ComponentFactory.getMainView().getAlert().setContentText(employeeBooksNotification.getFormattedErrors());
                ComponentFactory.getMainView().getAlert().showAndWait();
            }
        }
    }

    private class CreateBookButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            ComponentFactory.getEmployeeView().showPane(ComponentFactory.getEmployeeCreateBookView().getPane());
        }
    }



}
