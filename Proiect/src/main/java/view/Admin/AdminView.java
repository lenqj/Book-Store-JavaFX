package view.Admin;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import view.View;

public class AdminView implements View {
    private final Scene scene;
    private final BorderPane rootPane;
    public AdminView() {
        rootPane = new BorderPane();
        scene = new Scene(rootPane);
    }
    public void showPane(Pane pane) {
        rootPane.setCenter(pane);
    }
    public Scene getScene(){
        return scene;
    }
}
