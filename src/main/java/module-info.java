module org.example.smartenergymanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;

    opens org.example.smartenergymanager to javafx.fxml;
    exports org.example.smartenergymanager;
}