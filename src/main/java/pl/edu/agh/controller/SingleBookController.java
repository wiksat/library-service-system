package pl.edu.agh.controller;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pl.edu.agh.model.books.Book;
import pl.edu.agh.model.books.CoverType;
import pl.edu.agh.model.books.Rating;
import pl.edu.agh.model.books.Title;
import pl.edu.agh.model.users.Member;
import pl.edu.agh.model.users.User;
import pl.edu.agh.service.BookService;
import pl.edu.agh.session.UserSession;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Component
public class SingleBookController {

    @FXML
    private ImageView imageView;
    @FXML
    private TextField authorTextField;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField categoryTextField;
    @FXML
    private TextField availableSoftTextField;
    @FXML
    private TextField availableHardTextField;
    @FXML
    private TableView<Rating> ratingTable;
    @FXML
    private TableColumn<Rating, Integer> RateColumn;
    @FXML
    private TableColumn<Rating, String> CommentColumn;

    @FXML
    private Button loanButtonSoft;
    @FXML
    private Button loanButtonHard;
    private Long numberOfAvailableSoft;
    private Long numberOfAvailableHard;


    private Stage primaryStage;
    private ApplicationContext context;
    private final UserSession session = UserSession.getInstance();
    private Title title;
    private final BookService bookService;
    @Autowired
    public SingleBookController(BookService bookService) {
        this.bookService = bookService;
    }

    public void setTitle(Title title) {
        this.title = title;
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
            loader.setLocation(SingleBookController.class.getResource("/view/SingleBookView.fxml"));
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
        numberOfAvailableSoft = bookService.getNumberOfAvailableBooks(title.getTitleId(), CoverType.SOFT);
        numberOfAvailableHard = bookService.getNumberOfAvailableBooks(title.getTitleId(), CoverType.HARD);
        authorTextField.setText(title.getAuthor());
        titleTextField.setText(title.getTitle());
        categoryTextField.setText(title.getCategory().toString());
        availableSoftTextField.setText(numberOfAvailableSoft.toString());
        availableHardTextField.setText(numberOfAvailableHard.toString());

        try {
            showImage(title.getImage());
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        ratingTable.getItems().clear();
        ratingTable.setPlaceholder(new Label("Brak ocen"));
        List<Rating> ratingsList = bookService.findRatingsByTitleId(title.getTitleId());
        ratingTable.getItems().addAll(ratingsList);
        RateColumn.setCellValueFactory(new PropertyValueFactory<>("rate"));
        CommentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));

    }

    private void showImage(Blob blobImage) throws IOException, SQLException {
        InputStream in = blobImage.getBinaryStream();
        BufferedImage bufferedImage = ImageIO.read(in);
        Image image = SwingFXUtils.toFXImage(bufferedImage, null );
        imageView.setImage(image);
        imageView.setFitWidth(250);
        imageView.setFitHeight(250);

        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

    }
    public void handleBackClickAction() {
        CatalogController catalogController = context.getBean(CatalogController.class);
        catalogController.setPrimaryStage(primaryStage);
        catalogController.loadView();
    }

    public void hadleLoanSoftAction() {
        if(numberOfAvailableSoft < 1L) return;
        if(bookService.reserveBook(title.getTitleId(), CoverType.SOFT, session.getUser()) != null)
            availableSoftTextField.setText(bookService.getNumberOfAvailableBooks(title.getTitleId(), CoverType.SOFT).toString());
    }
    public void hadleLoanHardAction() {
        if(numberOfAvailableHard < 1L) return;
        if(bookService.reserveBook(title.getTitleId(), CoverType.HARD, session.getUser()) != null)
            availableHardTextField.setText(bookService.getNumberOfAvailableBooks(title.getTitleId(), CoverType.HARD).toString());
    }

    public void handleRateAction() {
        showRateAddingDialog();
    }

    public void showRateAddingDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setControllerFactory(aClass -> context.getBean(aClass));
            loader.setLocation(SingleBookController.class.getResource("/view/RateAddingDialog.fxml"));
            BorderPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Dodaj ocenę");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            RateDialogController rateDialogController = loader.getController();
            rateDialogController.setTitle(title);
            rateDialogController.setDialogStage(dialogStage);

            dialogStage.showAndWait();
            ratingTable.getItems().clear();
            ratingTable.getItems().addAll(bookService.findRatingsByTitleId(title.getTitleId()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
