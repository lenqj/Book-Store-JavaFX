package controller.Admin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.ComponentFactory;
import model.validator.Notification;
import view.Admin.AdminCreateUserView;


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
            adminCreateUserNotification = new Notification<>();
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
                ComponentFactory.getAdminUsersView().clearTexts();
                ComponentFactory.getAdminCreateUserView().clearTexts();
            }
            if(adminCreateUserNotification.hasErrors()) {
                ComponentFactory.getMainView().getAlert().setContentText(adminCreateUserNotification.getFormattedErrors());
                ComponentFactory.getMainView().getAlert().showAndWait();
            }
        }
    }
}
