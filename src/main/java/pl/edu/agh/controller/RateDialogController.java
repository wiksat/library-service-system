package pl.edu.agh.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.w3c.dom.Text;
import pl.edu.agh.model.books.Title;
import pl.edu.agh.model.users.AccountType;
import pl.edu.agh.model.users.Admin;
import pl.edu.agh.model.users.Librarian;
import pl.edu.agh.model.users.Member;
import pl.edu.agh.repository.books.TitleRepository;
import pl.edu.agh.service.AdminService;
import pl.edu.agh.service.BookService;
import pl.edu.agh.session.UserSession;

import java.io.IOException;
import java.util.function.UnaryOperator;

@Component
public class RateDialogController {
    @FXML
    private TextField CommentField;
    @FXML
    private TextField RateField;
    private Title title;
    private Stage dialogStage;
    private final UserSession session = UserSession.getInstance();

    private final BookService bookService;

    @Autowired
    public RateDialogController(BookService bookService) {
        this.bookService = bookService;
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    @FXML
    private void initialize() {
        RateField.textProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(newValue instanceof String && !((String)newValue).matches("\\d*")) {
                    RateField.setText(((String)newValue).replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    @FXML
    private void handleOkAction(ActionEvent event) {
        int rate = Integer.parseInt(RateField.getText());
        String comment = CommentField.getText();

        bookService.rateBook(title, session.getUser(), rate, comment);
        dialogStage.close();
    }

    @FXML
    private void handleCancelAction(ActionEvent event) {
        dialogStage.close();
    }
}
