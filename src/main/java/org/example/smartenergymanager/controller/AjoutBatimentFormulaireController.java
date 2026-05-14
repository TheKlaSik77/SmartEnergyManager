package org.example.smartenergymanager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.smartenergymanager.model.batiment.*;
import org.example.smartenergymanager.model.utils.Coordonnees;
import org.example.smartenergymanager.service.BatimentService;

public class AjoutBatimentFormulaireController {

    private Coordonnees coordonneesBatiment;
    private TypeBatiment typeBatiment;
    @FXML Label title;
    @FXML TextField nomField;
    @FXML TextField surfaceField;
    private Batiment batiment;

    private Batiment batimentAModifier = null;

    @FXML
    public void initialize(){
        this.batiment = null;
        this.nomField.setText("");
        this.surfaceField.setText("");
    }

    public void setType(TypeBatiment typeBatiment){
        this.typeBatiment = typeBatiment;
        switch (typeBatiment){
            case MAISON -> this.title.setText("Nouvelle Maison");
            case APPARTEMENT -> this.title.setText("Nouvel Appartement");
            case BUREAU -> this.title.setText("Nouveau Bureau");
            case BATIMENT_UNIVERSITAIRE -> this.title.setText("Nouveau Bâtiment Universitaire");
            case LOCAL_COMMERCIAL -> this.title.setText("Nouveau Local Commercial");
        }
    }

    public void setBatimentAModifier(Batiment batiment) {
        this.batimentAModifier = batiment;
        this.nomField.setText(batiment.getNom());
        this.surfaceField.setText(String.valueOf(batiment.getSurface()));
        this.title.setText("Modifier " + batiment.getNom());
    }

    public void setCoordonneesBatiment(Coordonnees coordonneesBatiment){
        this.coordonneesBatiment = coordonneesBatiment;
    }

    @FXML
    public void onCreer() {
        String nom = nomField.getText();
        int surface = Integer.parseInt(surfaceField.getText());

        if (batimentAModifier != null) {
            Batiment nouveauBatiment = BatimentService.getInstance().creerBatiment(
                    batimentAModifier.getTypeBatiment(), nom, surface, batimentAModifier.getCoordonnees()
            );
            BatimentService.getInstance().modifierBatiment(batimentAModifier, nouveauBatiment);
            this.batiment = nouveauBatiment;
        } else {
            this.batiment = BatimentService.getInstance().creerBatiment(
                    this.typeBatiment, nom, surface, coordonneesBatiment
            );
        }
        ((Stage) nomField.getScene().getWindow()).close();
    }

    @FXML
    public void onAnnuler() {
        ((Stage) nomField.getScene().getWindow()).close();
    }

    public Batiment getBatimentCree(){
        return this.batiment;
    }
}
