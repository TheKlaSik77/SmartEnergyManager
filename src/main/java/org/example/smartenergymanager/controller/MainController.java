package org.example.smartenergymanager.controller;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.smartenergymanager.model.batiment.Batiment;
import org.example.smartenergymanager.service.BatimentService;
import org.example.smartenergymanager.service.SimulationService;

import java.io.IOException;

public class MainController {

    @FXML private Label labelDate;
    @FXML private Button btnJourSuivant;
    @FXML private Button btnGenererMois;

    @FXML private CarteController carteController;
    @FXML private ListeController listeController;
    @FXML private DashboardController dashboardController;

    @FXML
    public void initialize() {
        System.out.println("Main page chargée");
        BatimentService.getInstance().getBatiments().addListener(
                (ListChangeListener<Batiment>) change -> {
                    rafraichir();
                }
        );
    }

    public void onJourSuivant(ActionEvent actionEvent) {
        SimulationService.getInstance().avancerUnJour();
        listeController.rafraichir();
        dashboardController.rafraichir();
        labelDate.setText(SimulationService.getInstance().getDateActuelle().toString());
    }

    public void onGenererMois(ActionEvent actionEvent) {
        SimulationService.getInstance().avancerUnMois();
        listeController.rafraichir();
        dashboardController.rafraichir();
        labelDate.setText(SimulationService.getInstance().getDateActuelle().toString());
    }

    public void rafraichir(){
        carteController.rafraichirCarte();
    }

    @FXML
    private void ouvrirSauvegardeFormulaire() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/org/example/smartenergymanager/fxml/sauvegarder-fichier-formulaire.fxml"
            ));
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setScene(new Scene(loader.load()));

            SauvegarderFichierFormulaireController controller = loader.getController();
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
