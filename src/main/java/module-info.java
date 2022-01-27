module sk.tobas.contactschallenge {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens sk.tobas.contactschallenge to javafx.fxml;
    exports sk.tobas.contactschallenge;
}