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
        adminView.addBooksMenuListener(new BooksMenuListener());
    }
    private static class UsersMenuListener implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            ComponentFactory.getAdminView().showPane(ComponentFactory.getAdminUsersView().getPane());
        }
    }
    private static class BooksMenuListener implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            ComponentFactory.getAdminView().showPane(ComponentFactory.getAdminBooksView().getPane());
        }
    }
}