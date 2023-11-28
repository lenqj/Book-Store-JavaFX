package launcher;

import javafx.application.Application;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import model.User;

public class Launcher extends Application {
    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        ComponentFactory.getInstance(false, primaryStage);
    }
}
