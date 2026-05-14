package org.example.smartenergymanager.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.smartenergymanager.model.batiment.Batiment;
import org.example.smartenergymanager.service.BatimentService;

import java.io.IOException;

public class ListeController {

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
    private TableColumn<Batiment, Void> colActions;

    @FXML
    public void initialize() {
        tableBatiments.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
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

        colActions.setCellFactory(col -> new TableCell<>() {
            private final Button btnModifier = new Button("Modifier Infos");
            private final Button btnVoir = new Button("Voir Consommation");
            private final HBox hbox = new HBox(5, btnVoir, btnModifier);
            {
                hbox.setAlignment(javafx.geometry.Pos.CENTER);

                btnVoir.setOnAction(e -> {
                    Batiment batiment = getTableView().getItems().get(getIndex());
                    onBatimentSelectionne(batiment);
                });

                btnModifier.setOnAction(e -> {
                    Batiment batiment = getTableView().getItems().get(getIndex());
                    ouvrirFormulaireModification(batiment);
                });

            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setAlignment(javafx.geometry.Pos.CENTER);
                    setGraphic(hbox);
                }
            }
        });

        tableBatiments.setItems(BatimentService.getInstance().getBatiments());

        tableBatiments.getSelectionModel().selectedItemProperty().addListener(
                (observable, ancienBatiment, nouveauBatiment) -> {
                    if (nouveauBatiment != null) {
                        onBatimentSelectionne(nouveauBatiment);
                    }
                }
        );
    }

    public void rafraichir(){
        tableBatiments.refresh();
    }

    public void onBatimentSelectionne(Batiment batiment){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/org/example/smartenergymanager/fxml/graphique.fxml"
            ));
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setScene(new Scene(loader.load()));

            GraphiqueController controller = loader.getController();
            controller.setBatiment(batiment);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ouvrirFormulaireModification(Batiment batiment) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/org/example/smartenergymanager/fxml/carte/ajout-batiment-formulaire.fxml"
            ));
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setScene(new Scene(loader.load()));

            AjoutBatimentFormulaireController controller = loader.getController();
            controller.setType(batiment.getTypeBatiment());
            controller.setBatimentAModifier(batiment);

            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

