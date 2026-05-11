package org.example.smartenergymanager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.example.smartenergymanager.service.JsonPersistanceService;

public class SauvegarderFichierFormulaireController {

    @FXML private ComboBox<String> listeFichiers;

    @FXML
    public void initialize(){
        listeFichiers.getItems().addAll(JsonPersistanceService.getInstance().getFichiersSauvegarde());
    }

    @FXML
    public void onAnnuler(){
        ((Stage) listeFichiers.getScene().getWindow()).close();
    }
    @FXML
    public void onSauvegardeSurFichier(){
        String fichierSelectionne = listeFichiers.getValue();
        if (fichierSelectionne != null){
            JsonPersistanceService.getInstance().sauvegarder(fichierSelectionne);
        }
        ((Stage) listeFichiers.getScene().getWindow()).close();
    }
}
