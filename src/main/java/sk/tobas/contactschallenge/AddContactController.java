package sk.tobas.contactschallenge;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sk.tobas.contactschallenge.model.Contact;
import sk.tobas.contactschallenge.model.ContactData;

public class AddContactController {

    @FXML
    private TextField tfFirstName;

    @FXML
    private TextField tfLastName;

    @FXML
    private TextField tfPhoneNumber;

    @FXML
    private TextArea taNotes;

    public Contact createContact() {
        // get values from fields
        String firstName = tfFirstName.getText().trim();
        String lastName = tfLastName.getText().trim();
        String phoneNumber = tfPhoneNumber.getText().trim();
        String note = taNotes.getText().trim();

        // create new contact
        Contact contact = new Contact();
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setPhoneNumber(phoneNumber);
        contact.setNote(note);

        // add contact
        ContactData.getInstance().addContact(contact);

        return contact;
    }
}
