package controller.Employee;

import com.itextpdf.text.DocumentException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.ComponentFactory;
import model.PDF;
import view.Employee.EmployeeView;

import java.io.IOException;


public class EmployeeController {
    private final EmployeeView employeeView;
    public EmployeeController(EmployeeView employeeView) {
        this.employeeView = employeeView;
        employeeView.showPane(ComponentFactory.getEmployeeBooksView().getPane());
        employeeView.addBooksMenuListener(new BooksMenuListener());
        employeeView.addSoldBooksMenuListener(new SoldBooksMenuListener());
        employeeView.addLogoutMenuListener(new LogoutMenuListener());
        employeeView.addReportMenuListener(new ReportMenuListener());
    }
    private static class BooksMenuListener implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            ComponentFactory.getEmployeeView().showPane(ComponentFactory.getEmployeeBooksView().getPane());
            ComponentFactory.getEmployeeBooksView().setUsernameText(ComponentFactory.getLoginController().getLoginNotification().getResult().getUsername());
            ComponentFactory.getEmployeeBooksView().setMoneyText("Money: " + ComponentFactory.getLoginController().getLoginNotification().getResult().getMoney());
            ComponentFactory.getEmployeeBooksView().setTableBookList(ComponentFactory.getBookService().findAllSellableBooks(Boolean.TRUE));
            ComponentFactory.getEmployeeBooksView().clearTexts();
        }
    }
    private static class SoldBooksMenuListener implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            ComponentFactory.getEmployeeView().showPane(ComponentFactory.getEmployeeSoldBooksView().getPane());
            ComponentFactory.getEmployeeSoldBooksView().setSceneTitle("Sold books by: " + ComponentFactory.getLoginController().getLoginNotification().getResult().getUsername());
            ComponentFactory.getEmployeeSoldBooksView().setTableBookList(ComponentFactory.getUserBooksService().findAllSoldBooks(ComponentFactory.getLoginController().getLoginNotification().getResult()));
        }
    }
    private static class LogoutMenuListener implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            ComponentFactory.getMainView().showScene(ComponentFactory.getLoginView().getScene());
            ComponentFactory.getLoginView().clearTexts();
            ComponentFactory.getMainView().setStageTitle("Welcome to our Book Store");
        }
    }
    private static class ReportMenuListener implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            PDF pdfContentGenerator = new PDF();

            final String fileName = "sample.pdf";

            try {
                pdfContentGenerator.generatePdf(fileName,ComponentFactory.getLoginController().getLoginNotification().getResult(), ComponentFactory.getUserBooksService().findAllSoldBooks(ComponentFactory.getLoginController().getLoginNotification().getResult()));
            } catch (DocumentException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}