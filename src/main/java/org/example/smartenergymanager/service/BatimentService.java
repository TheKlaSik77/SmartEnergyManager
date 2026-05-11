package org.example.smartenergymanager.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.smartenergymanager.model.batiment.*;
import org.example.smartenergymanager.model.utils.Coordonnees;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BatimentService {
    private ObservableList<Batiment> listeBatiments;
    private JsonPersistanceService jsonPersistanceService;

    private static BatimentService instance = null;

    public BatimentService(){
        this.listeBatiments = FXCollections.observableArrayList();
        this.jsonPersistanceService = JsonPersistanceService.getInstance();
    }

    public static BatimentService getInstance(){
        if (instance == null){
            instance = new BatimentService();
        }
        return instance;
    }
    public ObservableList<Batiment> getBatiments(){
        return this.listeBatiments;
    }

    public Batiment creerBatiment(TypeBatiment typeBatiment, String nom, int surface, Coordonnees coordonneesBatiment){
        switch (typeBatiment){
            case MAISON :
                return new Maison(nom, surface,coordonneesBatiment);
            case APPARTEMENT:
                return new Appartement(nom, surface, coordonneesBatiment);
            case BUREAU:
                return new Bureau(nom, surface,coordonneesBatiment);
            case BATIMENT_UNIVERSITAIRE:
                return new BatimentUniversitaire(nom, surface, coordonneesBatiment);
            case LOCAL_COMMERCIAL:
                return new LocalCommercial(nom, surface,coordonneesBatiment);
        }
        return null;
    }
    
    public void ajouterBatiment(Batiment batiment){
        this.listeBatiments.add(batiment);
    }

    public void supprimerBatiment(Batiment batiment){
        this.listeBatiments.remove(batiment);
    }

    public void modifierBatiment(Batiment ancienBatiment, Batiment nouveauBatiment){
        this.listeBatiments.remove(ancienBatiment);
        this.listeBatiments.add(nouveauBatiment);
    }

    public void genererReleveUnJour(LocalDate date){
        for (Batiment batiment : this.listeBatiments){
            batiment.genererReleves(date);
        }
    }

    public void clearBatiments(){
        listeBatiments.clear();
    }

    public void charger(String nomFichier){
        List<Batiment> resultats = jsonPersistanceService.charger(nomFichier);
        if (resultats != null){
            this.listeBatiments.clear();
            this.listeBatiments.addAll(resultats);
        }
    }

    public Batiment getBatimentLePlusProche(Coordonnees clic, int taille) {
        Batiment lePlusProche = null;
        for (Batiment batiment : listeBatiments) {
            if (clic.estSurBatiment(batiment, taille)) {
                if (lePlusProche == null || clic.estPlusProche(batiment.getCoordonnees(), lePlusProche.getCoordonnees())) {
                    lePlusProche = batiment;
                }
            }
        }
        return lePlusProche;
    }

}
