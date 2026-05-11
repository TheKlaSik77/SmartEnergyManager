package org.example.smartenergymanager.model.batiment;

import org.example.smartenergymanager.model.Releve;
import org.example.smartenergymanager.model.utils.TypeEnergie;
import org.example.smartenergymanager.model.utils.Coordonnees;
import org.example.smartenergymanager.model.utils.Saison;
import org.example.smartenergymanager.service.CalendrierService;

import java.time.LocalDate;

public class BatimentUniversitaire extends Batiment {

    public BatimentUniversitaire(String nom, int surface, Coordonnees coordonnees) {
        super(nom, surface, coordonnees);
    }

    @Override
    public TypeBatiment getTypeBatiment() {
        return TypeBatiment.BATIMENT_UNIVERSITAIRE;
    }

    @Override
    protected Releve genererElectricite(LocalDate date) {
        if (!CalendrierService.estWeekend(date) && !CalendrierService.estVacancesScolaires(date)) {
            return this.calculerReleve(date, TypeEnergie.ELECTRICITE, 0.40, 0.80);
        } else {
            return this.calculerReleve(date, TypeEnergie.ELECTRICITE, 0.02, 0.08);
        }
    }

    @Override
    protected Releve genererEau(LocalDate date) {
        if (!CalendrierService.estVacancesScolaires(date)) {
            return this.calculerReleve(date, TypeEnergie.EAU, 2.00, 4.00);
        } else {
            return this.calculerReleve(date, TypeEnergie.EAU, 0.10, 0.30);
        }
    }

    @Override
    protected Releve genererGaz(LocalDate date) {
        return this.calculerReleve(date, TypeEnergie.GAZ, 0.00, 0.00);
    }

    @Override
    protected Releve genererChauffage(LocalDate date) {
        double coefMin;
        double coefMax;
        if (CalendrierService.getSaison(date) == Saison.HIVER && !CalendrierService.estVacancesScolaires(date)) {
            coefMin = 0.40;
            coefMax = 0.80;
        } else if (CalendrierService.getSaison(date) == Saison.HIVER && CalendrierService.estVacancesScolaires(date)) {
            coefMin = 0.05;
            coefMax = 0.15;
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
        if (CalendrierService.getSaison(date) == Saison.ETE && !CalendrierService.estVacancesScolaires(date)) {
            coefMin = 0.30;
            coefMax = 0.60;
        } else {
            coefMin = 0.00;
            coefMax = 0.00;
        }
        return this.calculerReleve(date, TypeEnergie.CLIMATISATION, coefMin, coefMax);
    }
}
