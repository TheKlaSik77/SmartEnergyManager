package org.example.smartenergymanager.service;

import java.time.LocalDate;

public class SimulationService {
    private LocalDate dateActuelle;
    private BatimentService batimentService;

    public SimulationService(BatimentService batimentService){
        this.batimentService = batimentService;
        this.dateActuelle = LocalDate.of(2026,1,1);
    }

    public void avancerUnJour(){
        this.batimentService.genererReleveUnJour(dateActuelle);
        this.dateActuelle = dateActuelle.plusDays(1);
    }

    public void avancerUnMois(){
        LocalDate dateCible = dateActuelle.plusMonths(1);
        while (dateActuelle.equals(dateCible)){
            this.batimentService.genererReleveUnJour(dateActuelle);
            this.dateActuelle = dateActuelle.plusDays(1);
        }
    }
}
