package view;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import static javafx.scene.control.Alert.AlertType.ERROR;

public class MainView {
    private final Stage stage;
    private final Alert alert;

    public MainView(Stage primaryStage) {
        stage = primaryStage;
        alert = new Alert(ERROR);
        alert.setTitle("ERROR!");
        alert.setHeaderText("An error has been encountered");
        stage.setMaximized(true);
        stage.show();
    }
    public void showScene(Scene scene) {
        stage.setScene(scene);
    }
    public void setStageTitle(String text){
        stage.setTitle(text);
    }
    public Alert getAlert(){
        return alert;
    }
}
