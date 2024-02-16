package pl.edu.agh.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pl.edu.agh.model.books.BookCategory;
import pl.edu.agh.service.BookService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

@Component
public class AddBookController {

    private Stage primaryStage;
    private ApplicationContext context;

    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField isbnField;
    @FXML
    private ChoiceBox<BookCategory> categoryChoiceBox;
    @FXML
    private TextField softCoverQuantityField;
    @FXML
    private TextField hardCoverQuantityField;
    @FXML
    private ImageView imageView;
    @FXML
    private Label resultLabel;

    private Blob imageBlob;

    private BookService bookService;


    @Autowired
    public AddBookController(BookService bookService) {
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
            loader.setLocation(AddBookController.class.getResource("/view/AddBookView.fxml"));
            BorderPane mainLayout = loader.load();

            categoryChoiceBox.getItems().addAll(BookCategory.values());

            Scene scene = new Scene(mainLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void chooseImageFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz zdjęcie");

        // Filtry do wyboru pliku (np. tylko obrazy)
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Pliki graficzne", "*.jpg", "*.jpeg", "*.png", "*.gif", "*.bmp");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            try {
                byte[] fileContent = readBytesFromFile(selectedFile);
                imageBlob = createBlob(fileContent);
                showImage(selectedFile);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] readBytesFromFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] fileContent = new byte[(int) file.length()];
        fis.read(fileContent);
        fis.close();
        return fileContent;
    }

    private Blob createBlob(byte[] fileContent) throws SQLException {
        return new javax.sql.rowset.serial.SerialBlob(fileContent);
    }

    private void showImage(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        Image image = new Image(fis);

        imageView.setImage(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setTranslateY(15);

        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

        fis.close();
    }


    public void handleAddBookAction() {
        String title = titleField.getText();
        String author = authorField.getText();
        String isbn = isbnField.getText();
        String softCoverQuantity = softCoverQuantityField.getText();
        String hardCoverQuantity = hardCoverQuantityField.getText();
        BookCategory category = (BookCategory) categoryChoiceBox.getValue();

        String result = this.bookService.addBook(title, author, isbn, category, imageBlob, softCoverQuantity, hardCoverQuantity);
        showResult(result);
    }

    private void showResult(String labelText) {
        if ("Ksiazka zostala dodana".equals(labelText)) {
            resultLabel.setStyle("-fx-text-fill: green;");

            // clear fields if succesfully added
            clearInputFields();
        } else {
            resultLabel.setStyle("-fx-text-fill: red;");
        }
        resultLabel.setText(labelText);
    }

    private void clearInputFields() {
        titleField.setText(null);
        authorField.setText(null);
        isbnField.setText(null);
        categoryChoiceBox.setValue(null);
    }

    public void handleBackClickAction() {
        MainController mainController = context.getBean(MainController.class);
        mainController.setPrimaryStage(primaryStage);
        mainController.loadView();
    }
}
