package org.example.smartenergymanager.controller;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import org.example.smartenergymanager.model.batiment.Batiment;
import org.example.smartenergymanager.service.BatimentService;
import org.example.smartenergymanager.service.JsonPersistanceService;

public class ChargerFichierController {

    @FXML private ComboBox<String> listeFichiers;

    @FXML
    public void initialize(){
        listeFichiers.setItems(JsonPersistanceService.getInstance().getFichiersSauvegarde());
        listeFichiers.setPromptText("Sélectionner...");
    }


    @FXML
    public void charger(){
        BatimentService.getInstance().clearBatiments();
        String fichierSelectionne =  this.listeFichiers.getValue();
        if (fichierSelectionne != null){
            BatimentService.getInstance().charger(fichierSelectionne);
        }
    }
}
