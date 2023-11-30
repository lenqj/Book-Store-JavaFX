package view.Employee;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.converter.LongStringConverter;

import java.time.LocalDate;

public class EmployeeUpdateBookView {
    private final GridPane gridPane;
    private Button updateButton;
    private Label bookAuthorLabel;
    private Label bookTitleLabel;
    private Label bookPublishedDateLabel;
    private Label bookStockLabel;
    private Label bookPriceLabel;
    private TextField authorTextField;
    private TextField titleTextField;
    private DatePicker publishedDateDatePicker;
    private TextField stockTextField;
    private TextField priceTextField;
    public EmployeeUpdateBookView() {
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
        bookAuthorLabel = new Label();
        bookTitleLabel = new Label();
        bookPublishedDateLabel = new Label();
        bookStockLabel = new Label();
        bookPriceLabel = new Label();

        gridPane.add(bookAuthorLabel, 0, 0, 1, 1);
        gridPane.add(bookTitleLabel, 1, 0, 1, 1);
        gridPane.add(bookPublishedDateLabel, 2, 0, 1, 1);
        gridPane.add(bookStockLabel, 3, 0, 1, 1);
        gridPane.add(bookPriceLabel, 4, 0, 2, 1);


        authorTextField = new TextField();
        titleTextField = new TextField();
        publishedDateDatePicker = new DatePicker();
        stockTextField = new TextField();
        stockTextField.setTextFormatter(new TextFormatter<>(new LongStringConverter()));
        priceTextField = new TextField();
        stockTextField.setTextFormatter(new TextFormatter<>(new LongStringConverter()));


        Label authorLabel = new Label("Author: ");
        Label titleLabel = new Label("Title: ");
        Label publishedDateLabel = new Label("Published Date: ");
        Label stockLabel = new Label("Stock: ");
        Label priceLabel = new Label("Price: ");

        gridPane.add(authorTextField, 1, 2, 1, 1);
        gridPane.add(titleTextField, 1, 3, 1, 1);
        gridPane.add(publishedDateDatePicker, 1, 4, 1, 1);
        gridPane.add(stockTextField, 1, 5, 1, 1);
        gridPane.add(priceTextField, 1, 6, 2, 1);


        gridPane.add(authorLabel, 0, 2, 1, 1);
        gridPane.add(titleLabel, 0, 3, 1, 1);
        gridPane.add(publishedDateLabel, 0, 4, 1, 1);
        gridPane.add(stockLabel, 0, 5, 1, 1);
        gridPane.add(priceLabel, 0, 6, 2, 1);

    }
    private void initializeButtons(GridPane gridPane){
        updateButton = new Button("UPDATE");
        HBox updateButtonHBox = new HBox(10);
        updateButtonHBox.setAlignment(Pos.BOTTOM_CENTER);
        updateButtonHBox.getChildren().add(updateButton);
        gridPane.add(updateButtonHBox, 0, 6, 3, 1);
    }

    public void setUserLabels(String author, String title, LocalDate publishedDate, Long stock, Long price){
        bookAuthorLabel.setText("Author: " + author);
        bookTitleLabel.setText("Title: " + title);
        bookPublishedDateLabel.setText("Published Date: " + publishedDate);
        bookStockLabel.setText("Stock: " + stock);
        bookPriceLabel.setText("Price: " + price);
    }

    public String getAuthorTextField(){
        return authorTextField.getText() == null || authorTextField.getText().isEmpty() ? null : authorTextField.getText();
    }
    public String getTitleTextField(){
        return titleTextField.getText() == null || titleTextField.getText().isEmpty() ? null : titleTextField.getText();
    }
    public LocalDate getPublishedDateDatePicker() {
        return publishedDateDatePicker.getValue();
    }
    public Long getStockTextField(){
        return Long.valueOf(stockTextField.getText());
    }
    public Long getPriceTextField(){
        return Long.valueOf(priceTextField.getText());
    }
    public void addUpdateButtonListener(EventHandler<ActionEvent> updateButtonListener) {
        updateButton.setOnAction(updateButtonListener);
    }
    public Pane getPane() {
        return gridPane;
    }


}
