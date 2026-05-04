package org.example.smartenergymanager.model.batiment;

import org.example.smartenergymanager.model.Releve;
import org.example.smartenergymanager.model.utils.TypeEnergie;
import org.example.smartenergymanager.model.utils.Coordonnees;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Batiment {
    private int id;
    private String nom;
    private int surface;
    private Coordonnees coordonnees;
    private List<Releve> listeReleves;

    public Batiment(int id, String nom, int surface, Coordonnees coordonnees) {
        this.id = id;
        this.nom = nom;
        this.surface = surface;
        this.coordonnees = coordonnees;
        this.listeReleves = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }

    public int getSurface() {
        return surface;
    }

    public Coordonnees getCoordonnees() {
        return coordonnees;
    }

    public List<Releve> getListeReleves() {
        return this.listeReleves;
    }

    public void ajouterReleve(Releve nouveauReleve) {
        this.listeReleves.add(nouveauReleve);
    }

    protected Releve calculerReleve(LocalDate date, TypeEnergie type, double coefMin, double coefMax) {
        double min = getSurface() * coefMin;
        double max = getSurface() * coefMax;
        double quantite = min + Math.random() * (max - min);
        double cout = quantite * type.getPrixUnitaire();
        return new Releve(date, type, quantite, cout);
    }

    public void genererReleves(LocalDate date){
        listeReleves.add(genererElectricite(date));
        listeReleves.add(genererEau(date));
        listeReleves.add(genererGaz(date));
        listeReleves.add(genererChauffage(date));
        listeReleves.add(genererClimatisation(date));

    }

    protected abstract Releve genererElectricite(LocalDate date);

    protected abstract Releve genererEau(LocalDate date);

    protected abstract Releve genererGaz(LocalDate date);

    protected abstract Releve genererChauffage(LocalDate date);

    protected abstract Releve genererClimatisation(LocalDate date);
}
