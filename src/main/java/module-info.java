module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.desktop;
    requires com.google.gson;
    requires java.sql;

    opens com.example.demo to javafx.fxml,com.google.gson;
    exports com.example.demo;
}