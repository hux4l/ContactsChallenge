package sk.tobas.contactschallenge;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import sk.tobas.contactschallenge.model.Contact;
import sk.tobas.contactschallenge.model.ContactData;

public class ContactsController {

    @FXML
    private TableView<Contact> twContacts;

    public void listContacts() {
        Task<ObservableList<Contact>> task = new GetAllContactsTask();
        twContacts.itemsProperty().bind(task.valueProperty());

        new Thread(task).start();
    }
}

// get contacts in separate task
class  GetAllContactsTask extends Task {

    @Override
    protected ObservableList<Contact> call() {
        System.out.println("im here");
        // for data binding
        return FXCollections.observableArrayList(ContactData.getInstance().getContacts());
    }
}