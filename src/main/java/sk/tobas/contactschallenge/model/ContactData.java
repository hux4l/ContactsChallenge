package sk.tobas.contactschallenge.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;

public class ContactData {

    private static final ContactData instance = new ContactData();
    private static final String filename = "Contacts.xml";

    private final ObservableList<Contact> contacts;

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
    public void loadContacts() throws IOException, SAXException, ParserConfigurationException {
        // get xml file
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(filename);
        document.getDocumentElement().normalize();

        // get nodes wit contact tag name
        NodeList nodeList = document.getElementsByTagName("contact");

        // create contact from each contact node and store to ObservableList
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            NodeList chNodes = node.getChildNodes();
            Contact contact = new Contact();
            contact.setFirstName(chNodes.item(0).getTextContent());
            contact.setLastName(chNodes.item(1).getTextContent());
            contact.setPhoneNumber(chNodes.item(2).getTextContent());
            contact.setNote(chNodes.item(3).getTextContent());
            System.out.println(contact.getFirstName());
            contacts.add(contact);
        }
    }

    // save contacts
    public void saveContacts() throws IOException, ParserConfigurationException, TransformerException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        // contacts element root element
        Element rootElement = document.createElement("contacts");
        document.appendChild(rootElement);

        // loop contact and create node for each contact
        for (Contact cont : contacts) {
            Element contact = document.createElement("contact");
            rootElement.appendChild(contact);

            // firstname element
            Element fName = document.createElement("firstname");
            fName.appendChild(document.createTextNode(cont.getFirstName()));
            contact.appendChild(fName);

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
