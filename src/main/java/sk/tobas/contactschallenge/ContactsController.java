package sk.tobas.contactschallenge;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
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

    @FXML
    private ContextMenu contactContextMenu;

    public void initialize() {

        // context menu
        contactContextMenu = new ContextMenu();
        MenuItem editMenuItem = new MenuItem("Edit");
        editMenuItem.setOnAction(actionEvent -> {
            Contact contact = twContacts.getSelectionModel().getSelectedItem();
            if (contact != null) {
                handleEdit();
            }
        });

        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(actionEvent -> {
            Contact contact = twContacts.getSelectionModel().getSelectedItem();
            if (contact != null) {
                handleDelete();
            }
        });
        contactContextMenu.getItems().addAll(editMenuItem, deleteMenuItem);
        twContacts.setContextMenu(contactContextMenu);

    }

    public void listContacts() {
        GetAllContactsTask task = new GetAllContactsTask();
        twContacts.itemsProperty().bind(task.valueProperty());
        twContacts.getSelectionModel().selectFirst();
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
            listContacts();
            twContacts.getSelectionModel().select(contact);
        }
    }

    @FXML
    public void handleEdit() {
        // get selected contact
        Contact contact = twContacts.getSelectionModel().getSelectedItem();

        // create dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(bpMain.getScene().getWindow());
        dialog.setTitle("Update contact: " + contact.getFirstName() + " " + contact.getLastName());

        // load fxml file
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("updateContact.fxml"));

        // if found load dialog
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // update dialog fields based on selected contact
        UpdateContactController controller = fxmlLoader.getController();
        controller.updateDialogFields(contact);

        // add buttons to dialog
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        // if ok pressed update contact data
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            contact = controller.updateContact(contact);
            listContacts();
            twContacts.getSelectionModel().select(contact);
        }
    }

    // delete contact
    @FXML
    public void handleDelete() {
        // create alert window
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Contact selected = twContacts.getSelectionModel().getSelectedItem();
        alert.setTitle("Deleting contact");
        alert.setHeaderText("Are you sure you want to delete " + selected.getFirstName() + " " + selected.getLastName() + " contact?");
        Optional<ButtonType> result = alert.showAndWait();

        // delete selected contact if ok pressed
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ContactData.getInstance().deleteContact(selected);
            listContacts();
            twContacts.getSelectionModel().selectFirst();
        }
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