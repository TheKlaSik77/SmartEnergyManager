package org.example.smartenergymanager.service;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import org.example.smartenergymanager.model.batiment.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JsonPersistanceService {

    private static final String DOSSIER = "src/main/resources/org/example/smartenergymanager/data/";

    private ObservableList<String> fichiersSauvegarde;

    RuntimeTypeAdapterFactory<Batiment> adapter = RuntimeTypeAdapterFactory
            .of(Batiment.class, "type")
            .registerSubtype(Maison.class, "Maison")
            .registerSubtype(Appartement.class, "Appartement")
            .registerSubtype(Bureau.class, "Bureau")
            .registerSubtype(LocalCommercial.class, "LocalCommercial")
            .registerSubtype(BatimentUniversitaire.class, "BatimentUniversitaire");

    private Gson gson;
    private static JsonPersistanceService instance = null;

    private JsonPersistanceService() {
        // TODO : Faire un ObservableList ici et ajouter des listeners qui vont maj le combobox chargements de fichiers.
        this.fichiersSauvegarde = FXCollections.observableArrayList(getFichiersDisque());
        this.gson = new GsonBuilder()
                .registerTypeAdapterFactory(adapter)
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>)
                        (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>)
                        (json, typeOfT, context) -> LocalDate.parse(json.getAsString()))
                .create();
    }

    public static JsonPersistanceService getInstance() {
        if (instance == null) {
            instance = new JsonPersistanceService();
        }
        return instance;
    }

    public ObservableList<String> getFichiersSauvegarde(){
        return this.fichiersSauvegarde;
    }

    public List<String> getFichiersDisque() {
        File dossier = new File(DOSSIER);
        String[] fichiers = dossier.list((dir, name) -> name.endsWith(".json"));
        if (fichiers == null) return new ArrayList<>();

        List<String> noms = new ArrayList<>();
        for (String fichier : fichiers) {
            noms.add(fichier.replace(".json", "")); // retire l'extension
        }
        return noms;
    }

    public void sauvegarder(String nomFichier) {
        String chemin = DOSSIER + nomFichier + ".json";

        if (!fichiersSauvegarde.contains(nomFichier)) {
            fichiersSauvegarde.add(nomFichier);
        }

        try (FileWriter writer = new FileWriter(chemin, false)) {
            Type type = new TypeToken<List<Batiment>>() {
            }.getType();
            gson.toJson(BatimentService.getInstance().getBatiments(), type, writer); // on précise le type ici
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    public ObservableList<Batiment> charger(String nomFichier) {
        String chemin = DOSSIER + nomFichier + ".json";
        try (FileReader reader = new FileReader(chemin)) {
            Type type = new TypeToken<List<Batiment>>() {
            }.getType();
            List<Batiment> resultat = gson.fromJson(reader, type);
            if (!fichiersSauvegarde.contains(nomFichier)) {
                fichiersSauvegarde.add(nomFichier);
            }
            return FXCollections.observableArrayList(resultat);
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement : " + e.getMessage());
            return FXCollections.observableArrayList();
        }
    }
}