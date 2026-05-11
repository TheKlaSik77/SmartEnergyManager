package org.example.smartenergymanager;

import org.example.smartenergymanager.model.batiment.*;
import org.example.smartenergymanager.model.utils.Coordonnees;
import org.example.smartenergymanager.model.utils.TypeEnergie;
import org.example.smartenergymanager.service.BatimentService;
import org.example.smartenergymanager.service.JsonPersistanceService;
import org.example.smartenergymanager.service.SimulationService;

import java.io.File;

public class Main {

    static void main() {

        BatimentService service = BatimentService.getInstance();

        Maison m = new Maison("Maison1" , 90, new Coordonnees(100, 150));
        Appartement a = new Appartement( "Appartement1" , 45, new Coordonnees(200, 300));
        Bureau b = new Bureau("Bureau1" , 200, new Coordonnees(50, 400));
        LocalCommercial lc = new LocalCommercial("Local1" , 120, new Coordonnees(350, 250));
        BatimentUniversitaire u = new BatimentUniversitaire( "Université1" , 5000, new Coordonnees(400, 500));

        service.ajouterBatiment(m);
        service.ajouterBatiment(a);
        service.ajouterBatiment(b);
        service.ajouterBatiment(lc);
        service.ajouterBatiment(u);

        SimulationService sim = SimulationService.getInstance();

        // On simule en hiver (janvier 2026) et en été (juillet 2026)
        sim.avancerUnJour(); // 1er janvier — hiver
        sim.avancerUnJour(); // 2 janvier — hiver

        // Avancer jusqu'en été
        for (int i = 0; i < 180; i++) {
            sim.avancerUnJour();
        }

        JsonPersistanceService.getInstance().sauvegarder("batiments");
        System.out.println("Sauvegarde terminée !");
    }

}
