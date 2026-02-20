module buruiana.calcfin {
    requires javafx.controls;
    requires javafx.fxml;


    opens buruiana.calcfin to javafx.fxml;
    exports buruiana.calcfin;
    exports buruiana.calcfin.controller;
    opens buruiana.calcfin.controller to javafx.fxml;
}