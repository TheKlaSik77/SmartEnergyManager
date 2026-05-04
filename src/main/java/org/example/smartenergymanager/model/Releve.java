package org.example.smartenergymanager.model;

import org.example.smartenergymanager.model.utils.TypeEnergie;

import java.time.LocalDate;

public class Releve {
    private LocalDate date;
    private TypeEnergie typeEnergie;
    private double quantite;
    private double cout;

    public Releve(LocalDate date, TypeEnergie typeEnergie, double quantite, double cout) {
        this.date = date;
        this.typeEnergie = typeEnergie;
        this.quantite = quantite;
        this.cout = cout;
    }

    public TypeEnergie getTypeEnergie() {
        return typeEnergie;
    }

    public double getQuantite() {
        return quantite;
    }

    public double getCout() {
        return cout;
    }

    public LocalDate getDate(){
        return this.date;
    }

}
