package sk.tobas.contactschallenge;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import sk.tobas.contactschallenge.model.Contact;
import sk.tobas.contactschallenge.model.ContactData;

import java.io.IOException;
import java.util.Optional;

public class ContactsController {

    @FXML
    private BorderPane bpMain;

    @FXML
    private TableView<Contact> twContacts;

    public void listContacts() {
        GetAllContactsTask task = new GetAllContactsTask();
        twContacts.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
    }

    // show dialog when add contact is pressed
    @FXML
    public void handleAdd() {
        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.initOwner(bpMain.getScene().getWindow());
        dialog.setTitle("Add new Contact");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addContact.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            AddContactController controller = fxmlLoader.getController();
            Contact contact = controller.createContact();
            twContacts.getSelectionModel().select(contact);
        }

    }

    @FXML
    public void handleEdit(ActionEvent actionEvent) {
    }

    @FXML
    public void handleDelete(ActionEvent actionEvent) {
    }
}

// get contacts in separate task
class  GetAllContactsTask extends Task {

    @Override
    protected ObservableList<Contact> call() {
        // for data binding
        return FXCollections.observableArrayList(ContactData.getInstance().getContacts());
    }
}