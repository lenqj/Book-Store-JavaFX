package controller.Admin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.ComponentFactory;
import model.User;
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
    private static class CreateButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

        }
    }
    private static class UpdateButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

        }
    }
    private static class DeleteButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            User user = ComponentFactory.getAdminUsersView().getSelectedUser();
            ComponentFactory.getUserService().deleteUser(user);
        }
    }
}
