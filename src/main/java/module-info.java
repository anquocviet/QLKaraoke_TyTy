module iuh_ptudnhom16.qlkaraoke_tyty {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires javafx.base;
    requires javafx.graphics;
    requires java.desktop;
    requires java.logging;
    requires java.sql;

    opens main to javafx.fxml;
    opens controllers to javafx.fxml;
    opens model to javafx.base;
    exports main;
    exports controllers;
    exports model;
}