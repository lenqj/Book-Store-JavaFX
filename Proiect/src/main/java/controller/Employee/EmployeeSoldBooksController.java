package controller.Employee;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.ComponentFactory;
import view.Employee.EmployeeSoldBooksView;


public class EmployeeSoldBooksController {
    private final EmployeeSoldBooksView employeeSoldBooksView;
    public EmployeeSoldBooksController(EmployeeSoldBooksView employeeSoldBooksView) {
        this.employeeSoldBooksView = employeeSoldBooksView;
        employeeSoldBooksView.addDeleteButtonListener(new DeleteButtonButtonListener());
    }
    private class DeleteButtonButtonListener implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            Long selectedBookID = employeeSoldBooksView.getSelectedBook();
            ComponentFactory.getUserBooksService().deleteBook(ComponentFactory.getLoginController().getLoginNotification().getResult(), selectedBookID);
            employeeSoldBooksView.setTableBookList(ComponentFactory.getUserBooksService().findAll(ComponentFactory.getLoginController().getLoginNotification().getResult()));
        }
    }

}
