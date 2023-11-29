package controller.Admin;

import launcher.ComponentFactory;
import view.Admin.AdminView;

public class AdminController {
    private final AdminView adminView;
    public AdminController(AdminView adminView) {
        this.adminView = adminView;
        adminView.showPane(ComponentFactory.getAdminUsersView().getPane());
    }

    public AdminView getAdminView() {
        return adminView;
    }
}