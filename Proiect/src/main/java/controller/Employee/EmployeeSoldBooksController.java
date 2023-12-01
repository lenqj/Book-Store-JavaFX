package controller.Employee;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.ComponentFactory;
import model.book.BookInterface;
import model.validator.Notification;
import view.Employee.EmployeeSoldBooksView;

import java.sql.Date;
import java.util.Map;


public class EmployeeSoldBooksController {
    private final EmployeeSoldBooksView employeeSoldBooksView;
    private Notification<Boolean> employeeSoldBooksNotification;
    public EmployeeSoldBooksController(EmployeeSoldBooksView employeeSoldBooksView) {
        employeeSoldBooksNotification = new Notification<>();
        this.employeeSoldBooksView = employeeSoldBooksView;
        employeeSoldBooksView.addDeleteButtonListener(new DeleteButtonButtonListener());
    }
    private class DeleteButtonButtonListener implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            employeeSoldBooksNotification = new Notification<>();
            Map.Entry<Long, Map.Entry<BookInterface, Date>> selectedBook = employeeSoldBooksView.getSelectedBook();
            Notification<Boolean> deleteBookNotification = ComponentFactory.getUserBooksService().deleteBook(ComponentFactory.getLoginController().getLoginNotification().getResult(), selectedBook.getKey());
            if(deleteBookNotification.hasErrors()){
                deleteBookNotification.getErrors().forEach(employeeSoldBooksNotification::addError);
            }else {
                Notification<Boolean> sellBookNotification = ComponentFactory.getBookService().sell(selectedBook.getValue().getKey());
                if(sellBookNotification.hasErrors()){
                    sellBookNotification.getErrors().forEach(employeeSoldBooksNotification::addError);
                }else {
                    employeeSoldBooksView.setTableBookList(ComponentFactory.getUserBooksService().findAllSoldBooks(ComponentFactory.getLoginController().getLoginNotification().getResult()));
                }
            }
            if(employeeSoldBooksNotification.hasErrors()) {
                ComponentFactory.getMainView().getAlert().setContentText(employeeSoldBooksNotification.getFormattedErrors());
                ComponentFactory.getMainView().getAlert().showAndWait();
            }
        }
    }

}
