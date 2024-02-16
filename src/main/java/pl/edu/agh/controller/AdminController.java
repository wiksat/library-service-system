package pl.edu.agh.controller;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pl.edu.agh.model.users.AccountType;
import pl.edu.agh.model.users.User;
import pl.edu.agh.service.AdminService;

import java.io.IOException;
import java.util.List;

@Component
public class AdminController {
    @FXML
    public TableView<User> usersTable;

    @FXML
    public TableColumn<User, Integer> UserIdColumn;

    @FXML
    public TableColumn<User, String> FirstNameColumn;

    @FXML
    public TableColumn<User, String> LastNameColumn;

    @FXML
    public TableColumn<User, AccountType> AccountTypeColumn;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    private Stage primaryStage;
    private ApplicationContext context;
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
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
            loader.setLocation(AdminController.class.getResource("/view/AdminView.fxml"));
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
        usersTable.getItems().clear();
        usersTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        List<User> userList = adminService.getAllUsers();
        usersTable.getItems().addAll(userList);
        UserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        FirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        LastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        AccountTypeColumn.setCellValueFactory(new PropertyValueFactory<>("accountType"));

        deleteButton.disableProperty().bind(Bindings.isEmpty(usersTable.getSelectionModel().getSelectedItems()));
        editButton.disableProperty().bind(Bindings.isEmpty(usersTable.getSelectionModel().getSelectedItems()));
    }

    @FXML
    private void handleDeleteAction(ActionEvent event) {
        User user = usersTable.getSelectionModel().getSelectedItem();
        adminService.deleteUser(user);
        usersTable.getItems().set(usersTable.getSelectionModel().getSelectedIndex(), null);
    }

    @FXML
    private void handleEditAction(ActionEvent event) {
        User user = usersTable.getSelectionModel().getSelectedItem();
        if (user != null) {
            User newUser = showUserEditDialog(user);
            usersTable.getItems().set(usersTable.getSelectionModel().getSelectedIndex(), adminService.findAllUsersByEmail(newUser.getEmail()));
        }
    }

    public User showUserEditDialog(User user) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setControllerFactory(aClass -> context.getBean(aClass));
            loader.setLocation(AdminController.class.getResource("/view/UserEditDialog.fxml"));
            BorderPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit user");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            UserDialogController userDialogController = loader.getController();
            userDialogController.setDialogStage(dialogStage);
            userDialogController.setData(user);

            dialogStage.showAndWait();
            return user;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void handleBackClickAction() {
        MainController mainController = context.getBean(MainController.class);
        mainController.setPrimaryStage(primaryStage);
        mainController.loadView();
    }
}
