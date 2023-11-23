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
    private Stage stage;
    private TableView<BookInterface> table;
    private Button logoutButton;
    private Button soldBooksButton;
    private Button sellBookButton;
    private Text textSellBook;

    public CustomerView(Stage primaryStage, User userLoggedIn) {
        stage = primaryStage;
        stage.setTitle("Book Store ");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 720, 480);
        stage.setScene(scene);

        initializeSceneTitle(gridPane, userLoggedIn);

        table = new TableView<>();
        initializeTableView(table, gridPane);

        initializeButtons(gridPane);

        initializeTextSellBook(gridPane);

        stage.show();
    }

    private void initializeGridPane(GridPane gridPane){
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    private void initializeSceneTitle(GridPane gridPane, User user){
        Text sceneTitle = new Text("Logged IN as " + user.getUsername());
        sceneTitle.setFont(Font.font("Thoma", FontWeight.NORMAL, 20));
        gridPane.add(sceneTitle, 0, 0, 2, 1);
    }
    @SuppressWarnings("unchecked")
    private void initializeTableView(TableView<BookInterface> tableView, GridPane gridPane){
        gridPane.add(table, 0, 1, 2, 1);
        TableColumn<BookInterface,String> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<BookInterface,String> author = new TableColumn<>("Author");
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        TableColumn<BookInterface,String> title = new TableColumn<>("Title");
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<BookInterface,String> publishedDate = new TableColumn<>("Published Date");
        publishedDate.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));
        tableView.getColumns().setAll(id, author, title, publishedDate);
    }
    public void setTableBookList(List<BookInterface> books){
        table.getItems().clear();
        for (BookInterface book: books)
            table.getItems().add(book);
    }

    private void initializeButtons(GridPane gridPane){
        logoutButton = new Button("Logout");
        HBox logOutButtonHBox = new HBox(10);
        logOutButtonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        logOutButtonHBox.getChildren().add(logoutButton);
        gridPane.add(logOutButtonHBox, 2, 5);


        soldBooksButton = new Button("Sold Books");
        HBox soldBooksButtonHBox = new HBox(10);
        soldBooksButtonHBox.setAlignment(Pos.BOTTOM_CENTER);
        soldBooksButtonHBox.getChildren().add(soldBooksButton);
        gridPane.add(soldBooksButtonHBox, 1, 5);

        sellBookButton = new Button("Sell Book");
        HBox sellBookButtonHBox = new HBox(10);
        sellBookButtonHBox.setAlignment(Pos.BOTTOM_LEFT);
        sellBookButtonHBox.getChildren().add(sellBookButton);
        gridPane.add(sellBookButtonHBox, 0, 5);

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

    public Stage getStage() {
        return stage;
    }
    public BookInterface getSelectedBook(){
        return table.getSelectionModel().getSelectedItem();
    }
    public void initializeTextSellBook(GridPane gridPane){
        textSellBook = new Text();
        gridPane.add(textSellBook, 0, 6);
    }
    public void setTextSellBook (String text){
        textSellBook.setText(text);
    }
}
