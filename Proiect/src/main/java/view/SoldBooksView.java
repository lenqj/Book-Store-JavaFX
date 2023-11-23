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
import service.book.BookService;

import java.util.List;
import java.util.Optional;

public class SoldBooksView {
    private final Stage stage;
    private TableView<BookInterface> table;
    private Button backButton;
    private Button deleteButton;

    public SoldBooksView(User user) {
        stage = new Stage();
        stage.setTitle("Book Store ");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 720, 480);
        stage.setScene(scene);

        initializeSceneTitle(gridPane, user);

        table = new TableView<>();
        initializeTableView(table, gridPane);

        initializeButtons(gridPane);
        showStage(true);
    }

    private void initializeGridPane(GridPane gridPane){
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    private void initializeSceneTitle(GridPane gridPane, User user){
        Text sceneTitle = new Text("Sold books for " + user.getUsername());
        sceneTitle.setFont(Font.font("Thoma", FontWeight.NORMAL, 20));
        gridPane.add(sceneTitle, 0, 0, 2, 1);
    }
    @SuppressWarnings("unchecked")
    private void initializeTableView(TableView<BookInterface> tableView, GridPane gridPane){
        gridPane.add(table, 0, 1, 2, 1);
        TableColumn<BookInterface, String> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<BookInterface,String> author = new TableColumn<>("Author");
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        TableColumn<BookInterface,String> title = new TableColumn<>("Title");
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<BookInterface,String> publishedDate = new TableColumn<>("Published Date");
        publishedDate.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));
        tableView.getColumns().setAll(  id, author, title, publishedDate);
    }
    public void setTableBookList(List<BookInterface> books){
        table.getItems().clear();
        for (BookInterface book: books)
            table.getItems().add(book);
    }

    private void initializeButtons(GridPane gridPane){
        deleteButton = new Button("Delete");
        HBox deleteButtonHBox = new HBox(10);
        deleteButtonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        deleteButtonHBox.getChildren().add(deleteButton);
        gridPane.add(deleteButtonHBox, 0, 5);


        backButton = new Button("Back");
        HBox backButtonHBox = new HBox(10);
        backButtonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        backButtonHBox.getChildren().add(backButton);
        gridPane.add(backButtonHBox, 1, 5);
    }
    public void addBackButtonListener(EventHandler<ActionEvent> backButtonListener) {
        backButton.setOnAction(backButtonListener);
    }
    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonListener) {
        deleteButton.setOnAction(deleteButtonListener);
    }
    public BookInterface getSelectedBook(){
        return table.getSelectionModel().getSelectedItem();
    }
    public void showStage(boolean flag){
        if(flag)
            stage.show();
        else
            stage.close();
    }
}
