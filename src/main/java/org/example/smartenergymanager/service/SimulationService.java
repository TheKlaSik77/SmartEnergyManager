package org.example.smartenergymanager.service;

import java.time.LocalDate;

/*
    On utilise le design pattern singleton afin de n'avoir qu'une seule instance partout de simulation.
 */
public class SimulationService {
    private static SimulationService instance;

    private LocalDate dateActuelle;

    public SimulationService(){
        this.dateActuelle = LocalDate.of(2026,1,1);
    }

    public static SimulationService getInstance(){
        if (instance == null){
            instance = new SimulationService();
        }
        return instance;
    }

    public void avancerUnJour(){
        BatimentService.getInstance().genererReleveUnJour(dateActuelle);
        this.dateActuelle = dateActuelle.plusDays(1);
    }

    public void avancerUnMois(){
        LocalDate dateCible = dateActuelle.plusMonths(1);
        while (dateActuelle.isBefore(dateCible)){
            BatimentService.getInstance().genererReleveUnJour(dateActuelle);
            this.dateActuelle = dateActuelle.plusDays(1);
        }
    }
}
