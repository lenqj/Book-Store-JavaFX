package controller.Employee;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.ComponentFactory;
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

        }
    }
}
