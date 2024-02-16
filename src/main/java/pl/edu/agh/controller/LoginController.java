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
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edu.agh.enums.UserRoleEnum;
import pl.edu.agh.model.users.Member;
import pl.edu.agh.model.users.User;
import pl.edu.agh.service.*;
import pl.edu.agh.session.UserSession;
import pl.edu.agh.stage.StageReadyEvent;

import java.io.IOException;

@Component
public class LoginController implements ApplicationListener<StageReadyEvent> {

    private Stage primaryStage;

    @FXML
    private TextField mailField;

    @FXML
    private TextField passwordField;
    @FXML
    private Label resultLabel;
    private MemberService memberService;
    private AdminService adminService;
    private LibrarianService librarianService;
    private ApplicationContext context;


    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }
    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }
    @Autowired
    public void setLibrarianService(LibrarianService librarianService) {
        this.librarianService = librarianService;
    }

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initRootLayout() {
        this.primaryStage.setTitle("Library");
        loadView();
    }
    public void loadView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setControllerFactory(aClass -> context.getBean(aClass));
            loader.setLocation(LoginController.class.getResource("/view/LoginView.fxml"));
            BorderPane mainLayout = loader.load();

            Scene scene = new Scene(mainLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleLoginClickAction() {

        String mail = mailField.getText();
        String givenPassword = passwordField.getText();
        User user = new Member();
        UserRoleEnum role = UserRoleEnum.NOT_LOGGED;
        if (memberService.findByEmail(mail) != null) {
            user = memberService.findByEmail(mail);
            role = UserRoleEnum.MEMBER;
        } else if (adminService.findByEmail(mail) != null) {
            user = adminService.findByEmail(mail);
            role = UserRoleEnum.ADMIN;
        } else if (librarianService.findByEmail(mail) != null) {
            user = librarianService.findByEmail(mail);
            role = UserRoleEnum.LIBRARIAN;
        }
        if (role == UserRoleEnum.NOT_LOGGED) {
            resultLabel.setText("Bledny e-mail lub haslo");
            return;
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(givenPassword, user.getPassword())) {
            resultLabel.setText("Bledny e-mail lub haslo");
            return;
        }

        UserSession session = UserSession.getInstance();
        session.setUser(user);
        session.setRole(role);

        MainController mainController = context.getBean(MainController.class);
        mainController.setPrimaryStage(primaryStage);
        mainController.loadView();
    }

    public void handleAddUserClickAction() {
        AddUserController addUserController = context.getBean(AddUserController.class);
        addUserController.setPrimaryStage(primaryStage);
        addUserController.loadView();
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        this.primaryStage = event.getStage();
        initRootLayout();
    }
}
