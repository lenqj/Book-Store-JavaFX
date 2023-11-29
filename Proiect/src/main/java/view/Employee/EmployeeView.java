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
    public EmployeeView() {
        rootPane = new BorderPane();
        scene = new Scene(rootPane);
        booksMenuItem = new MenuItem("Books");
        soldBooksMenuItem = new MenuItem("Sold Books");
        logoutMenuItem = new MenuItem("Logout");
        initializeMenuBar();
    }
    public void initializeMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu usersMenu = new Menu("Users");
        Menu booksMenu = new Menu("Books");
        usersMenu.getItems().addAll(logoutMenuItem);
        booksMenu.getItems().addAll(booksMenuItem, soldBooksMenuItem);
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
}
