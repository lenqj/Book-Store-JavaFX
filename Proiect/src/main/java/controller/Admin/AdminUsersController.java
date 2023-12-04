package controller.Admin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.ComponentFactory;
import model.Role;
import model.User;
import model.validator.Notification;
import view.Admin.AdminUsersView;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AdminUsersController {
    private final AdminUsersView adminUsersView;
    private static Notification<Boolean> adminUsersNotification;
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
            ComponentFactory.getAdminView().showPane(ComponentFactory.getAdminCreateUserView().getPane());
        }
    }
    private static class UpdateButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            adminUsersNotification = new Notification<>();
            Notification<Boolean> updateUserNotification = new Notification<>();
            if ((ComponentFactory.getAdminUsersView().getSelectedUser()) != null) {
                ComponentFactory.getAdminView().showPane(ComponentFactory.getAdminUpdateUserView().getPane());
                ComponentFactory.getAdminUpdateUserView().setUserLabels(ComponentFactory.getAdminUsersView().getSelectedUser().getUsername(), ComponentFactory.getAdminUsersView().getSelectedUser().getRoles(), ComponentFactory.getAdminUsersView().getSelectedUser().getMoney());
                List<Role> differences = ComponentFactory.getAuthenticationService().findAllRoles().stream()
                        .filter(element -> !ComponentFactory.getAdminUsersView().getSelectedUser().getRoles().contains(element))
                        .collect(Collectors.toList());
                ComponentFactory.getAdminUpdateUserView().setRoleComboBox(differences);
            } else {
                updateUserNotification.addError("You must select 1 user!");
            }
            updateUserNotification.getErrors().forEach(adminUsersNotification::addError);
            if(adminUsersNotification.hasErrors()) {
                ComponentFactory.getMainView().getAlert().setContentText(adminUsersNotification.getFormattedErrors());
                ComponentFactory.getMainView().getAlert().showAndWait();
            }
        }
    }
    private static class DeleteButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            adminUsersNotification = new Notification<>();
            User user = ComponentFactory.getAdminUsersView().getSelectedUser();
            Notification<Boolean> deleteUserNotification = ComponentFactory.getUserService().deleteUser(user);
                if (deleteUserNotification.hasErrors()) {
                    deleteUserNotification.getErrors().forEach(adminUsersNotification::addError);
                } else {
                    if (Objects.equals(user.getId(), ComponentFactory.getLoginController().getLoginNotification().getResult().getId())) {
                        ComponentFactory.getMainView().showScene(ComponentFactory.getLoginView().getScene());
                        ComponentFactory.getLoginView().clearTexts();
                        ComponentFactory.getMainView().setStageTitle("Welcome to our Book Store");
                        ComponentFactory.getAdminUsersView().clearTexts();
                    } else {
                        ComponentFactory.getMainView().setStageTitle("[ADMINISTRATOR] - Logged as " + ComponentFactory.getLoginController().getLoginNotification().getResult().getUsername());
                        ComponentFactory.getAdminUsersView().setUsernameText(ComponentFactory.getLoginController().getLoginNotification().getResult().getUsername());
                        ComponentFactory.getAdminUsersView().setMoneyText("Money: " + ComponentFactory.getLoginController().getLoginNotification().getResult().getMoney());
                        ComponentFactory.getAdminUsersView().setTableUserList(ComponentFactory.getUserService().findAll());
                        ComponentFactory.getAdminUsersView().clearTexts();
                    }
                }
            if(adminUsersNotification.hasErrors()) {
                ComponentFactory.getMainView().getAlert().setContentText(adminUsersNotification.getFormattedErrors());
                ComponentFactory.getMainView().getAlert().showAndWait();
            }
        }
    }
}
