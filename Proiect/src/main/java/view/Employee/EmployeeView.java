package view.Employee;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import view.View;

public class EmployeeView implements View {
    private final Scene scene;
    private final BorderPane rootPane;
    private final MenuItem booksMenuItem;
    private final MenuItem soldBooksMenuItem;
    private final MenuItem logoutMenuItem;
    private final MenuItem reportMenuItem;
    public EmployeeView() {
        rootPane = new BorderPane();
        scene = new Scene(rootPane);
        booksMenuItem = new MenuItem("Books");
        soldBooksMenuItem = new MenuItem("Sold Books");
        logoutMenuItem = new MenuItem("Logout");
        reportMenuItem = new MenuItem("Report Activity");
        initializeMenuBar();
    }
    public void initializeMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu usersMenu = new Menu("Users");
        Menu booksMenu = new Menu("Books");
        usersMenu.getItems().addAll(logoutMenuItem);
        booksMenu.getItems().addAll(booksMenuItem, soldBooksMenuItem, reportMenuItem);
        menuBar.getMenus().addAll(usersMenu, booksMenu);
        rootPane.setTop(menuBar);
    }
    public void showPane(Pane pane) {
        rootPane.setCenter(pane);
    }
    public Scene getScene(){
        return scene;
    }
    public void addBooksMenuListener(EventHandler<ActionEvent> booksMenuListener) {
        booksMenuItem.setOnAction(booksMenuListener);
    }
    public void addSoldBooksMenuListener(EventHandler<ActionEvent> soldBooksMenuListener) {
        soldBooksMenuItem.setOnAction(soldBooksMenuListener);
    }
    public void addLogoutMenuListener(EventHandler<ActionEvent> logoutMenuListener) {
        logoutMenuItem.setOnAction(logoutMenuListener);
    }
    public void addReportMenuListener(EventHandler<ActionEvent> reportMenuListeners) {
        reportMenuItem.setOnAction(reportMenuListeners);
    }
}
