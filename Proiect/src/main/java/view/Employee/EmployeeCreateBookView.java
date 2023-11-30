package view.Employee;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static database.Constants.Roles.ROLES;

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
        gridPane.add(createButtonHBox, 0, 4, 2, 1);
    }



    public void addCreateButtonListener(EventHandler<ActionEvent> createButtonListener) {
        createButton.setOnAction(createButtonListener);
    }
    public Pane getPane() {
        return gridPane;
    }


}
