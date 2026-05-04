package org.example.smartenergymanager.model.batiment;

import org.example.smartenergymanager.model.Releve;
import org.example.smartenergymanager.model.utils.TypeEnergie;
import org.example.smartenergymanager.model.utils.Coordonnees;
import org.example.smartenergymanager.model.utils.Saison;
import org.example.smartenergymanager.service.CalendrierService;

import java.time.LocalDate;

public class Maison extends Batiment{


    public Maison(int id, String nom, int surface, Coordonnees coordonnees) {
        super(id, nom, surface, coordonnees);
    }

    @Override
    protected Releve genererElectricite(LocalDate date) {
        double coefMin = 0.05;
        double coefMax = 0.15;

        return this.calculerReleve(date,TypeEnergie.ELECTRICITE,coefMin,coefMax);
    }

    @Override
    protected Releve genererEau(LocalDate date) {
        double coefMin = 1;
        double coefMax = 2;

        return this.calculerReleve(date,TypeEnergie.EAU,coefMin,coefMax);    }

    @Override
    protected Releve genererGaz(LocalDate date) {
        double coefMin;
        double coefMax;
        if (CalendrierService.getSaison(date) == Saison.HIVER){
            coefMin = 0.10;
            coefMax = 0.20;
        } else {
            coefMin = 0.01;
            coefMax = 0.05;
        }
        return this.calculerReleve(date,TypeEnergie.GAZ,coefMin,coefMax);
    }

    @Override
    protected Releve genererChauffage(LocalDate date) {
        double coefMin;
        double coefMax;
        if (CalendrierService.getSaison(date) == Saison.HIVER) {
            coefMin = 0.20;
            coefMax = 0.40;
        } else {
            coefMin = 0.00;
            coefMax = 0.05;
        }
        return this.calculerReleve(date, TypeEnergie.CHAUFFAGE, coefMin, coefMax);
    }

    @Override
    protected Releve genererClimatisation(LocalDate date) {
        return this.calculerReleve(date, TypeEnergie.CLIMATISATION, 0.00, 0.00);
    }
}
