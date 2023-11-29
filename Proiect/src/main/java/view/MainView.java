package view;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainView {
    private final Stage stage;
    public MainView(Stage primaryStage) {
        stage = primaryStage;
        stage.setMaximized(true);
        stage.show();
    }
    public void showScene(Scene scene) {
        stage.setScene(scene);
    }
    public void setStageTitle(String text){
        stage.setTitle(text);
    }
}
