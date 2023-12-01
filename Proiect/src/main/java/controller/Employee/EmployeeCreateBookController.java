package controller.Employee;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.ComponentFactory;
import model.book.BookInterface;
import model.builder.BookBuilder;
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
            BookInterface book = new BookBuilder()
                    .setAuthor(ComponentFactory.getEmployeeCreateBookView().getAuthorTextField())
                    .setTitle(ComponentFactory.getEmployeeCreateBookView().getTitleTextField())
                    .setPublishedDate(ComponentFactory.getEmployeeCreateBookView().getPublishedDateDatePicker())
                    .setStock(ComponentFactory.getEmployeeCreateBookView().getStockTextField())
                    .setPrice(ComponentFactory.getEmployeeCreateBookView().getPriceTextField())
                    .setToSell(Boolean.TRUE)
                    .build();
            Notification<Boolean> saveNotification = ComponentFactory.getBookService().save(book);
            if(saveNotification.hasErrors()){
                saveNotification.getErrors().forEach(employeeCreateBookNotification::addError);
            }else{
                ComponentFactory.getEmployeeView().showPane(ComponentFactory.getEmployeeBooksView().getPane());
                ComponentFactory.getEmployeeBooksView().setUsernameText(ComponentFactory.getLoginController().getLoginNotification().getResult().getUsername());
                ComponentFactory.getEmployeeBooksView().setMoneyText("Money: " + ComponentFactory.getLoginController().getLoginNotification().getResult().getMoney());
                ComponentFactory.getEmployeeBooksView().setTableBookList(ComponentFactory.getBookService().findAllSellableBooks(Boolean.TRUE));
                ComponentFactory.getEmployeeBooksView().clearTexts();
            }
            if(employeeCreateBookNotification.hasErrors()){
                ComponentFactory.getMainView().getAlert().setContentText(employeeCreateBookNotification.getFormattedErrors());
                ComponentFactory.getMainView().getAlert().showAndWait();
            }
        }
    }
}
