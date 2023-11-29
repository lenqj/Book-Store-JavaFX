package controller.Employee;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.ComponentFactory;
import model.book.BookInterface;
import view.Employee.EmployeeSoldBooksView;

import java.util.Map;


public class EmployeeSoldBooksController {
    private final EmployeeSoldBooksView employeeSoldBooksView;
    public EmployeeSoldBooksController(EmployeeSoldBooksView employeeSoldBooksView) {
        this.employeeSoldBooksView = employeeSoldBooksView;
        employeeSoldBooksView.addDeleteButtonListener(new DeleteButtonButtonListener());
    }
    private class DeleteButtonButtonListener implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            Map.Entry<Long, BookInterface> selectedBook = employeeSoldBooksView.getSelectedBook();
            ComponentFactory.getUserBooksService().deleteBook(ComponentFactory.getLoginController().getLoginNotification().getResult(), selectedBook.getKey());
            ComponentFactory.getBookService().sell(selectedBook.getValue());
            employeeSoldBooksView.setTableBookList(ComponentFactory.getUserBooksService().findAllSoldBooks(ComponentFactory.getLoginController().getLoginNotification().getResult()));
        }
    }

}
