package sk.tobas.contactschallenge;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import sk.tobas.contactschallenge.model.Contact;
import sk.tobas.contactschallenge.model.ContactData;

public class ContactsController {

    @FXML
    private TableView<Contact> twContacts;

    public void listContacts() {
        GetAllContactsTask task = new GetAllContactsTask();
        twContacts.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
    }

    @FXML
    public void handleAdd(ActionEvent actionEvent) {
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