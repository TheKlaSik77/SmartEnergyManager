package org.example.smartenergymanager.model.batiment;

import org.example.smartenergymanager.model.Releve;
import org.example.smartenergymanager.model.utils.TypeEnergie;
import org.example.smartenergymanager.model.utils.Coordonnees;
import org.example.smartenergymanager.model.utils.Saison;
import org.example.smartenergymanager.service.CalendrierService;

import java.time.LocalDate;

public class Appartement extends Batiment {

    public Appartement(int id, String nom, int surface, Coordonnees coordonnees) {
        super(id, nom, surface, coordonnees);
    }

    @Override
    protected Releve genererElectricite(LocalDate date) {
        return this.calculerReleve(date, TypeEnergie.ELECTRICITE, 0.03, 0.10);
    }

    @Override
    protected Releve genererEau(LocalDate date) {
        return this.calculerReleve(date, TypeEnergie.EAU, 0.80, 1.50);
    }

    @Override
    protected Releve genererGaz(LocalDate date) {
        double coefMin;
        double coefMax;
        if (CalendrierService.getSaison(date) == Saison.HIVER) {
            coefMin = 0.05;
            coefMax = 0.12;
        } else {
            coefMin = 0.00;
            coefMax = 0.03;
        }
        return this.calculerReleve(date, TypeEnergie.GAZ, coefMin, coefMax);
    }

    @Override
    protected Releve genererChauffage(LocalDate date) {
        double coefMin;
        double coefMax;
        if (CalendrierService.getSaison(date) == Saison.HIVER) {
            coefMin = 0.10;
            coefMax = 0.25;
        } else {
            coefMin = 0.00;
            coefMax = 0.03;
        }
        return this.calculerReleve(date, TypeEnergie.CHAUFFAGE, coefMin, coefMax);
    }

    @Override
    protected Releve genererClimatisation(LocalDate date) {
        double coefMin;
        double coefMax;
        if (CalendrierService.getSaison(date) == Saison.ETE) {
            coefMin = 0.05;
            coefMax = 0.15;
        } else {
            coefMin = 0.00;
            coefMax = 0.00;
        }
        return this.calculerReleve(date, TypeEnergie.CLIMATISATION, coefMin, coefMax);
    }
}