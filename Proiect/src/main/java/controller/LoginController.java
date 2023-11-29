package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.ComponentFactory;
import model.User;
import model.validator.Notification;
import view.LoginView;


import static database.Constants.Roles.*;

public class LoginController {

    private final LoginView loginView;
    private static Notification<User> loginNotification;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        loginNotification = new Notification<>();
        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    }
    public Notification<User> getLoginNotification(){
        return loginNotification;
    }
    public static void showStage(String role){
        switch (role)  {
            case CUSTOMER -> {
                ComponentFactory.getMainView().showScene(ComponentFactory.getCustomerView().getScene());
                ComponentFactory.getCustomerBooksView().setUsernameText(loginNotification.getResult().getUsername());
                ComponentFactory.getCustomerBooksView().setMoneyText("Money: " + loginNotification.getResult().getMoney());
                ComponentFactory.getCustomerBooksView().setTableBookList(ComponentFactory.getBookService().findAll());
            }
            case EMPLOYEE -> {
                ComponentFactory.getMainView().showScene(ComponentFactory.getEmployeeView().getScene());
            }
            case ADMINISTRATOR -> {
                ComponentFactory.getMainView().showScene(ComponentFactory.getAdminView().getScene());
                ComponentFactory.getAdminUsersView().setTableBookList(ComponentFactory.getUserService().findAll());
            }
            default -> {
                ComponentFactory.getMainView().showScene(ComponentFactory.getLoginView().getScene());
            }
        }
    }

    private class LoginButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            loginNotification = ComponentFactory.getAuthenticationService().login(username, password);

            if (loginNotification.hasErrors()){
                loginView.setActionTargetText(loginNotification.getFormattedErrors());
            }else {
                loginView.setActionTargetText("LogIn Successfull!");
                showStage((ComponentFactory.getRightsRolesRepository().findRolesForUser(loginNotification.getResult().getId()).get(0)).getRole());
            }
        }
    }
    private class RegisterButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();
            Notification<Boolean> registerNotification = ComponentFactory.getAuthenticationService().register(username, password);

            if (registerNotification.hasErrors()) {
                loginView.setActionTargetText(registerNotification.getFormattedErrors());
            } else {
                loginView.setActionTargetText("Register successful!");
            }
        }
    }


}
