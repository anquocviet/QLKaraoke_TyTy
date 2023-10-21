module iuh_ptudnhom16.qlkaraoke_tyty {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens main to javafx.fxml;
    opens controllers to javafx.fxml;
    exports main;
    exports controllers;
}