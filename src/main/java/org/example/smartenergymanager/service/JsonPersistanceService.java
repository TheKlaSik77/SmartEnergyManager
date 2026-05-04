package org.example.smartenergymanager.service;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import org.example.smartenergymanager.model.batiment.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JsonPersistanceService {

    private String cheminFichier;
    RuntimeTypeAdapterFactory<Batiment> adapter = RuntimeTypeAdapterFactory
            .of(Batiment.class, "type")
            .registerSubtype(Maison.class, "Maison")
            .registerSubtype(Appartement.class, "Appartement")
            .registerSubtype(Bureau.class, "Bureau")
            .registerSubtype(LocalCommercial.class, "LocalCommercial")
            .registerSubtype(BatimentUniversitaire.class, "BatimentUniversitaire");

    private Gson gson;

    public JsonPersistanceService() {
        this.cheminFichier = "src/main/resources/org/example/smartenergymanager/data/batiments.json";
        this.gson = new GsonBuilder()
                .registerTypeAdapterFactory(adapter)
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>)
                        (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>)
                        (json, typeOfT, context) -> LocalDate.parse(json.getAsString()))
                .create();
    }

    public void sauvegarder(List<Batiment> listeBatiments) {
        // Permet de créer un nouveau champ type qui viendrait définir le type de Batiment
        try {
            FileWriter writer = new FileWriter(cheminFichier);
            gson.toJson(listeBatiments, writer);
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde du fichier : " + e.getMessage());
        }
    }

    public List<Batiment> charger() {
        try {
            FileReader reader = new FileReader(cheminFichier);
            Type type = new TypeToken<List<Batiment>>() {
            }.getType();
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement : " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
