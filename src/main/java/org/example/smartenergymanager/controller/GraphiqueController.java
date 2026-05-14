package org.example.smartenergymanager.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.smartenergymanager.model.batiment.Batiment;
import org.example.smartenergymanager.model.utils.TypeEnergie;
import org.example.smartenergymanager.service.DashboardService;

import java.util.Map;

public class GraphiqueController {

    @FXML public Label nomBatiment;
    @FXML public ChoiceBox<String> choixPeriode;
    @FXML public ChoiceBox<String> choixEnergie;
    @FXML public VBox zoneGraphiques;

    private Batiment batiment;

    @FXML
    public void initialize() {
        choixPeriode.getItems().addAll("30 derniers jours", "12 derniers mois", "Toutes les années");
        choixPeriode.setValue("30 derniers jours");

        choixEnergie.getItems().add("Tout");
        for (TypeEnergie t : TypeEnergie.values()) {
            choixEnergie.getItems().add(t.name());
        }
        choixEnergie.setValue("Tout");

        choixPeriode.getSelectionModel().selectedItemProperty().addListener(
                (obs, old, nouveau) -> rafraichir()
        );
        choixEnergie.getSelectionModel().selectedItemProperty().addListener(
                (obs, old, nouveau) -> rafraichir()
        );
    }

    public void setBatiment(Batiment batiment) {
        this.batiment = batiment;
        this.nomBatiment.setText(batiment.getNom());
        rafraichir();
    }

    public void rafraichir() {
        if (batiment == null) return;
        zoneGraphiques.getChildren().clear();

        String periode = choixPeriode.getValue();
        String energie = choixEnergie.getValue();

        if (energie.equals("Tout")) {
            zoneGraphiques.getChildren().add(creerGraphiquePrix(periode, null));
        } else {
            TypeEnergie typeEnergie = TypeEnergie.valueOf(energie);
            zoneGraphiques.getChildren().add(creerGraphiqueQuantite(periode, typeEnergie));
            zoneGraphiques.getChildren().add(creerGraphiquePrix(periode, typeEnergie));
        }
    }

    private LineChart<String, Number> creerGraphiquePrix(String periode, TypeEnergie typeEnergie) {
        CategoryAxis axeX = new CategoryAxis();
        NumberAxis axeY = new NumberAxis();
        axeX.setLabel("Date");
        axeY.setLabel("Prix (€)");

        LineChart<String, Number> chart = new LineChart<>(axeX, axeY);
        chart.setTitle(typeEnergie == null ? "Coût total" : "Coût - " + typeEnergie.name());
        chart.setPrefHeight(200);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Prix (€)");

        Map<?, Double> data = obtenirDonneesPrix(periode, typeEnergie);
        for (Map.Entry<?, Double> entry : data.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey().toString(), entry.getValue()));
        }

        chart.getData().add(series);
        return chart;
    }

    private LineChart<String, Number> creerGraphiqueQuantite(String periode, TypeEnergie typeEnergie) {
        CategoryAxis axeX = new CategoryAxis();
        NumberAxis axeY = new NumberAxis();
        axeX.setLabel("Date");
        axeY.setLabel("Quantité (" + typeEnergie.getUnite() + ")");

        LineChart<String, Number> chart = new LineChart<>(axeX, axeY);
        chart.setTitle("Consommation - " + typeEnergie.name());
        chart.setPrefHeight(200);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(typeEnergie.getUnite());

        Map<?, Double> data = obtenirDonneesQuantite(periode, typeEnergie);
        for (Map.Entry<?, Double> entry : data.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey().toString(), entry.getValue()));
        }

        chart.getData().add(series);
        return chart;
    }

    private Map<?, Double> obtenirDonneesPrix(String periode, TypeEnergie typeEnergie) {
        return switch (periode) {
            case "30 derniers jours" -> typeEnergie == null
                    ? DashboardService.getCoutParJour(batiment, 30)
                    : DashboardService.getCoutParJourParTypeEnergie(batiment, 30, typeEnergie);
            case "12 derniers mois" -> typeEnergie == null
                    ? DashboardService.getCoutParMois(batiment, 12)
                    : DashboardService.getCoutParMoisParTypeEnergie(batiment, 12, typeEnergie);
            default -> typeEnergie == null
                    ? DashboardService.getCoutParAnnee(batiment)
                    : DashboardService.getCoutParAnneeParTypeEnergie(batiment, typeEnergie);
        };
    }

    private Map<?, Double> obtenirDonneesQuantite(String periode, TypeEnergie typeEnergie) {
        return switch (periode) {
            case "30 derniers jours" -> DashboardService.getQuantiteParJourParTypeEnergie(batiment, 30, typeEnergie);
            case "12 derniers mois" -> DashboardService.getQuantiteParMoisParTypeEnergie(batiment, 12, typeEnergie);
            default -> DashboardService.getQuantiteParAnneeParTypeEnergie(batiment, typeEnergie);
        };
    }

    @FXML
    public void fermer() {
        ((Stage) zoneGraphiques.getScene().getWindow()).close();
    }
}