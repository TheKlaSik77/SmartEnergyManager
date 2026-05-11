package org.example.smartenergymanager.controller;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.smartenergymanager.model.batiment.*;
import org.example.smartenergymanager.model.utils.Coordonnees;
import org.example.smartenergymanager.service.BatimentService;
import org.example.smartenergymanager.service.SimulationService;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CarteController {

    @FXML private Pane cartePane;
    @FXML private ImageView carteImage;
    @FXML private BarreOutilsController barreOutilsController;
    @FXML private ToggleGroup toggleGroup;

    private final Map<Batiment, ImageView> icones = new HashMap<>();
    private static final int TAILLE_ICONE = 32;

    @FXML
    public void initialize() {
        initCarte();
        this.toggleGroup = barreOutilsController.getGroupeBoutonsBarreOutils();

        BatimentService.getInstance().getBatiments().addListener(
                (ListChangeListener<Batiment>) change -> {
                    while (change.next()) {
                        if (change.wasAdded()) {
                            change.getAddedSubList().forEach(this::poserIconeBatiment);
                        }
                        if (change.wasRemoved()) {
                            change.getRemoved().forEach(this::retirerIconeBatiment);
                        }
                        if (change.wasReplaced()) {
                            rafraichirCarte();
                        }
                    }
                }
        );
    }

    private void initCarte() {
        Image carteParis = new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/org/example/smartenergymanager/images/carte-paris.png")
        ));
        carteImage.setImage(carteParis);
    }

    @FXML
    public void poserBatiment(MouseEvent event) {
        if (event.getButton() != MouseButton.PRIMARY) return;

        Toggle actif = this.toggleGroup.getSelectedToggle();
        Coordonnees clic = new Coordonnees(event.getX(), event.getY());

        if (actif == barreOutilsController.getGommeBtn()) {
            Batiment batiment = BatimentService.getInstance().getBatimentLePlusProche(clic, TAILLE_ICONE);
            if (batiment != null) {
                BatimentService.getInstance().supprimerBatiment(batiment);
            }
        } else {
            TypeBatiment type = getTypeBatimentActif(actif);
            if (type == null) return;

            Batiment batiment = ouvrirFormulaireAjout(type, clic);
            if (batiment != null) {
                BatimentService.getInstance().ajouterBatiment(batiment);
            }
        }
    }

    private void poserIconeBatiment(Batiment batiment) {
        ImageView icone = getIconePourType(batiment.getTypeBatiment());
        icone.setFitWidth(TAILLE_ICONE);
        icone.setFitHeight(TAILLE_ICONE);
        icone.setLayoutX(batiment.getCoordonnees().getX() - TAILLE_ICONE / 2.0);
        icone.setLayoutY(batiment.getCoordonnees().getY() - TAILLE_ICONE / 2.0);
        icones.put(batiment, icone);
        cartePane.getChildren().add(icone);
    }

    private void retirerIconeBatiment(Batiment batiment) {
        ImageView icone = icones.remove(batiment);
        if (icone != null) cartePane.getChildren().remove(icone);
    }

    public void rafraichirCarte() {
        cartePane.getChildren().removeAll(icones.values());
        icones.clear();
        for (Batiment batiment : BatimentService.getInstance().getBatiments()) {
            poserIconeBatiment(batiment);
        }
    }

    private TypeBatiment getTypeBatimentActif(Toggle actif) {
        if (actif == barreOutilsController.getMaisonBtn()) return TypeBatiment.MAISON;
        if (actif == barreOutilsController.getAppartementBtn()) return TypeBatiment.APPARTEMENT;
        if (actif == barreOutilsController.getBureauBtn()) return TypeBatiment.BUREAU;
        if (actif == barreOutilsController.getBatimentUniversitaireBtn()) return TypeBatiment.BATIMENT_UNIVERSITAIRE;
        if (actif == barreOutilsController.getLocalCommercialBtn()) return TypeBatiment.LOCAL_COMMERCIAL;
        return null;
    }

    private Batiment ouvrirFormulaireAjout(TypeBatiment type, Coordonnees clic) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/org/example/smartenergymanager/fxml/carte/ajout-batiment-formulaire.fxml"
            ));
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setScene(new Scene(loader.load()));

            AjoutBatimentFormulaireController controller = loader.getController();
            controller.setType(type);
            controller.setCoordonneesBatiment(clic);

            dialog.showAndWait();
            return controller.getBatimentCree();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ImageView getIconePourType(TypeBatiment type) {
        String chemin = switch (type) {
            case MAISON -> "/org/example/smartenergymanager/icones/house-color.png";
            case APPARTEMENT -> "/org/example/smartenergymanager/icones/appartment-color.png";
            case BUREAU -> "/org/example/smartenergymanager/icones/office.png";
            case BATIMENT_UNIVERSITAIRE -> "/org/example/smartenergymanager/icones/school-color.png";
            case LOCAL_COMMERCIAL -> "/org/example/smartenergymanager/icones/shop-color.png";
        };
        return new ImageView(new Image(Objects.requireNonNull(
                getClass().getResourceAsStream(chemin)
        )));
    }
}