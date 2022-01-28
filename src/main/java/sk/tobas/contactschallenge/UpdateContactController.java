package sk.tobas.contactschallenge;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sk.tobas.contactschallenge.model.Contact;

public class UpdateContactController {

    @FXML
    private TextField tfFirstName;

    @FXML
    private TextField tfLastName;

    @FXML
    private TextField tfPhoneNumber;

    @FXML
    private TextArea taNotes;

    public void updateDialogFields(Contact contact) {
        // set values from selected contact
        tfFirstName.setText(contact.getFirstName());
        tfLastName.setText(contact.getLastName());
        tfPhoneNumber.setText(contact.getPhoneNumber());
        taNotes.setText(contact.getNote());
    }

    // update contact
    public Contact updateContact(Contact contact) {

        // get values from fields
        contact.setFirstName(tfFirstName.getText().trim());
        contact.setLastName(tfLastName.getText().trim());
        contact.setPhoneNumber(tfPhoneNumber.getText().trim());
        contact.setNote(taNotes.getText().trim());

        return contact;
    }
}
