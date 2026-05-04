package org.example.smartenergymanager.model.utils;

public enum TypeEnergie {
    ELECTRICITE("kWh",0.25),
    EAU("L",0.004),
    GAZ("m³",1.10),
    CHAUFFAGE("kWh",0.20),
    CLIMATISATION("kWh",0.25);


    private final String unite;
    private final double prixUnitaire;

    TypeEnergie(String unite, double prixUnitaire){
        this.unite = unite;
        this.prixUnitaire = prixUnitaire;
    }

    public String getUnite(){
        return this.unite;
    }

    public double getPrixUnitaire() {
        return this.prixUnitaire;
    }
}
