package controller.Employee;

import launcher.ComponentFactory;
import view.Employee.EmployeeView;

public class EmployeeController {
    private final EmployeeView employeeView;
    public EmployeeController(EmployeeView employeeView) {
        this.employeeView = employeeView;
        employeeView.showPane(ComponentFactory.getAdminUsersView().getPane());
    }
    public EmployeeView getEmployeeView() {
        return employeeView;
    }
}