package controller.Employee;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.validator.Notification;
import view.Employee.EmployeeCreateBookView;


public class EmployeeCreateBookController {
    private final EmployeeCreateBookView employeeCreateBookView;
    private static Notification<Boolean> employeeCreateBookNotification;
    public EmployeeCreateBookController(EmployeeCreateBookView employeeCreateBookView) {
        this.employeeCreateBookView = employeeCreateBookView;
        employeeCreateBookNotification = new Notification<>();
        this.employeeCreateBookView.addCreateButtonListener(new CreateButtonListener());
    }
    private static class CreateButtonListener implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {

        }
    }
}
