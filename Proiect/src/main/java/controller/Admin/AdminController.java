package controller.Admin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.ComponentFactory;
import view.Admin.AdminView;

public class AdminController {
    private final AdminView adminView;
    public AdminController(AdminView adminView) {
        this.adminView = adminView;
        adminView.showPane(ComponentFactory.getAdminUsersView().getPane());
        adminView.addUsersMenuListener(new UsersMenuListener());
        adminView.addLogoutMenuListener(new LogoutMenuListener());
        adminView.addBooksMenuListener(new BooksMenuListener());
    }
    private static class UsersMenuListener implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            ComponentFactory.getAdminView().showPane(ComponentFactory.getAdminUsersView().getPane());
            ComponentFactory.getMainView().setStageTitle("[ADMINISTRATOR] - Logged as " + ComponentFactory.getLoginController().getLoginNotification().getResult().getUsername());
            ComponentFactory.getAdminUsersView().setUsernameText(ComponentFactory.getLoginController().getLoginNotification().getResult().getUsername());
            ComponentFactory.getAdminUsersView().setMoneyText("Money: " + ComponentFactory.getLoginController().getLoginNotification().getResult().getMoney());
            ComponentFactory.getAdminUsersView().setTableUserList(ComponentFactory.getUserService().findAll());
        }
    }
    private static class BooksMenuListener implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            ComponentFactory.getAdminView().showPane(ComponentFactory.getAdminBooksView().getPane());
            ComponentFactory.getAdminBooksView().setUsernameText(ComponentFactory.getLoginController().getLoginNotification().getResult().getUsername());
            ComponentFactory.getAdminBooksView().setMoneyText("Money: " + ComponentFactory.getLoginController().getLoginNotification().getResult().getMoney());
            ComponentFactory.getAdminBooksView().setTableBookList(ComponentFactory.getBookService().findAll());
        }

    }
    private static class LogoutMenuListener implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            ComponentFactory.getMainView().showScene(ComponentFactory.getLoginView().getScene());
            ComponentFactory.getLoginView().clearTexts();
            ComponentFactory.getMainView().setStageTitle("Welcome to our Book Store");
        }
    }
}