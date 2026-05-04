module org.example.smartenergymanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires com.google.gson;
    requires gson.extras;

    opens org.example.smartenergymanager to javafx.fxml;
    exports org.example.smartenergymanager;
    exports org.example.smartenergymanager.model;
    opens org.example.smartenergymanager.model to javafx.fxml;
    exports org.example.smartenergymanager.model.utils;
    opens org.example.smartenergymanager.model.utils to javafx.fxml;
}