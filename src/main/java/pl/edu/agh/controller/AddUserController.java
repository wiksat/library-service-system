package pl.edu.agh.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pl.edu.agh.service.MemberService;

import java.io.IOException;


@Component
public class AddUserController {

    private Stage primaryStage;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField mailField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label resultLabel;


    private MemberService memberService;
    private ApplicationContext context;

    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    public void loadView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setControllerFactory(aClass -> context.getBean(aClass));
            loader.setLocation(AdminController.class.getResource("/view/AddUserView.fxml"));
            BorderPane mainLayout = loader.load();

            Scene scene = new Scene(mainLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleAddUserAction() {
        resultLabel.setText(null);

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = mailField.getText();
        String password = passwordField.getText();

        String result = this.memberService.addUser(firstName, lastName, email, password);
        showResult(result);
    }

    private void showResult(String labelText) {
        if ("Uzytkownik zostal dodany".equals(labelText)) {
            resultLabel.setStyle("-fx-text-fill: green;");

            // clear fields if succesfully added
            clearInputFields();
        } else {
            resultLabel.setStyle("-fx-text-fill: red;");
        }
        resultLabel.setText(labelText);
    }

    private void clearInputFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        mailField.setText("");
        passwordField.setText("");
    }

    public void handleBackClickAction() {
        LoginController loginController = context.getBean(LoginController.class);
        loginController.setPrimaryStage(primaryStage);
        loginController.loadView();
    }
}
