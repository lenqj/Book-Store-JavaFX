package view.Admin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.User;

import java.util.List;

public class AdminUsersView {
    private final GridPane gridPane;
    private final TableView<User> table;
    private Button createButton;
    private Button updateButton;
    private Button deleteButton;
    private Text usernameText;
    private Text moneyText;

    public AdminUsersView() {
        gridPane = new GridPane();
        initializeGridPane(gridPane);
        initializeSceneTitle(gridPane);
        table = new TableView<>();
        initializeTableView(table, gridPane);

        initializeButtons(gridPane);
    }
    private void initializeGridPane(GridPane gridPane){
        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(100d / 5);
        for (int i = 0; i < 7; i++) {
            gridPane.getRowConstraints().add(rc);
        }

        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(100d / 6);

        for (int i = 0; i < 6; i++) {
            gridPane.getColumnConstraints().add(cc);
        }
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    private void initializeSceneTitle(GridPane gridPane){
        usernameText = new Text();
        usernameText.setFont(Font.font("Thoma", FontWeight.NORMAL, 20));
        HBox usernameTextHBox = new HBox();
        usernameTextHBox.setAlignment(Pos.BOTTOM_LEFT);
        usernameTextHBox.getChildren().add(usernameText);


        moneyText = new Text();
        moneyText.setFont(Font.font("Thoma", FontWeight.NORMAL, 20));
        HBox moneyTextHBox = new HBox();
        moneyTextHBox.setAlignment(Pos.BOTTOM_RIGHT);
        moneyTextHBox.getChildren().add(moneyText);

        gridPane.add(usernameTextHBox, 0, 0, 1, 1);
        gridPane.add(moneyTextHBox, 5, 0, 1, 1);
    }
    @SuppressWarnings("unchecked")
    private void initializeTableView(TableView<User> tableView, GridPane gridPane){
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        ScrollPane sp = new ScrollPane(tableView);
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        gridPane.add(sp, 0, 1, 6, 5);
        TableColumn<User, String> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<User,String> username = new TableColumn<>("Username");
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn<User,String> roles = new TableColumn<>("Roles");
        roles.setCellValueFactory(new PropertyValueFactory<>("roles"));
        TableColumn<User,String> money = new TableColumn<>("Money");
        money.setCellValueFactory(new PropertyValueFactory<>("money"));
        tableView.getColumns().setAll(id, username, roles, money);
    }
    public void setTableUserList(List<User> users){
        table.getItems().clear();
        for (User user: users)
            table.getItems().add(user);
    }

    private void initializeButtons(GridPane gridPane){
        deleteButton = new Button("DELETE");
        HBox deleteButtonHBox = new HBox(10);
        deleteButtonHBox.setAlignment(Pos.BOTTOM_LEFT);
        deleteButtonHBox.getChildren().add(deleteButton);
        gridPane.add(deleteButtonHBox, 0, 7);


        createButton = new Button("CREATE");
        HBox createButtonHBox = new HBox(10);
        createButtonHBox.setAlignment(Pos.BOTTOM_CENTER);
        createButtonHBox.getChildren().add(createButton);
        gridPane.add(createButtonHBox, 2, 7, 2, 1);

        updateButton = new Button("UPDATE");
        HBox updateButtonHBox = new HBox(10);
        updateButtonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        updateButtonHBox.getChildren().add(updateButton);
        gridPane.add(updateButtonHBox, 5, 7);
    }
    public void setMoneyText(String text){
        moneyText.setText(text);
    }
    public void setUsernameText(String text) {
        usernameText.setText(text);
    }
    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonListener) {
        deleteButton.setOnAction(deleteButtonListener);
    }
    public void addCreateButtonListener(EventHandler<ActionEvent> createButtonListener) {
        createButton.setOnAction(createButtonListener);
    }
    public void addUpdateButtonListener(EventHandler<ActionEvent> updateButtonListener) {
        updateButton.setOnAction(updateButtonListener);
    }
    public User getSelectedUser(){
        return table.getSelectionModel().getSelectedItem();
    }
    public Pane getPane() {
        return gridPane;
    }

}
