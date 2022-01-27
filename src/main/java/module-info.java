module sk.tobas.contactschallenge {
    requires javafx.controls;
    requires javafx.fxml;


    opens sk.tobas.contactschallenge to javafx.fxml;
    exports sk.tobas.contactschallenge;
}