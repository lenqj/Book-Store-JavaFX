package controller.Admin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.ComponentFactory;
import model.User;
import model.validator.Notification;
import view.Admin.AdminUpdateUserView;

import java.util.List;

public class AdminUpdateUserController {
    private final AdminUpdateUserView adminUpdateUserView;
    private static Notification<Boolean> adminUpdateUserNotification;
    public AdminUpdateUserController(AdminUpdateUserView adminUpdateUserView) {
        this.adminUpdateUserView = adminUpdateUserView;
        adminUpdateUserNotification = new Notification<>();
        this.adminUpdateUserView.addUpdateButtonListener(new UpdateButtonListener());
    }
    private static class UpdateButtonListener implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            adminUpdateUserNotification = new Notification<>();
            Notification<User> updateUserNotification = ComponentFactory.getUserService().updateUser(
                    ComponentFactory.getAdminUsersView().getSelectedUser(),
                    ComponentFactory.getAdminUpdateUserView().getUsernameTextField(),
                    ComponentFactory.getAdminUpdateUserView().getPasswordTextField(),
                    ComponentFactory.getAdminUpdateUserView().getMoneyComboBox(),
                    List.of(ComponentFactory.getAdminUpdateUserView().getRoleComboBox()));
            if(updateUserNotification.hasErrors()){
                updateUserNotification.getErrors().forEach(adminUpdateUserNotification::addError);
            }else{
                ComponentFactory.getAdminView().showPane(ComponentFactory.getAdminUsersView().getPane());
                ComponentFactory.getMainView().setStageTitle("[ADMINISTRATOR] - Logged as " + ComponentFactory.getLoginController().getLoginNotification().getResult().getUsername());
                ComponentFactory.getAdminUsersView().setUsernameText(ComponentFactory.getLoginController().getLoginNotification().getResult().getUsername());
                ComponentFactory.getAdminUsersView().setMoneyText("Money: " + ComponentFactory.getLoginController().getLoginNotification().getResult().getMoney());
                ComponentFactory.getAdminUsersView().setTableUserList(ComponentFactory.getUserService().findAll());
                ComponentFactory.getAdminUsersView().clearTexts();
                ComponentFactory.getAdminUpdateUserView().clearTexts();
            }
            if(adminUpdateUserNotification.hasErrors()) {
                ComponentFactory.getMainView().getAlert().setContentText(adminUpdateUserNotification.getFormattedErrors());
                ComponentFactory.getMainView().getAlert().showAndWait();
            }
        }
    }
}
