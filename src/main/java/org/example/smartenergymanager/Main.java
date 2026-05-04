package org.example.smartenergymanager;

import org.example.smartenergymanager.model.batiment.*;
import org.example.smartenergymanager.model.utils.Coordonnees;
import org.example.smartenergymanager.model.utils.TypeEnergie;
import org.example.smartenergymanager.service.BatimentService;
import org.example.smartenergymanager.service.SimulationService;

public class Main {

    static void main() {
        BatimentService service = new BatimentService();

        Maison m = new Maison(1, "Maison Montmartre", 90, new Coordonnees(100, 150));
        Appartement a = new Appartement(2, "Appart Marais", 45, new Coordonnees(200, 300));
        Bureau b = new Bureau(3, "Bureau La Défense", 200, new Coordonnees(50, 400));
        LocalCommercial lc = new LocalCommercial(4, "Boutique Rivoli", 120, new Coordonnees(350, 250));
        BatimentUniversitaire u = new BatimentUniversitaire(5, "Fac Jussieu", 5000, new Coordonnees(400, 500));

        service.ajouterBatiment(m);
        service.ajouterBatiment(a);
        service.ajouterBatiment(b);
        service.ajouterBatiment(lc);
        service.ajouterBatiment(u);

        SimulationService sim = new SimulationService(service);

        // On simule en hiver (janvier 2026) et en été (juillet 2026)
        sim.avancerUnJour(); // 1er janvier — hiver
        sim.avancerUnJour(); // 2 janvier — hiver

        // Avancer jusqu'en été
        for (int i = 0; i < 180; i++) {
            sim.avancerUnJour();
        }

        service.sauvegarder();
        System.out.println("Sauvegarde terminée !");
    }

}
