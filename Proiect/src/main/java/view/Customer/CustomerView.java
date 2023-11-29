package view.Customer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import view.View;

public class CustomerView implements View {
    private final Scene scene;
    private final BorderPane rootPane;
    private final MenuItem booksMenuItem;
    private final MenuItem logoutMenuItem;

    public CustomerView() {
        rootPane = new BorderPane();
        scene = new Scene(rootPane);
        booksMenuItem = new MenuItem("Books");
        logoutMenuItem = new MenuItem("Logout");
        initializeMenuBar();
    }

    public void initializeMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu usersMenu = new Menu("Users");
        Menu booksMenu = new Menu("Books");
        usersMenu.getItems().addAll(logoutMenuItem);
        booksMenu.getItems().add(booksMenuItem);
        menuBar.getMenus().addAll(usersMenu, booksMenu);
        rootPane.setTop(menuBar);
    }

    public void showPane(Pane pane) {
        rootPane.setCenter(pane);
    }

    public Scene getScene() {
        return scene;
    }

    public void addBooksMenuListener(EventHandler<ActionEvent> booksMenuListener) {
        booksMenuItem.setOnAction(booksMenuListener);
    }

    public void addLogoutMenuListener(EventHandler<ActionEvent> logoutMenuListener) {
        logoutMenuItem.setOnAction(logoutMenuListener);
    }
}
