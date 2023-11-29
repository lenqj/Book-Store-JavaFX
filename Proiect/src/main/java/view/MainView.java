package view;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainView {
    private final Stage stage;
    public MainView(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("Book Store ");
        stage.setMaximized(true);
        stage.show();
    }
    public void showScene(Scene scene) {
        stage.setScene(scene);
    }
}
