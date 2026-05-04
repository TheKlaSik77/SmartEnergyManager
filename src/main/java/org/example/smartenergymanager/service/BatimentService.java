package org.example.smartenergymanager.service;

import org.example.smartenergymanager.model.batiment.Batiment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BatimentService {
    private List<Batiment> listeBatiments;
    private JsonPersistanceService jsonPersistanceService;

    public BatimentService(){
        this.listeBatiments = new ArrayList<>();
        this.jsonPersistanceService = new JsonPersistanceService();
    }

    public List<Batiment> getBatiments(){
        return this.listeBatiments;
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

    public void sauvegarder(){
        jsonPersistanceService.sauvegarder(this.listeBatiments);
    }

    public void charger(){
        this.listeBatiments = jsonPersistanceService.charger();
    }

}
