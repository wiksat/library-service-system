package pl.edu.agh.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pl.edu.agh.enums.UserRoleEnum;
import pl.edu.agh.session.UserSession;

import java.io.IOException;

@Component
public class MainController {

    private Stage primaryStage;
    private ApplicationContext context;
    @FXML
    Button CatalogButton;
    @FXML
    Button AddBookButton;
    @FXML
    Button AdminViewButton;
    @FXML
    Button LogoutButton;
    @FXML
    Button ShowStatsLibrarianButton;
    @FXML
    Button ShowStatsUserButton;
    @FXML
    Button BorrowedBooksButton;
    @FXML
    Button SendEmailButton;

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
            loader.setLocation(MainController.class.getResource("/view/mainView.fxml"));
            BorderPane mainLayout = loader.load();

            initButtons();

            Scene scene = new Scene(mainLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initButtons() {
        UserSession session = UserSession.getInstance();
        UserRoleEnum role = session.getRole();
        CatalogButton.setVisible(role != UserRoleEnum.NOT_LOGGED);
        CatalogButton.setManaged(role != UserRoleEnum.NOT_LOGGED);
        BorrowedBooksButton.setVisible(role != UserRoleEnum.NOT_LOGGED);
        BorrowedBooksButton.setManaged(role != UserRoleEnum.NOT_LOGGED);
        AddBookButton.setVisible(role == UserRoleEnum.LIBRARIAN);
        AddBookButton.setManaged(role == UserRoleEnum.LIBRARIAN);
        AdminViewButton.setVisible(role == UserRoleEnum.ADMIN);
        AdminViewButton.setManaged(role == UserRoleEnum.ADMIN);
        ShowStatsLibrarianButton.setVisible(role == UserRoleEnum.LIBRARIAN);
        ShowStatsLibrarianButton.setManaged(role == UserRoleEnum.LIBRARIAN);
        ShowStatsUserButton.setVisible(role == UserRoleEnum.MEMBER);
        ShowStatsUserButton.setManaged(role == UserRoleEnum.MEMBER);
        LogoutButton.setVisible(role != UserRoleEnum.NOT_LOGGED);
        LogoutButton.setManaged(role != UserRoleEnum.NOT_LOGGED);
        SendEmailButton.setVisible(role == UserRoleEnum.LIBRARIAN);
        SendEmailButton.setManaged(role == UserRoleEnum.LIBRARIAN);
    }

    public void handleCatalogClickAction() {
        CatalogController catalogController = context.getBean(CatalogController.class);
        catalogController.setPrimaryStage(primaryStage);
        catalogController.loadView();
    }
    public void handleShowBorrowedClickAction() {
        BorrowedBooksController borrowedBooksController = context.getBean(BorrowedBooksController.class);
        borrowedBooksController.setPrimaryStage(primaryStage);
        borrowedBooksController.loadView();
    }
    public void handleAddBookClickAction() {
        AddBookController addBookController = context.getBean(AddBookController.class);
        addBookController.setPrimaryStage(primaryStage);
        addBookController.loadView();
    }

    public void handleAdminClickAction() {
        AdminController adminController = context.getBean(AdminController.class);
        adminController.setPrimaryStage(primaryStage);
        adminController.loadView();
    }

    public void handleSendEmailClickAction() {
        SendEmailController sendEmailController = context.getBean(SendEmailController.class);
        sendEmailController.setPrimaryStage(primaryStage);
        sendEmailController.loadView();
    }

    public void handleShowStatsLibrarianClickAction() {
        LibrarianStatsController librarianStatsController = context.getBean(LibrarianStatsController.class);
        librarianStatsController.setPrimaryStage(primaryStage);
        librarianStatsController.loadView();
    }

    public void handleShowStatsUserClickAction() {
        UserStatsController userStatsController = context.getBean(UserStatsController.class);
        userStatsController.setPrimaryStage(primaryStage);
        userStatsController.loadView();
    }

    public void handleLogoutClickAction() {
        UserSession session = UserSession.getInstance();
        session.setUser(null);
        session.setRole(UserRoleEnum.NOT_LOGGED);
        LoginController loginController = context.getBean(LoginController.class);
        loginController.setPrimaryStage(primaryStage);
        loginController.loadView();
    }
}
