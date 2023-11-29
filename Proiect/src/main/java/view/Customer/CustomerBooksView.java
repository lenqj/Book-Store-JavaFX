package view.Customer;

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
import model.book.BookInterface;

import java.util.List;

public class CustomerBooksView {
    private final GridPane gridPane;
    private final TableView<BookInterface> table;
    private Button logoutButton;
    private Button buyBookButton;
    private Text usernameText;
    private Text textSellBook;
    private Text moneyText;

    public CustomerBooksView() {
        gridPane = new GridPane();
        initializeGridPane();
        initializeSceneTitle();
        table = new TableView<>();
        initializeTableView(table);
        initializeButtons();
        initializeTextSellBook();
    }

    private void initializeGridPane(){
        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(100d / 7);
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

    private void initializeSceneTitle(){
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
    private void initializeTableView(TableView<BookInterface> tableView){
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        ScrollPane sp = new ScrollPane(tableView);
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        gridPane.add(sp, 0, 1, 6, 5);
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

    private void initializeButtons(){

        buyBookButton = new Button("Buy Book");
        HBox buyBookButtonHBox = new HBox();
        buyBookButtonHBox.setAlignment(Pos.BASELINE_RIGHT);
        buyBookButtonHBox.getChildren().add(buyBookButton);
        gridPane.add(buyBookButtonHBox, 0, 7);


        logoutButton = new Button("Logout");
        HBox logOutButtonHBox = new HBox();
        logOutButtonHBox.setAlignment(Pos.BASELINE_RIGHT);
        logOutButtonHBox.getChildren().add(logoutButton);
        gridPane.add(logOutButtonHBox, 5, 7);

    }

    public BookInterface getSelectedBook(){
        return table.getSelectionModel().getSelectedItem();
    }
    public void initializeTextSellBook(){
        textSellBook = new Text();
        HBox textSellBookHBox = new HBox();
        textSellBookHBox.setAlignment(Pos.BASELINE_LEFT);
        textSellBookHBox.getChildren().add(textSellBook);

        gridPane.add(textSellBook, 0, 6, 6, 1);
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
    public void clearTexts(){
        textSellBook.setText("");
    }
    public void addLogoutButtonListener(EventHandler<ActionEvent> logoutButtonListener) {
        logoutButton.setOnAction(logoutButtonListener);
    }
    public void addBuyBookButtonButtonListener(EventHandler<ActionEvent> buyBookButtonButtonListener) {
        buyBookButton.setOnAction(buyBookButtonButtonListener);
    }
    public Pane getPane() {
        return gridPane;
    }

}
