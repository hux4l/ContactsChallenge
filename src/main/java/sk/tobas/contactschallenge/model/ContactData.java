package sk.tobas.contactschallenge.model;

import javafx.collections.ObservableList;

import java.io.IOException;

public class ContactData {

    private static final ContactData instance = new ContactData();
    private static final String filename = "Contacts.xml";

    private ObservableList<Contact> contacts;

    // private constructor to make single instance
    private ContactData() {

    }

    // get instance
    public static ContactData getInstance() {
        return instance;
    }

    // get all contacts
    public ObservableList<Contact> getContacts() {
        return contacts;
    }

    // add new contact
    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    // load contacts
    public void loadContacts() throws IOException {

    }

    // save contacts
    public void saveContats() throws IOException {

    }

}
