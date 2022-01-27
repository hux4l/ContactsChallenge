package sk.tobas.contactschallenge;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ContactsController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}