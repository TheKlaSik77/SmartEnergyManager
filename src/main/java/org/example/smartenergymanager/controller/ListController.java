package org.example.smartenergymanager.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.smartenergymanager.model.batiment.Batiment;
import org.example.smartenergymanager.service.BatimentService;

public class ListController {

    @FXML
    private TableView<Batiment> tableBatiments;
    @FXML
    private TableColumn<Batiment, String> colTypeBatiment;
    @FXML
    private TableColumn<Batiment, String> colNom;
    @FXML
    private TableColumn<Batiment, Integer> colSurface;
    @FXML
    private TableColumn<Batiment, Integer> colNbReleves;

    @FXML
    public void initialize() {
        tableBatiments.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        // TODO: Gérer pour que ce soit dynamique par rapport au listener

        tableBatiments.setPlaceholder(new Label("Aucun bâtiment ajouté"));

        colTypeBatiment.setCellValueFactory(data -> {
            String typePropre = "";
            switch (data.getValue().getTypeBatiment()){
                case MAISON -> typePropre = "Maison";
                case APPARTEMENT -> typePropre = "Appartement";
                case BUREAU -> typePropre = "Bureau";
                case BATIMENT_UNIVERSITAIRE -> typePropre = "Bâtiment Universitaire";
                case LOCAL_COMMERCIAL -> typePropre = "Local Commercial";
            }

            return new SimpleStringProperty(typePropre);
        });
        colNom.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getNom())
        );
        colSurface.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getSurface()).asObject()
        );
        colNbReleves.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getListeReleves().size()).asObject()
        );

        tableBatiments.setItems(BatimentService.getInstance().getBatiments());
    }
}

