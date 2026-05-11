package org.example.smartenergymanager.model.batiment;

import org.example.smartenergymanager.model.Releve;
import org.example.smartenergymanager.model.utils.TypeEnergie;
import org.example.smartenergymanager.model.utils.Coordonnees;
import org.example.smartenergymanager.model.utils.Saison;
import org.example.smartenergymanager.service.CalendrierService;

import java.time.LocalDate;

public class LocalCommercial extends Batiment {

    public LocalCommercial(String nom, int surface, Coordonnees coordonnees) {
        super(nom, surface, coordonnees);
    }

    @Override
    public TypeBatiment getTypeBatiment() {
        return TypeBatiment.LOCAL_COMMERCIAL;
    }

    @Override
    protected Releve genererElectricite(LocalDate date) {
        return this.calculerReleve(date, TypeEnergie.ELECTRICITE, 0.25, 0.50);
    }

    @Override
    protected Releve genererEau(LocalDate date) {
        return this.calculerReleve(date, TypeEnergie.EAU, 0.80, 1.50);
    }

    @Override
    protected Releve genererGaz(LocalDate date) {
        return this.calculerReleve(date, TypeEnergie.GAZ, 0.00, 0.00);
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
        double coefMin;
        double coefMax;
        if (CalendrierService.getSaison(date) == Saison.ETE) {
            coefMin = 0.30;
            coefMax = 0.60;
        } else {
            coefMin = 0.05;
            coefMax = 0.15;
        }
        return this.calculerReleve(date, TypeEnergie.CLIMATISATION, coefMin, coefMax);
    }
}
