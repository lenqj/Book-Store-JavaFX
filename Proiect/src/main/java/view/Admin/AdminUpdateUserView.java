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
import model.Role;
import java.util.List;
import static database.Constants.Roles.*;

public class AdminUpdateUserView {
    private final GridPane gridPane;
    private Button updateButton;
    private Label userUsernameLabel;
    private Label userRoleLabel;
    private Label userMoneyLabel;
    private TextField usernameTextField;
    private TextField passwordTextField;
    private ComboBox<Role> roleComboBox;
    private ComboBox<Long> moneyComboBox;
    public AdminUpdateUserView() {
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
        userUsernameLabel = new Label();
        userRoleLabel = new Label();
        userMoneyLabel = new Label();

        gridPane.add(userUsernameLabel, 0, 0, 1, 1);
        gridPane.add(userRoleLabel, 1, 0, 1, 1);
        gridPane.add(userMoneyLabel, 2, 0, 1, 1);


        usernameTextField = new TextField();
        passwordTextField = new TextField();
        roleComboBox = new ComboBox<>();
        moneyComboBox = new ComboBox<>();
        moneyComboBox.getItems().addAll(0L, 100L, 200L);
        moneyComboBox.getSelectionModel().selectFirst();

        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");
        Label roleLabel = new Label("Role:");
        Label moneyLabel = new Label("Money:");

        gridPane.add(usernameTextField, 1, 2, 2, 1);
        gridPane.add(passwordTextField, 1, 3, 2, 1);
        gridPane.add(roleComboBox, 1, 4, 2, 1);
        gridPane.add(moneyComboBox, 1, 5, 2, 1);


        gridPane.add(usernameLabel, 0, 2, 2, 1);
        gridPane.add(passwordLabel, 0, 3, 2, 1);
        gridPane.add(roleLabel, 0, 4, 2, 1);
        gridPane.add(moneyLabel, 0, 5, 2, 1);

    }
    private void initializeButtons(GridPane gridPane){
        updateButton = new Button("UPDATE");
        HBox updateButtonHBox = new HBox(10);
        updateButtonHBox.setAlignment(Pos.BOTTOM_CENTER);
        updateButtonHBox.getChildren().add(updateButton);
        gridPane.add(updateButtonHBox, 0, 6, 3, 1);
    }

    public void setUserLabels(String username, List<Role> role, Long money){
        userUsernameLabel.setText("Username: " + username);
        userRoleLabel.setText("Role: " + role);
        userMoneyLabel.setText("Money: " + money);
    }

    public String getUsernameTextField(){
        return usernameTextField.getText() == null || usernameTextField.getText().isEmpty() ? null : usernameTextField.getText();
    }
    public String getPasswordTextField(){
        return passwordTextField.getText() == null || passwordTextField.getText().isEmpty() ? null : passwordTextField.getText();

    }
    public Role getRoleComboBox(){
        return roleComboBox.getSelectionModel().getSelectedItem();
    }
    public Long getMoneyComboBox(){
        return moneyComboBox.getSelectionModel().getSelectedItem();
    }
    public void setRoleComboBox(List<Role> list){
        roleComboBox.getItems().clear();
        roleComboBox.getItems().addAll(list);
        roleComboBox.getSelectionModel().selectFirst();
    }
    public void addUpdateButtonListener(EventHandler<ActionEvent> updateButtonListener) {
        updateButton.setOnAction(updateButtonListener);
    }
    public Pane getPane() {
        return gridPane;
    }


}
