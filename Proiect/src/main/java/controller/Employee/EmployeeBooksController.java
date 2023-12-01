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
        employeeBooksView.addSellBookButtonListener(new SellBookButtonListener());
        employeeBooksView.addCreateBookButtonListener(new CreateBookButtonListener());
        employeeBooksView.addUpdateBookButtonListener(new UpdateBookButtonListener());
        employeeBooksView.addDeleteBookButtonListener(new DeleteBookButtonListener());
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
            employeeBooksNotification = new Notification<>();
            ComponentFactory.getEmployeeView().showPane(ComponentFactory.getEmployeeCreateBookView().getPane());
        }
    }

    private class UpdateBookButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            employeeBooksNotification = new Notification<>();
            BookInterface book = ComponentFactory.getEmployeeBooksView().getSelectedBook();
            if(book != null) {
                ComponentFactory.getEmployeeView().showPane(ComponentFactory.getEmployeeUpdateBookView().getPane());
                ComponentFactory.getEmployeeUpdateBookView().setUserLabels(
                        book.getAuthor(),
                        book.getTitle(),
                        book.getPublishedDate(),
                        book.getStock(),
                        book.getPrice());
            }else{
                employeeBooksNotification.addError("You must select 1 book.");
            }
            if(employeeBooksNotification.hasErrors()){
                ComponentFactory.getMainView().getAlert().setContentText(employeeBooksNotification.getFormattedErrors());
                ComponentFactory.getMainView().getAlert().showAndWait();
            }

        }
    }

    private class DeleteBookButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            employeeBooksNotification = new Notification<>();
            BookInterface book = ComponentFactory.getEmployeeBooksView().getSelectedBook();
            if(book == null){
                employeeBooksNotification.addError("You must select 1 book.");
            }else{
                Notification<Boolean> deleteNotification = ComponentFactory.getBookService().delete(book);
                if(deleteNotification.hasErrors()){
                    deleteNotification.getErrors().forEach(employeeBooksNotification::addError);
                }else{
                    employeeBooksView.setTextSellBook("You successfully deleted: " + book);
                    ComponentFactory.getEmployeeBooksView().setUsernameText(ComponentFactory.getLoginController().getLoginNotification().getResult().getUsername());
                    ComponentFactory.getEmployeeBooksView().setMoneyText("Money: " + ComponentFactory.getLoginController().getLoginNotification().getResult().getMoney());
                    ComponentFactory.getEmployeeBooksView().setTableBookList(ComponentFactory.getBookService().findAllSellableBooks(Boolean.TRUE));
                }
            }
            if(employeeBooksNotification.hasErrors()){
                ComponentFactory.getMainView().getAlert().setContentText(employeeBooksNotification.getFormattedErrors());
                ComponentFactory.getMainView().getAlert().showAndWait();
            }

        }
    }



}
