package pl.edu.agh.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.model.users.*;
import pl.edu.agh.service.AdminService;

@Component
public class UserDialogController {
    public ComboBox<String> typeAccountField;
    public TextField uidField;
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField emailField;
    public CheckBox bannedCheckBox;
    public CheckBox newsletterCheckBox;
    public TextField phoneNumberField;
    public TextField roomField;
    public VBox memberVBox;
    public VBox adminVBox;
    public VBox adminVBoxLabels;
    private Stage dialogStage;

    private boolean approved;
    private User user;

    private final AdminService adminService;

    @Autowired
    public UserDialogController(AdminService adminService) {
        this.adminService = adminService;
    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setData(User user) {
        this.user = user;
        updateControls();
    }

    private void updateModel() {
        user.setAccountType(AccountType.valueOf(typeAccountField.getValue()));
        user.setFirstName(firstNameField.getText());
        user.setLastName(lastNameField.getText());
        user.setEmail(emailField.getText());
        if (user instanceof Member memberUser) {
            memberUser.setBanned(bannedCheckBox.isSelected());
            memberUser.setNewsLetter(newsletterCheckBox.isSelected());
        }
        if (user instanceof Admin adminUser) {
            adminUser.setRoom(Integer.parseInt(roomField.getText()));
            adminUser.setPhoneNumber(phoneNumberField.getText());
        }
    }

    private void updateControls() {
        typeAccountField.setValue(user.getAccountType().toString());
        uidField.setText(String.valueOf(user.getUserId()));
        firstNameField.setText(user.getFirstName());
        lastNameField.setText(user.getLastName());
        emailField.setText(user.getEmail());
        if (user instanceof Member memberUser) {
            bannedCheckBox.setSelected(memberUser.getBanned());
            newsletterCheckBox.setSelected(memberUser.getNewsLetter());
            memberVBox.setVisible(true);
        }
        if (user instanceof Admin adminUser) {
            roomField.setText(String.valueOf(adminUser.getRoom()));
            phoneNumberField.setText(adminUser.getPhoneNumber());
            adminVBox.setVisible(true);
            adminVBoxLabels.setVisible(true);
        }
    }

    public boolean isApproved() {
        return approved;
    }

    @FXML
    private void handleOkAction(ActionEvent event) {
        updateModel();
        approved = true;
        if (user instanceof Member memberUser && user.getAccountType().equals(AccountType.ADMIN)) {
            this.adminService.changeMemberToAdmin(memberUser);
        } else if (user instanceof Member memberUser && user.getAccountType().equals(AccountType.LIBRARIAN)) {
            this.adminService.changeMemberToLibrarian(memberUser);
        } else if (user instanceof Admin adminUser && user.getAccountType().equals(AccountType.MEMBER)) {
            this.adminService.changeAdminToMember(adminUser);
        } else if (user instanceof Admin adminUser && user.getAccountType().equals(AccountType.LIBRARIAN)) {
            this.adminService.changeAdminToLibrarian(adminUser);
        } else if (user instanceof Librarian librarianUser && user.getAccountType().equals(AccountType.MEMBER)) {
            this.adminService.changeLibrarianToMember(librarianUser);
        } else if (user instanceof Librarian librarianUser && user.getAccountType().equals(AccountType.ADMIN)) {
            this.adminService.changeLibrarianToAdmin(librarianUser);
        } else {
            adminService.updateUser(user);
        }
        dialogStage.close();
    }

    @FXML
    private void handleCancelAction(ActionEvent event) {
        dialogStage.close();
    }
}
