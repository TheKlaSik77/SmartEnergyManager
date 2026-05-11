package org.example.smartenergymanager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.Objects;

public class BarreOutilsController {

    @FXML private ImageView gommeIcone;
    @FXML private ImageView maisonIcone;
    @FXML private ImageView appartementIcone;
    @FXML private ImageView bureauIcone;
    @FXML private ImageView batimentUniversitaireIcone;
    @FXML private ImageView localCommercialIcone;

    @FXML private ToggleGroup groupeBoutonsBarreOutils;
    @FXML private ToggleButton gommeBtn;
    @FXML private ToggleButton maisonBtn;
    @FXML private ToggleButton appartementBtn;
    @FXML private ToggleButton bureauBtn;
    @FXML private ToggleButton batimentUniversitaireBtn;
    @FXML private ToggleButton localCommercialBtn;



    @FXML
    public void initialize() {
        initToggleButtons();
        initImages();
        setTooltips();
    }

    public ToggleGroup getGroupeBoutonsBarreOutils() {
        return groupeBoutonsBarreOutils;
    }

    public ToggleButton getGommeBtn() {
        return gommeBtn;
    }

    public ToggleButton getMaisonBtn() {
        return maisonBtn;
    }

    public ToggleButton getAppartementBtn() {
        return appartementBtn;
    }

    public ToggleButton getBureauBtn() {
        return bureauBtn;
    }

    public ToggleButton getBatimentUniversitaireBtn() {
        return batimentUniversitaireBtn;
    }

    public ToggleButton getLocalCommercialBtn() {
        return localCommercialBtn;
    }

    private void initToggleButtons(){
        // Création de la barre d'outils avec tous les boutons groupés
        this.groupeBoutonsBarreOutils = new ToggleGroup();
        gommeBtn.setToggleGroup(groupeBoutonsBarreOutils);
        maisonBtn.setToggleGroup(groupeBoutonsBarreOutils);
        appartementBtn.setToggleGroup(groupeBoutonsBarreOutils);
        bureauBtn.setToggleGroup(groupeBoutonsBarreOutils);
        batimentUniversitaireBtn.setToggleGroup(groupeBoutonsBarreOutils);
        localCommercialBtn.setToggleGroup(groupeBoutonsBarreOutils);
    }

    private void setTooltips() {
        setTooltip(maisonBtn, "Maison");
        setTooltip(appartementBtn, "Appartement");
        setTooltip(bureauBtn, "Bureau");
        setTooltip(batimentUniversitaireBtn, "Bâtiment Universitaire");
        setTooltip(localCommercialBtn, "Local Commercial");
        setTooltip(gommeBtn, "Gomme");
    }

    private void setTooltip(ToggleButton btn, String texte) {
        Tooltip t = new Tooltip(texte);
        t.setShowDelay(Duration.millis(100));
        btn.setTooltip(t);
    }

    private void initImages(){

        // Image des icônes
        Image gommeImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                "/org/example/smartenergymanager/icones/eraser.png"
        )));
        gommeIcone.setImage(gommeImg);

        Image maisonImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                "/org/example/smartenergymanager/icones/house.png"
        )));
        maisonIcone.setImage(maisonImg);

        Image appartementImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                "/org/example/smartenergymanager/icones/appartment.png"
        )));
        appartementIcone.setImage(appartementImg);

        Image bureauImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                "/org/example/smartenergymanager/icones/office.png"
        )));
        bureauIcone.setImage(bureauImg);

        Image batimentUniversitaireImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                "/org/example/smartenergymanager/icones/school.png"
        )));
        batimentUniversitaireIcone.setImage(batimentUniversitaireImg);

        Image localCommercialImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                "/org/example/smartenergymanager/icones/shop.png"
        )));
        localCommercialIcone.setImage(localCommercialImg);
    }
}
