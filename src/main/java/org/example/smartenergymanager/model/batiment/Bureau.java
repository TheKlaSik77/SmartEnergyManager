package org.example.smartenergymanager.model.batiment;
import org.example.smartenergymanager.model.Releve;
import org.example.smartenergymanager.model.utils.TypeEnergie;
import org.example.smartenergymanager.model.utils.Coordonnees;
import org.example.smartenergymanager.model.utils.Saison;
import org.example.smartenergymanager.service.CalendrierService;

import java.time.LocalDate;

public class Bureau extends Batiment {

    public Bureau(String nom, int surface, Coordonnees coordonnees) {
        super(nom, surface, coordonnees);
    }

    @Override
    public TypeBatiment getTypeBatiment() {
        return TypeBatiment.BUREAU;
    }

    @Override
    protected Releve genererElectricite(LocalDate date) {
        if (!CalendrierService.estWeekend(date)) {
            return this.calculerReleve(date, TypeEnergie.ELECTRICITE, 0.20, 0.40);
        } else {
            return this.calculerReleve(date, TypeEnergie.ELECTRICITE, 0.01, 0.03);
        }
    }

    @Override
    protected Releve genererEau(LocalDate date) {
        if (!CalendrierService.estWeekend(date)) {
            return this.calculerReleve(date, TypeEnergie.EAU, 0.50, 1.00);
        } else {
            return this.calculerReleve(date, TypeEnergie.EAU, 0.00, 0.10);
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
        if (CalendrierService.getSaison(date) == Saison.HIVER && !CalendrierService.estWeekend(date)) {
            coefMin = 0.15;
            coefMax = 0.30;
        } else if (CalendrierService.getSaison(date) == Saison.HIVER && CalendrierService.estWeekend(date)) {
            coefMin = 0.02;
            coefMax = 0.05;
        } else {
            coefMin = 0.00;
            coefMax = 0.02;
        }
        return this.calculerReleve(date, TypeEnergie.CHAUFFAGE, coefMin, coefMax);
    }

    @Override
    protected Releve genererClimatisation(LocalDate date) {
        double coefMin;
        double coefMax;
        if (CalendrierService.getSaison(date) == Saison.ETE && !CalendrierService.estWeekend(date)) {
            coefMin = 0.15;
            coefMax = 0.30;
        } else {
            coefMin = 0.00;
            coefMax = 0.00;
        }
        return this.calculerReleve(date, TypeEnergie.CLIMATISATION, coefMin, coefMax);
    }
}
