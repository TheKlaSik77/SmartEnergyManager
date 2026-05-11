package org.example.smartenergymanager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SmartEnergyManagerApplication extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/smartenergymanager/fxml/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Smart Energy Manager");
        stage.setScene(scene);
        stage.show();
    }
}
