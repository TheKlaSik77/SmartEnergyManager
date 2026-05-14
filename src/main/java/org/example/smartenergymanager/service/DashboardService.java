package org.example.smartenergymanager.service;

import org.example.smartenergymanager.model.Releve;
import org.example.smartenergymanager.model.batiment.Batiment;
import org.example.smartenergymanager.model.batiment.TypeBatiment;
import org.example.smartenergymanager.model.utils.TypeEnergie;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;
import java.util.TreeMap;

public class DashboardService {

    public static int getNbBatiments(){
        return BatimentService.getInstance().getBatiments().size();
    }

    public static int getNbBatimentParTypeBatiment(TypeBatiment typeBatiment){
        int somme = 0;
        for (Batiment batiment : BatimentService.getInstance().getBatiments()){
            if (batiment.getTypeBatiment() == typeBatiment){
                somme += 1;
            }
        }
        return somme;
    }

    public static int getNbReleves(){
        int nbRelevesTot = 0;
        for (Batiment batiment : BatimentService.getInstance().getBatiments()){
            nbRelevesTot += batiment.getListeReleves().size();
        }
        return nbRelevesTot;
    }

    public static int getNbRelevesParTypeEnergie(TypeEnergie typeEnergie){
        int nbRelevesTot = 0;
        for (Batiment batiment : BatimentService.getInstance().getBatiments()){
            for (Releve releve : batiment.getListeReleves()){
                if (releve.getTypeEnergie() == typeEnergie){
                    nbRelevesTot += 1;
                }
            }
        }
        return nbRelevesTot;
    }

    public static Map<LocalDate, Double> getCoutParJour(Batiment batiment, int nbJours) {
        Map<LocalDate, Double> map = new TreeMap<>();
        for (Releve releve : batiment.getListeReleves()) {
            LocalDate date = releve.getDate();
            if (date.isAfter(LocalDate.now().minusDays(nbJours))) {
                map.merge(date, releve.getCout(), Double::sum);
            }
        }
        return map;
    }

    public static Map<LocalDate, Double> getCoutParJourParTypeEnergie(Batiment batiment, int nbJours, TypeEnergie typeEnergie) {
        Map<LocalDate, Double> map = new TreeMap<>();
        for (Releve releve : batiment.getListeReleves()) {
            if (releve.getTypeEnergie() == typeEnergie && releve.getDate().isAfter(LocalDate.now().minusDays(nbJours))) {
                map.merge(releve.getDate(), releve.getCout(), Double::sum);
            }
        }
        return map;
    }

    public static Map<LocalDate, Double> getQuantiteParJourParTypeEnergie(Batiment batiment, int nbJours, TypeEnergie typeEnergie) {
        Map<LocalDate, Double> map = new TreeMap<>();
        for (Releve releve : batiment.getListeReleves()) {
            if (releve.getTypeEnergie() == typeEnergie && releve.getDate().isAfter(LocalDate.now().minusDays(nbJours))) {
                map.merge(releve.getDate(), releve.getQuantite(), Double::sum);
            }
        }
        return map;
    }
    public static Map<YearMonth, Double> getCoutParMois(Batiment batiment, int nbMois) {
        Map<YearMonth, Double> map = new TreeMap<>();
        YearMonth limite = YearMonth.now().minusMonths(nbMois);
        for (Releve releve : batiment.getListeReleves()) {
            YearMonth ym = YearMonth.from(releve.getDate());
            if (ym.isAfter(limite)) map.merge(ym, releve.getCout(), Double::sum);
        }
        return map;
    }

    public static Map<YearMonth, Double> getCoutParMoisParTypeEnergie(Batiment batiment, int nbMois, TypeEnergie typeEnergie) {
        Map<YearMonth, Double> map = new TreeMap<>();
        YearMonth limite = YearMonth.now().minusMonths(nbMois);
        for (Releve releve : batiment.getListeReleves()) {
            YearMonth ym = YearMonth.from(releve.getDate());
            if (releve.getTypeEnergie() == typeEnergie && ym.isAfter(limite))
                map.merge(ym, releve.getCout(), Double::sum);
        }
        return map;
    }

    public static Map<YearMonth, Double> getQuantiteParMoisParTypeEnergie(Batiment batiment, int nbMois, TypeEnergie typeEnergie) {
        Map<YearMonth, Double> map = new TreeMap<>();
        YearMonth limite = YearMonth.now().minusMonths(nbMois);
        for (Releve releve : batiment.getListeReleves()) {
            YearMonth ym = YearMonth.from(releve.getDate());
            if (releve.getTypeEnergie() == typeEnergie && ym.isAfter(limite))
                map.merge(ym, releve.getQuantite(), Double::sum);
        }
        return map;
    }

    public static Map<Integer, Double> getCoutParAnnee(Batiment batiment) {
        Map<Integer, Double> map = new TreeMap<>();
        for (Releve releve : batiment.getListeReleves()) {
            map.merge(releve.getDate().getYear(), releve.getCout(), Double::sum);
        }
        return map;
    }

    public static Map<Integer, Double> getCoutParAnneeParTypeEnergie(Batiment batiment, TypeEnergie typeEnergie) {
        Map<Integer, Double> map = new TreeMap<>();
        for (Releve releve : batiment.getListeReleves()) {
            if (releve.getTypeEnergie() == typeEnergie)
                map.merge(releve.getDate().getYear(), releve.getCout(), Double::sum);
        }
        return map;
    }

    public static Map<Integer, Double> getQuantiteParAnneeParTypeEnergie(Batiment batiment, TypeEnergie typeEnergie) {
        Map<Integer, Double> map = new TreeMap<>();
        for (Releve releve : batiment.getListeReleves()) {
            if (releve.getTypeEnergie() == typeEnergie)
                map.merge(releve.getDate().getYear(), releve.getQuantite(), Double::sum);
        }
        return map;
    }
}
