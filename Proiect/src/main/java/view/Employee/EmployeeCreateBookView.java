package view.Employee;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.converter.LongStringConverter;

import java.sql.Date;
import java.time.LocalDate;

public class EmployeeCreateBookView {
    private final GridPane gridPane;
    private Button createButton;
    private TextField authorTextField;
    private TextField titleTextField;
    private DatePicker publishedDateDatePicker;
    private TextField stockTextField;
    private TextField priceTextField;
    public EmployeeCreateBookView() {
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

        authorTextField = new TextField();
        titleTextField = new TextField();
        publishedDateDatePicker = new DatePicker();
        stockTextField = new TextField();
        stockTextField.setTextFormatter(new TextFormatter<>(new LongStringConverter()));
        priceTextField = new TextField();
        stockTextField.setTextFormatter(new TextFormatter<>(new LongStringConverter()));

        Label authorLabel = new Label("Author: ");
        Label titleLabel = new Label("Title: ");
        Label publishedDateLabel = new Label("Published Date:");
        Label stockLabel = new Label("Stock:");

        Label priceLabel = new Label("Price");

        gridPane.add(authorTextField, 1, 0, 1, 1);
        gridPane.add(titleTextField, 1, 1, 1, 1);
        gridPane.add(publishedDateDatePicker, 1, 2, 1, 1);
        gridPane.add(stockTextField, 1, 3, 1, 1);
        gridPane.add(priceTextField, 1, 4, 1, 1);


        gridPane.add(authorLabel, 0, 0, 1, 1);
        gridPane.add(titleLabel, 0, 1, 1, 1);
        gridPane.add(publishedDateLabel, 0, 2, 1, 1);
        gridPane.add(stockLabel, 0, 3, 1, 1);
        gridPane.add(priceLabel, 0, 4, 1, 1);

    }
    private void initializeButtons(GridPane gridPane){
        createButton = new Button("CREATE");
        HBox createButtonHBox = new HBox(10);
        createButtonHBox.setAlignment(Pos.BOTTOM_CENTER);
        createButtonHBox.getChildren().add(createButton);
        gridPane.add(createButtonHBox, 0, 5, 2, 1);
    }
    public String getAuthorTextField(){
        return authorTextField.getText() == null || authorTextField.getText().isEmpty() ? null : authorTextField.getText();
    }
    public String getTitleTextField(){
        return titleTextField.getText() == null || titleTextField.getText().isEmpty() ? null : titleTextField.getText();
    }
    public LocalDate getPublishedDateDatePicker() {
        return publishedDateDatePicker.getValue() == null ? new Date(0, 0, 0).toLocalDate() : publishedDateDatePicker.getValue();
    }
    public Long getStockTextField(){
        return stockTextField.getText() == null || stockTextField.getText().isEmpty() ? 0L : Long.parseLong(stockTextField.getText());
    }
    public Long getPriceTextField(){
        return priceTextField.getText() == null || priceTextField.getText().isEmpty() ? 0L : Long.parseLong(priceTextField.getText());
    }
    public void addCreateButtonListener(EventHandler<ActionEvent> createButtonListener) {
        createButton.setOnAction(createButtonListener);
    }
    public Pane getPane() {
        return gridPane;
    }


}
