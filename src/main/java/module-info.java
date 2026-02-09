module buruiana.calcfin {
    requires javafx.controls;
    requires javafx.fxml;


    opens buruiana.calcfin to javafx.fxml;
    exports buruiana.calcfin;
}