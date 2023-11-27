package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;
import model.book.BookInterface;

import java.util.List;

public class CustomerView {
    private final Stage stage;
    private final TableView<BookInterface> table;
    private final Scene scene;
    private Button logoutButton;
    private Button soldBooksButton;
    private Button sellBookButton;
    private Button buyBookButton;
    private Text usernameText;
    private Text textSellBook;
    private Text moneyText;

    public CustomerView(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("Book Store ");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        scene = new Scene(gridPane, 720, 480);

        initializeSceneTitle(gridPane);

        table = new TableView<>();
        initializeTableView(table, gridPane);

        initializeButtons(gridPane);

        initializeTextSellBook(gridPane);
    }

    private void initializeGridPane(GridPane gridPane){
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    private void initializeSceneTitle(GridPane gridPane){
        usernameText = new Text();
        usernameText.setFont(Font.font("Thoma", FontWeight.NORMAL, 20));
        HBox usernameTextHBox = new HBox(10);
        usernameTextHBox.setAlignment(Pos.BOTTOM_LEFT);
        usernameTextHBox.getChildren().add(usernameText);


        moneyText = new Text();
        moneyText.setFont(Font.font("Thoma", FontWeight.NORMAL, 20));
        HBox moneyTextHBox = new HBox(10);
        moneyTextHBox.setAlignment(Pos.BOTTOM_RIGHT);
        moneyTextHBox.getChildren().add(moneyText);

        gridPane.add(usernameTextHBox, 0, 0);
        gridPane.add(moneyTextHBox, 2, 0);
    }
    @SuppressWarnings("unchecked")
    private void initializeTableView(TableView<BookInterface> tableView, GridPane gridPane){
        gridPane.add(table, 1, 1, 2, 1);
        TableColumn<BookInterface, String> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<BookInterface, String> author = new TableColumn<>("Author");
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        TableColumn<BookInterface, String> title = new TableColumn<>("Title");
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<BookInterface, String> publishedDate = new TableColumn<>("Published Date");
        publishedDate.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));
        TableColumn<BookInterface, String> stock = new TableColumn<>("Stock");
        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        TableColumn<BookInterface, String> price = new TableColumn<>("Price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableView.getColumns().setAll(id, author, title, publishedDate, stock, price);
    }
    public void setTableBookList(List<BookInterface> books){
        table.getItems().clear();
        for (BookInterface book: books)
            table.getItems().add(book);
    }

    private void initializeButtons(GridPane gridPane){
        sellBookButton = new Button("Sell Book");
        HBox sellBookButtonHBox = new HBox(10);
        sellBookButtonHBox.setAlignment(Pos.BOTTOM_LEFT);
        sellBookButtonHBox.getChildren().add(sellBookButton);
        gridPane.add(sellBookButtonHBox, 0, 5);


        soldBooksButton = new Button("Sold Books");
        HBox soldBooksButtonHBox = new HBox(10);
        soldBooksButtonHBox.setAlignment(Pos.BOTTOM_LEFT);
        soldBooksButtonHBox.getChildren().add(soldBooksButton);
        gridPane.add(soldBooksButtonHBox, 1, 5);

        buyBookButton = new Button("Buy Book");
        HBox buyBookButtonHBox = new HBox(10);
        buyBookButtonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        buyBookButtonHBox.getChildren().add(buyBookButton);
        gridPane.add(buyBookButtonHBox, 2, 5);


        logoutButton = new Button("Logout");
        HBox logOutButtonHBox = new HBox(10);
        logOutButtonHBox.setAlignment(Pos.BOTTOM_LEFT);
        logOutButtonHBox.getChildren().add(logoutButton);
        gridPane.add(logOutButtonHBox, 3, 5);

    }
    public void addLogoutButtonListener(EventHandler<ActionEvent> logoutButtonListener) {
        logoutButton.setOnAction(logoutButtonListener);
    }
    public void addSoldBooksButtonButtonListener(EventHandler<ActionEvent> soldBooksButtonButtonListener) {
        soldBooksButton.setOnAction(soldBooksButtonButtonListener);
    }

    public void addSellBookButtonButtonListener(EventHandler<ActionEvent> sellBookButtonButtonListener) {
        sellBookButton.setOnAction(sellBookButtonButtonListener);
    }

    public void addBuyBookButtonButtonListener(EventHandler<ActionEvent> buyBookButtonButtonListener) {
        buyBookButton.setOnAction(buyBookButtonButtonListener);
    }

    public Stage getStage() {
        return stage;
    }
    public void showStage(Boolean flag) {
        stage.setScene(scene);
        if(flag)
            stage.show();
        else
            stage.close();
    }
    public BookInterface getSelectedBook(){
        return table.getSelectionModel().getSelectedItem();
    }
    public void initializeTextSellBook(GridPane gridPane){
        textSellBook = new Text();
        HBox textSellBookHBox = new HBox(10);
        textSellBookHBox.setAlignment(Pos.BOTTOM_RIGHT);
        textSellBookHBox.getChildren().add(textSellBook);

        gridPane.add(textSellBook, 1, 6);
    }
    public void setTextSellBook (String text){
        textSellBook.setText(text);
    }
    public void setMoneyText(String text){
        moneyText.setText(text);
    }
    public void setUsernameText(String text) {
        usernameText.setText(text);
    }
}
