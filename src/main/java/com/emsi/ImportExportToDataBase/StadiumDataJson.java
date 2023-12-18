package com.emsi.ImportExportToDataBase;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.emsi.entities.Stadium;
import com.emsi.service.StadiumService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class StadiumDataJson {
    private StadiumService stadiumService = new StadiumService();

    public void importDataFromJson(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            Gson gson = new GsonBuilder().create();
            ArrayList<Stadium> stadiumList = gson.fromJson(reader, new TypeToken<ArrayList<Stadium>>() {
            }.getType());
            System.out.println("Importing Data From Json is done!\nWaiting for saving to database...");
            for (Stadium stadium : stadiumList) {
                stadiumService.saveOrUpdate(stadium);
            }
            System.out.println("Saving Stadiums From Json To Database is done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportDataToJson(String filePath) {
        ArrayList<Stadium> stadiums = (ArrayList<Stadium>) stadiumService.findAll();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(stadiums);

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
        System.out.println("Exporting Data From Database To Json is done!");
    }
}
