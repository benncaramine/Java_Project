package com.emsi.ImportExportToDataBase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.emsi.entities.Stadium;
import com.emsi.service.StadiumService;

public class StadiumDataTxt {
    private StadiumService stadiumService = new StadiumService();

    public void importDataFromTextFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            List<Stadium> stadiumList = new ArrayList<>();
            String readLine = br.readLine();

            while (readLine != null) {
                String[] stadiumData = readLine.split("\\|");

                Stadium stadium = new Stadium();
                stadium.setId(Integer.parseInt(stadiumData[0].trim()));
                stadium.setName(stadiumData[1].trim());
                stadium.setCity(stadiumData[2].trim());
                stadium.setAddress(stadiumData[3].trim());
                stadium.setConstructionYear(Integer.parseInt(stadiumData[4].trim()));
                stadium.setCapacity(Double.parseDouble(stadiumData[5].trim()));

                stadiumList.add(stadium);
                readLine = br.readLine();
            }
            System.out.println("Importing Data From text is done!\nWaiting for saving to database...");
            for (Stadium stadium : stadiumList) {
                stadiumService.saveOrUpdate(stadium);
            }
            System.out.println("Saving Stadiums From Text To Database is done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportDataToTextFile(String filePath) {
        ArrayList<Stadium> stadiums = (ArrayList<Stadium>) stadiumService.findAll();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Stadium stadium : stadiums) {
                String stadiumData = stadium.getId() + " | " + stadium.getName() + " | " + stadium.getCity() + " | " +
                        stadium.getAddress() + " | " + stadium.getConstructionYear() + " | " + stadium.getCapacity();

                bw.write(stadiumData);
                bw.newLine();
            }

            System.out.println("Exporting Data From Database To Text is done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
