package controller.Admin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import launcher.ComponentFactory;
import model.User;
import model.validator.Notification;
import view.Admin.AdminCreateUserView;
import view.Admin.AdminUsersView;

public class AdminCreateUserController {
    private final AdminCreateUserView adminCreateUserView;
    private static Notification<Boolean> adminCreateUserNotification;
    public AdminCreateUserController(AdminCreateUserView adminCreateUserView) {
        this.adminCreateUserView = adminCreateUserView;
        adminCreateUserNotification = new Notification<>();
        this.adminCreateUserView.addCreateButtonListener(new CreateButtonListener());
    }
    private static class CreateButtonListener implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            Notification<Boolean> createUserNotification = ComponentFactory.getUserService().createUser(
                    ComponentFactory.getAdminCreateUserView().getUsernameTextField(),
                    ComponentFactory.getAdminCreateUserView().getPasswordTextField(),
                    ComponentFactory.getAdminCreateUserView().getRoleComboBox(),
                    ComponentFactory.getAdminCreateUserView().getMoneyComboBox());
            if(createUserNotification.hasErrors()){
                createUserNotification.getErrors().forEach(adminCreateUserNotification::addError);
            }else{
                ComponentFactory.getAdminView().showPane(ComponentFactory.getAdminUsersView().getPane());
                ComponentFactory.getMainView().setStageTitle("[ADMINISTRATOR] - Logged as " + ComponentFactory.getLoginController().getLoginNotification().getResult().getUsername());
                ComponentFactory.getAdminUsersView().setUsernameText(ComponentFactory.getLoginController().getLoginNotification().getResult().getUsername());
                ComponentFactory.getAdminUsersView().setMoneyText("Money: " + ComponentFactory.getLoginController().getLoginNotification().getResult().getMoney());
                ComponentFactory.getAdminUsersView().setTableUserList(ComponentFactory.getUserService().findAll());
            }
        }
    }
}
