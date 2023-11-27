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
import model.book.BookInterface;

import java.util.List;

public class SoldBooksView {
    private final Stage stage;
    private final Scene scene;
    private final TableView<BookInterface> table;
    private Button backButton;
    private Button deleteButton;
    private Text sceneTitle;

    public SoldBooksView(Stage stage) {
        this.stage = stage;
        stage.setTitle("Book Store ");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        scene = new Scene(gridPane, 720, 480);

        initializeSceneTitle(gridPane);

        table = new TableView<>();
        initializeTableView(table, gridPane);

        initializeButtons(gridPane);
    }

    private void initializeGridPane(GridPane gridPane){
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    private void initializeSceneTitle(GridPane gridPane){
        sceneTitle = new Text();
        sceneTitle.setFont(Font.font("Thoma", FontWeight.NORMAL, 20));
        gridPane.add(sceneTitle, 0, 0, 4, 1);
    }
    @SuppressWarnings("unchecked")
    private void initializeTableView(TableView<BookInterface> tableView, GridPane gridPane){
        gridPane.add(table, 0, 1, 4, 4);
        TableColumn<BookInterface, String> id = new TableColumn<>("ID");
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
        deleteButton = new Button("Delete");
        HBox deleteButtonHBox = new HBox(10);
        deleteButtonHBox.setAlignment(Pos.BOTTOM_LEFT);
        deleteButtonHBox.getChildren().add(deleteButton);
        gridPane.add(deleteButtonHBox, 0, 7);


        backButton = new Button("Back");
        HBox backButtonHBox = new HBox(10);
        backButtonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        backButtonHBox.getChildren().add(backButton);
        gridPane.add(backButtonHBox, 1, 7);
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
    public void showStage(Boolean flag) {
        stage.setScene(scene);
        if(flag)
            stage.show();
        else
            stage.close();
    }
}
