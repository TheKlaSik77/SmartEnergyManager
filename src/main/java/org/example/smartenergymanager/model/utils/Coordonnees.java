package org.example.smartenergymanager.model.utils;

import org.example.smartenergymanager.model.batiment.Batiment;

public class Coordonnees {
    private double x;
    private double y;

    public Coordonnees(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

    /*
    Retourne True si coordonnées 1 est plus proche que coordonnées 2
     */
    public boolean estPlusProche(Coordonnees coordonnees1, Coordonnees coordonnees2){
        double distA = Math.pow(this.x - coordonnees1.getX(), 2) + Math.pow(this.y - coordonnees1.getY(), 2);
        double distB = Math.pow(this.x - coordonnees2.getX(), 2) + Math.pow(this.y - coordonnees2.getY(), 2);
        return distA < distB;
    }

    public boolean estSurBatiment(Batiment batiment, int tailleBatiment) {
        Coordonnees coordonneesBatiment = batiment.getCoordonnees();
        double xmin = coordonneesBatiment.getX() - (double) tailleBatiment / 2;
        double xmax = coordonneesBatiment.getX() + (double) tailleBatiment / 2;
        double ymin = coordonneesBatiment.getY() - (double) tailleBatiment / 2;
        double ymax = coordonneesBatiment.getY() + (double) tailleBatiment / 2;

        return this.x >= xmin && this.y >= ymin && this.x <= xmax && this.y <= ymax;
    }
}
