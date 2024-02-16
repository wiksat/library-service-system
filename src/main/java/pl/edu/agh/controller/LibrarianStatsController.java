package pl.edu.agh.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pl.edu.agh.model.response.IBookResponse;
import pl.edu.agh.service.LibrarianStatsService;

import java.io.IOException;
import java.util.List;

@Component
public class LibrarianStatsController {
    @FXML
    public TableColumn TitleColumnCurrent;
    @FXML
    public TableColumn AmountColumnCurrent;
    @FXML
    public TableView mostCurrentTable;
    @FXML
    public TableColumn TitleColumnHistorical;
    @FXML
    public TableColumn AmountColumnHistorical;
    @FXML
    public TableView mostHistoricalTable;
    @FXML
    public TextField amountTitlesField;
    @FXML
    public TextField amountAllBooksField;
    @FXML
    public TextField amountCurrentlyBorrowedBooks;
    private Stage primaryStage;

    private ApplicationContext context;

    private final LibrarianStatsService librarianStatsService;

    @Autowired
    public LibrarianStatsController(LibrarianStatsService librarianStatsService) {
        this.librarianStatsService = librarianStatsService;
    }

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void loadView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setControllerFactory(aClass -> context.getBean(aClass));
            loader.setLocation(AdminController.class.getResource("/view/LibrarianStatsView.fxml"));
            BorderPane mainLayout = loader.load();
            Scene scene = new Scene(mainLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        mostCurrentTable.getItems().clear();
        List<IBookResponse> theMostFrequentlyCurrentlyBorrowedBooks = librarianStatsService.findTheMostFrequentlyCurrentlyBorrowedBooks();
        mostCurrentTable.getItems().addAll(theMostFrequentlyCurrentlyBorrowedBooks);
        TitleColumnCurrent.setCellValueFactory(new PropertyValueFactory<>("title"));
        AmountColumnCurrent.setCellValueFactory(new PropertyValueFactory<>("amount"));

        mostHistoricalTable.getItems().clear();
        List<IBookResponse> theMostFrequentlyHistoricalBorrowedBooks = librarianStatsService.findTheMostFrequentlyHistoricalBorrowedBooks();
        mostHistoricalTable.getItems().addAll(theMostFrequentlyHistoricalBorrowedBooks);
        TitleColumnHistorical.setCellValueFactory(new PropertyValueFactory<>("title"));
        AmountColumnHistorical.setCellValueFactory(new PropertyValueFactory<>("amount"));

        amountTitlesField.setText(librarianStatsService.getAmountOfTitles().toString());
        amountAllBooksField.setText(librarianStatsService.getAmountOfAllBooks().toString());
        amountCurrentlyBorrowedBooks.setText(librarianStatsService.getAmountOfCurrentlyBorrowedBooks().toString());

    }

    public void handleBackClickAction() {
        MainController mainController = context.getBean(MainController.class);
        mainController.setPrimaryStage(primaryStage);
        mainController.loadView();
    }
}
