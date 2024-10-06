module com.github.shuntingyardcalculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.github.shuntingyardcalculator.ui to javafx.fxml;
    exports com.github.shuntingyardcalculator.ui;
    opens com.github.shuntingyardcalculator.mainApp;
    exports com.github.shuntingyardcalculator.mainApp;

}