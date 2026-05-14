package org.example.smartenergymanager.controller;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.smartenergymanager.model.batiment.Batiment;
import org.example.smartenergymanager.model.batiment.TypeBatiment;
import org.example.smartenergymanager.model.utils.TypeEnergie;
import org.example.smartenergymanager.service.BatimentService;
import org.example.smartenergymanager.service.DashboardService;

public class DashboardController {

    @FXML
    private Label nbBatiment;
    @FXML
    private Label nbMaison;
    @FXML
    private Label nbAppartement;
    @FXML
    private Label nbBureau;
    @FXML
    private Label nbBatimentUniversitaire;
    @FXML
    private Label nbLocalCommercial;

    @FXML
    private Label nbReleves;
    @FXML
    private Label nbRelevesElectricite;
    @FXML
    private Label nbRelevesEau;
    @FXML
    private Label nbRelevesGaz;
    @FXML
    private Label nbRelevesChauffage;
    @FXML
    private Label nbRelevesClimatisation;

    @FXML
    public void initialize() {
        BatimentService.getInstance().getBatiments().addListener(
                (ListChangeListener<Batiment>) change -> rafraichir()
        );
        rafraichir();
    }

    public void rafraichir(){
        // Batiments
        nbBatiment.setText(String.valueOf(DashboardService.getNbBatiments()));
        nbMaison.setText(String.valueOf(DashboardService.getNbBatimentParTypeBatiment(TypeBatiment.MAISON)));
        nbAppartement.setText(String.valueOf(DashboardService.getNbBatimentParTypeBatiment(TypeBatiment.APPARTEMENT)));
        nbBureau.setText(String.valueOf(DashboardService.getNbBatimentParTypeBatiment(TypeBatiment.BUREAU)));
        nbBatimentUniversitaire.setText(String.valueOf(DashboardService.getNbBatimentParTypeBatiment(TypeBatiment.BATIMENT_UNIVERSITAIRE)));
        nbLocalCommercial.setText(String.valueOf(DashboardService.getNbBatimentParTypeBatiment(TypeBatiment.LOCAL_COMMERCIAL)));

        // Relevés
        nbReleves.setText(String.valueOf(DashboardService.getNbReleves()));
        nbRelevesElectricite.setText(String.valueOf(DashboardService.getNbRelevesParTypeEnergie(TypeEnergie.ELECTRICITE)));
        nbRelevesEau.setText(String.valueOf(DashboardService.getNbRelevesParTypeEnergie(TypeEnergie.EAU)));
        nbRelevesGaz.setText(String.valueOf(DashboardService.getNbRelevesParTypeEnergie(TypeEnergie.GAZ)));
        nbRelevesChauffage.setText(String.valueOf(DashboardService.getNbRelevesParTypeEnergie(TypeEnergie.CHAUFFAGE)));
        nbRelevesClimatisation.setText(String.valueOf(DashboardService.getNbRelevesParTypeEnergie(TypeEnergie.CLIMATISATION)));

    }
}
