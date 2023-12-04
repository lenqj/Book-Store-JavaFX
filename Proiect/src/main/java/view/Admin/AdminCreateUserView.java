package view.Admin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import static database.Constants.Roles.ROLES;

public class AdminCreateUserView {
    private final GridPane gridPane;
    private Button createButton;
    private TextField usernameTextField;
    private TextField passwordTextField;
    private ComboBox<String> roleComboBox;
    private ComboBox<Long> moneyComboBox;
    public AdminCreateUserView() {
        gridPane = new GridPane();
        initializeGridPane(gridPane);
        initializeLabelsAndTexts();
        initializeButtons(gridPane);
    }
    private void initializeGridPane(GridPane gridPane){
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    private void initializeLabelsAndTexts(){

        usernameTextField = new TextField();
        passwordTextField = new TextField();
        roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll(ROLES);
        roleComboBox.getSelectionModel().selectFirst();
        moneyComboBox = new ComboBox<>();
        moneyComboBox.getItems().addAll(0L, 100L, 200L);
        moneyComboBox.getSelectionModel().selectFirst();

        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");
        Label roleLabel = new Label("Role:");
        Label moneyLabel = new Label("Money:");

        gridPane.add(usernameTextField, 1, 0, 1, 1);
        gridPane.add(passwordTextField, 1, 1, 1, 1);
        gridPane.add(roleComboBox, 1, 2, 1, 1);
        gridPane.add(moneyComboBox, 1, 3, 1, 1);


        gridPane.add(usernameLabel, 0, 0, 1, 1);
        gridPane.add(passwordLabel, 0, 1, 1, 1);
        gridPane.add(roleLabel, 0, 2, 1, 1);
        gridPane.add(moneyLabel, 0, 3, 1, 1);

    }
    private void initializeButtons(GridPane gridPane){
        createButton = new Button("CREATE");
        HBox createButtonHBox = new HBox(10);
        createButtonHBox.setAlignment(Pos.BOTTOM_CENTER);
        createButtonHBox.getChildren().add(createButton);
        gridPane.add(createButtonHBox, 0, 4, 2, 1);
    }

    public String getUsernameTextField(){
        return usernameTextField.getText();
    }
    public String getPasswordTextField(){
        return passwordTextField.getText();
    }
    public String getRoleComboBox(){
        return roleComboBox.getSelectionModel().getSelectedItem();
    }
    public Long getMoneyComboBox(){
        return moneyComboBox.getSelectionModel().getSelectedItem();
    }
    public void addCreateButtonListener(EventHandler<ActionEvent> createButtonListener) {
        createButton.setOnAction(createButtonListener);
    }
    public void clearTexts(){
        usernameTextField.setText("");
        passwordTextField.setText("");
    }
    public Pane getPane() {
        return gridPane;
    }


}
