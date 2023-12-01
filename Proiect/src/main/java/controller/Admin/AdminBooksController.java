package controller.Admin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.ComponentFactory;
import model.book.BookInterface;
import model.validator.Notification;
import view.Admin.AdminBooksView;

public class AdminBooksController {
    private final AdminBooksView adminBooksView;
    private Notification<Boolean> adminBooksNotification;
    public AdminBooksController(AdminBooksView adminBooksView) {
        this.adminBooksView = adminBooksView;
        adminBooksNotification = new Notification<>();
        this.adminBooksView.addCreateButtonListener(new CreateButtonListener());
        this.adminBooksView.addUpdateButtonListener(new UpdateButtonListener());
        this.adminBooksView.addDeleteButtonListener(new DeleteButtonListener());
    }
    private class CreateButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

            adminBooksNotification = new Notification<>();
            ComponentFactory.getAdminView().showPane(ComponentFactory.getEmployeeCreateBookView().getPane());

        }
    }
    private class UpdateButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            adminBooksNotification = new Notification<>();
            BookInterface book = ComponentFactory.getAdminBooksView().getSelectedBook();
            if(book != null) {
                ComponentFactory.getAdminView().showPane(ComponentFactory.getEmployeeUpdateBookView().getPane());
                ComponentFactory.getEmployeeUpdateBookView().setUserLabels(
                        book.getAuthor(),
                        book.getTitle(),
                        book.getPublishedDate(),
                        book.getStock(),
                        book.getPrice());
            }else{
                adminBooksNotification.addError("You must select 1 book.");
            }
            if(adminBooksNotification.hasErrors()){
                ComponentFactory.getMainView().getAlert().setContentText(adminBooksNotification.getFormattedErrors());
                ComponentFactory.getMainView().getAlert().showAndWait();
            }
        }
    }
    private class DeleteButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            adminBooksNotification = new Notification<>();
            BookInterface book = ComponentFactory.getAdminBooksView().getSelectedBook();
            if(book == null){
                adminBooksNotification.addError("You must select 1 book.");
            }else{
                Notification<Boolean> deleteNotification = ComponentFactory.getBookService().delete(book);
                if(deleteNotification.hasErrors()){
                    deleteNotification.getErrors().forEach(adminBooksNotification::addError);
                }else{
                    //adminBooksView.setTextSellBook("You successfully deleted: " + book);
                    ComponentFactory.getAdminBooksView().setUsernameText(ComponentFactory.getLoginController().getLoginNotification().getResult().getUsername());
                    ComponentFactory.getAdminBooksView().setMoneyText("Money: " + ComponentFactory.getLoginController().getLoginNotification().getResult().getMoney());
                    ComponentFactory.getAdminBooksView().setTableBookList(ComponentFactory.getBookService().findAllSellableBooks(Boolean.TRUE));
                }
            }
            if(adminBooksNotification.hasErrors()){
                ComponentFactory.getMainView().getAlert().setContentText(adminBooksNotification.getFormattedErrors());
                ComponentFactory.getMainView().getAlert().showAndWait();
            }
        }
    }
}
