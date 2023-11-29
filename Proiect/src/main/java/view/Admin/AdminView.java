package view.Admin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import view.View;


public class AdminView implements View {
    private final Scene scene;
    private final BorderPane rootPane;
    private final MenuItem usersMenuItem;
    private final MenuItem logoutMenuItem;
    private final MenuItem booksMenuItem;
    public AdminView() {
        rootPane = new BorderPane();
        scene = new Scene(rootPane);
        usersMenuItem = new MenuItem("Users");
        logoutMenuItem = new MenuItem("Logout");
        booksMenuItem = new MenuItem("Books");
        initializeMenuBar();
    }
    public void initializeMenuBar(){
        MenuBar menuBar = new MenuBar();
        Menu usersMenu = new Menu("Users");
        Menu booksMenu = new Menu("Books");
        usersMenu.getItems().addAll(usersMenuItem, logoutMenuItem);
        booksMenu.getItems().addAll(booksMenuItem);
        menuBar.getMenus().addAll(usersMenu, booksMenu);
        rootPane.setTop(menuBar);
    }
    public void showPane(Pane pane) {
        rootPane.setCenter(pane);
    }
    public Scene getScene(){
        return scene;
    }
    public void addUsersMenuListener(EventHandler<ActionEvent> usersMenuListener){
        usersMenuItem.setOnAction(usersMenuListener);
    }
    public void addLogoutMenuListener(EventHandler<ActionEvent> logoutMenuListener) {
        logoutMenuItem.setOnAction(logoutMenuListener);
    }
    public void addBooksMenuListener(EventHandler<ActionEvent> booksMenuListener){
        booksMenuItem.setOnAction(booksMenuListener);
    }
}
