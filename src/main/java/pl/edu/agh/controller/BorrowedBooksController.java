package pl.edu.agh.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pl.edu.agh.model.extra.HistoricalLoanDetails;
import pl.edu.agh.model.extra.LoanDetails;
import pl.edu.agh.model.loans.HistoricalLoan;
import pl.edu.agh.model.users.User;
import pl.edu.agh.service.BookService;
import pl.edu.agh.session.UserSession;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Component
public class BorrowedBooksController {

    @FXML
    public TableView<LoanDetails> borrowedBooksTable;
    @FXML
    public TableColumn<LoanDetails, String> AuthorColumn;
    @FXML
    public TableColumn<LoanDetails, String> TitleColumn;
    @FXML
    public TableColumn<LoanDetails, Date> StartDateColumn;
    @FXML
    public TableColumn<LoanDetails, Date> EndDateColumn;
    @FXML
    public TableView<HistoricalLoanDetails> historicalBorrowsTable;
    @FXML
    public TableColumn<HistoricalLoanDetails, String> HistoricalAuthorColumn;
    @FXML
    public TableColumn<HistoricalLoanDetails, String> HistoricalTitleColumn;
    @FXML
    public TableColumn<HistoricalLoanDetails, Date> HistoricalStartDateColumn;
    @FXML
    public TableColumn<HistoricalLoanDetails, Date> HistoricalEndDateColumn;
    @FXML
    public TableColumn<HistoricalLoanDetails, Date> HistoricalReturnDateColumn;
    @FXML
    private Button returnButton;
    private Stage primaryStage;
    private ApplicationContext context;

    private final UserSession session = UserSession.getInstance();
    private final BookService bookService;

    @Autowired
    public BorrowedBooksController(BookService bookService) {
        this.bookService = bookService;
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
            loader.setLocation(CatalogController.class.getResource("/view/BorrowedBooks.fxml"));
            BorderPane mainLayout = loader.load();
            Scene scene = new Scene(mainLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void initialize() {
        User user = session.getUser();

        borrowedBooksTable.getItems().clear();
        borrowedBooksTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        List<LoanDetails> loansList = bookService.getAllUserLoans(user);
        borrowedBooksTable.getItems().addAll(loansList);
        AuthorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().title().getAuthor()));
        TitleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().title().getTitle()));
        StartDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().startLoanDate()));
        EndDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().endLoanDate()));
        borrowedBooksTable.setPlaceholder(new Label("Nie masz żadnych wypożyczonych książek"));

        returnButton.disableProperty().bind(Bindings.isEmpty(borrowedBooksTable.getSelectionModel().getSelectedItems()));

        historicalBorrowsTable.getItems().clear();
        List<HistoricalLoanDetails> historicalLoansList = bookService.getAllUserHistoricalLoans(user);
        historicalBorrowsTable.getItems().addAll(historicalLoansList);
        HistoricalAuthorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().title().getAuthor()));
        HistoricalTitleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().title().getTitle()));
        HistoricalStartDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().startLoanDate()));
        HistoricalEndDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().endLoanDate()));
        HistoricalReturnDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().returnLoanDate()));
        historicalBorrowsTable.setPlaceholder(new Label("Nie masz żadnych historycznych wypożyczeń"));
    }

    public void handleBackClickAction() {
        MainController mainController = context.getBean(MainController.class);
        mainController.setPrimaryStage(primaryStage);
        mainController.loadView();
    }

    public void handleReturnAction() {
        LoanDetails loanDetails = borrowedBooksTable.getSelectionModel().getSelectedItem();

        HistoricalLoan historicalLoan = bookService.returnBook(loanDetails.loanId());

        if(historicalLoan != null) {
            borrowedBooksTable.getItems().remove(borrowedBooksTable.getSelectionModel().getSelectedIndex());
        }
    }
}
