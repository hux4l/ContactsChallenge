package sk.tobas.contactschallenge;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sk.tobas.contactschallenge.model.ContactData;

import java.io.IOException;

public class ContactsApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ContactsApplication.class.getResource("contacts.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("My Contacts");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void init() throws Exception {
        ContactData.getInstance().loadContacts();
    }

    @Override
    public void stop() throws Exception {
        try {
            ContactData.getInstance().saveContacts();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}