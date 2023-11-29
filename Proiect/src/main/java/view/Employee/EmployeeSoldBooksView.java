package view.Employee;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.book.BookInterface;

import java.util.Map;

public class EmployeeSoldBooksView {
    private final GridPane gridPane;
    private final TableView<Map.Entry<Long, BookInterface>> table;
    private Button backButton;
    private Button deleteButton;
    private Text sceneTitle;

    public EmployeeSoldBooksView() {
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
        sceneTitle = new Text();
        sceneTitle.setFont(Font.font("Thoma", FontWeight.NORMAL, 20));
        gridPane.add(sceneTitle, 0, 0, 6, 1);
    }
    @SuppressWarnings("unchecked")
    private void initializeTableView(TableView<Map.Entry<Long, BookInterface>> tableView, GridPane gridPane){
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        ScrollPane sp = new ScrollPane(tableView);
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        gridPane.add(sp, 0, 1, 6, 5);
        TableColumn<Map.Entry<Long, BookInterface>, String> id = new TableColumn<>("#");
        id.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getKey())));

        TableColumn<Map.Entry<Long, BookInterface>, String> book_id = new TableColumn<>("Book ID");
        book_id.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getValue().getId())));

        TableColumn<Map.Entry<Long, BookInterface>, String> author = new TableColumn<>("Author");
        author.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getAuthor()));

        TableColumn<Map.Entry<Long, BookInterface>, String> title = new TableColumn<>("Title");
        title.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getTitle()));

        TableColumn<Map.Entry<Long, BookInterface>, String> publishedDate = new TableColumn<>("Published Date");
        publishedDate.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getValue().getPublishedDate())));
        tableView.getColumns().setAll(id, book_id, author, title, publishedDate);
    }
    public void setTableBookList(Map<Long, BookInterface> books){
        table.getItems().clear();
        table.getItems().addAll(books.entrySet());
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
        gridPane.add(backButtonHBox, 5, 7);
    }

    public void setSceneTitle(String sceneTitle) {
        this.sceneTitle.setText(sceneTitle);
    }

    public void addBackButtonListener(EventHandler<ActionEvent> backButtonListener) {
        backButton.setOnAction(backButtonListener);
    }
    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonListener) {
        deleteButton.setOnAction(deleteButtonListener);
    }
    public Long getSelectedBook(){
        return table.getSelectionModel().getSelectedItem().getKey();
    }
    public Pane getPane() {
        return gridPane;
    }
}
