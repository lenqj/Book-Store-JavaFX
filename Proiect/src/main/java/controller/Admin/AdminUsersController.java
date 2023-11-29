package controller.Admin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.ComponentFactory;
import model.User;
import model.validator.Notification;
import view.Admin.AdminUsersView;

public class AdminUsersController {
    private final AdminUsersView adminUsersView;
    private final Notification<Boolean> adminUsersNotification;
    public AdminUsersController(AdminUsersView adminUsersView) {
        this.adminUsersView = adminUsersView;
        adminUsersNotification = new Notification<>();
        this.adminUsersView.addCreateButtonListener(new CreateButtonListener());
        this.adminUsersView.addUpdateButtonListener(new UpdateButtonListener());
        this.adminUsersView.addDeleteButtonListener(new DeleteButtonListener());
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
