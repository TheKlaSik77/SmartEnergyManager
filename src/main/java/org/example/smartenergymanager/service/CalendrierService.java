package org.example.smartenergymanager.service;

import org.example.smartenergymanager.model.utils.Saison;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class CalendrierService {


    public static boolean estWeekend(LocalDate date) {
        return (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY);
    }

    public static boolean estVacancesScolaires(LocalDate date) {
        // Toussaint 2025
        if (!date.isBefore(LocalDate.of(2025, 10, 18)) && !date.isAfter(LocalDate.of(2025, 11, 3))) return true;

        // Noël 2025
        if (!date.isBefore(LocalDate.of(2025, 12, 20)) && !date.isAfter(LocalDate.of(2026, 1, 5))) return true;

        // Hiver 2026
        if (!date.isBefore(LocalDate.of(2026, 2, 14)) && !date.isAfter(LocalDate.of(2026, 3, 2))) return true;

        // Printemps 2026
        if (!date.isBefore(LocalDate.of(2026, 4, 18)) && !date.isAfter(LocalDate.of(2026, 5, 4))) return true;

        // Été 2026
        if (!date.isBefore(LocalDate.of(2026, 7, 4)) && !date.isAfter(LocalDate.of(2026, 9, 1))) return true;

        return false;
    }

    public static Saison getSaison(LocalDate date) {
        int mois = date.getMonthValue();
        int jour = date.getDayOfMonth();

        if ((mois == 12 && jour >= 21) || mois == 1 || mois == 2 || (mois == 3 && jour < 20)) {
            return Saison.HIVER;
        } else if ((mois == 3 && jour >= 20) || mois == 4 || mois == 5 || (mois == 6 && jour < 21)) {
            return Saison.PRINTEMPS;
        } else if ((mois == 6 && jour >= 21) || mois == 7 || mois == 8 || (mois == 9 && jour < 23)) {
            return Saison.ETE;
        } else {
            return Saison.AUTOMNE;
        }
    }
}
