package sk.tobas.contactschallenge.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;

public class ContactData {

    private static final ContactData instance = new ContactData();
    private static final String filename = "Contacts.xml";

    private ObservableList<Contact> contacts;

    // private constructor to make single instance
    private ContactData() {
        this.contacts = FXCollections.observableArrayList();
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
    public void saveContacts() throws IOException, ParserConfigurationException, TransformerException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        // contacts element root element
        Element rootElement = document.createElement("contacts");
        document.appendChild(rootElement);

        for (Contact cont : contacts) {
            System.out.println(cont.getFirstName());
            Element contact = document.createElement("contact");
            rootElement.appendChild(contact);

            // firstname element
            Element fName = document.createElement("firstname");
            fName.appendChild(document.createTextNode(cont.getFirstName()));
            contact.appendChild(fName);
            System.out.println(fName.getTextContent());

            // lastname element
            Element lName = document.createElement("lastname");
            lName.appendChild(document.createTextNode(cont.getLastName()));
            contact.appendChild(lName);

            // firstname element
            Element pNumber = document.createElement("phonenumber");
            pNumber.appendChild(document.createTextNode(cont.getPhoneNumber()));
            contact.appendChild(pNumber);

            // firstname element
            Element note = document.createElement("note");
            note.appendChild(document.createTextNode(cont.getNote()));
            contact.appendChild(note);
        }

        System.out.println(document.getChildNodes());
        // write to xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(filename);
        transformer.transform(source, result);

        // output for test
        StreamResult consoleResult = new StreamResult(System.out);
        transformer.transform(source, consoleResult);

    }

}
