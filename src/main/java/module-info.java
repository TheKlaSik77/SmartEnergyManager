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
    opens org.example.smartenergymanager.model to javafx.fxml, com.google.gson;

    exports org.example.smartenergymanager.model.utils;
    opens org.example.smartenergymanager.model.utils to javafx.fxml, com.google.gson;

    exports org.example.smartenergymanager.model.batiment;
    opens org.example.smartenergymanager.model.batiment to com.google.gson;
    exports org.example.smartenergymanager.service;
    opens org.example.smartenergymanager.service to com.google.gson, javafx.fxml;

    exports org.example.smartenergymanager.controller;
    opens org.example.smartenergymanager.controller to javafx.fxml;
}