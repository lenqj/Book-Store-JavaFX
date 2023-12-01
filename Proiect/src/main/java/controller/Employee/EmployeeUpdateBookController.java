package controller.Employee;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.ComponentFactory;
import model.book.BookInterface;
import model.validator.Notification;
import view.Admin.AdminCreateUserView;
import view.Employee.EmployeeCreateBookView;
import view.Employee.EmployeeUpdateBookView;


public class EmployeeUpdateBookController {
    private final EmployeeUpdateBookView employeeUpdateBookView;
    private static Notification<Boolean> employeeUpdateBookNotification;
    public EmployeeUpdateBookController(EmployeeUpdateBookView employeeUpdateBookView) {
        this.employeeUpdateBookView = employeeUpdateBookView;
        employeeUpdateBookNotification = new Notification<>();
        this.employeeUpdateBookView.addUpdateButtonListener(new UpdateButtonListener());
    }
    private static class UpdateButtonListener implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            BookInterface book = ComponentFactory.getEmployeeBooksView().getSelectedBook();
            Notification<BookInterface> updateBookNotification = ComponentFactory.getBookService().update(book,
                    ComponentFactory.getEmployeeUpdateBookView().getAuthorTextField(),
                    ComponentFactory.getEmployeeUpdateBookView().getTitleTextField(),
                    ComponentFactory.getEmployeeUpdateBookView().getPublishedDateDatePicker(),
                    ComponentFactory.getEmployeeUpdateBookView().getStockTextField(),
                    ComponentFactory.getEmployeeUpdateBookView().getPriceTextField());
            if(updateBookNotification.hasErrors()){
                updateBookNotification.getErrors().forEach(employeeUpdateBookNotification::addError);
            }else{
                ComponentFactory.getEmployeeView().showPane(ComponentFactory.getEmployeeBooksView().getPane());
                ComponentFactory.getEmployeeBooksView().setUsernameText(ComponentFactory.getLoginController().getLoginNotification().getResult().getUsername());
                ComponentFactory.getEmployeeBooksView().setMoneyText("Money: " + ComponentFactory.getLoginController().getLoginNotification().getResult().getMoney());
                ComponentFactory.getEmployeeBooksView().setTableBookList(ComponentFactory.getBookService().findAllSellableBooks(Boolean.TRUE));
                ComponentFactory.getEmployeeBooksView().clearTexts();
            }
            if(employeeUpdateBookNotification.hasErrors()) {
                ComponentFactory.getMainView().getAlert().setContentText(employeeUpdateBookNotification.getFormattedErrors());
                ComponentFactory.getMainView().getAlert().showAndWait();
            }
        }
    }
}
