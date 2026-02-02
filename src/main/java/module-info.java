module _4ain.calcolatrice1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens _4ain.calcolatrice1 to javafx.fxml;
    exports _4ain.calcolatrice1;
    exports controller;
    opens controller to javafx.fxml;
}