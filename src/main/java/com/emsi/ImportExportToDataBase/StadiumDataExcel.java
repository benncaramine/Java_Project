package com.emsi.ImportExportToDataBase;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.emsi.entities.Stadium;
import com.emsi.service.StadiumService;

public class StadiumDataExcel {
    private StadiumService stadiumService = new StadiumService();

    public void importDataFromExcel(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            ArrayList<Stadium> stadiumList = new ArrayList<>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) { // skip the header row
                    continue;
                }

                Stadium stadium = new Stadium();
                Cell idCell = row.getCell(0);
                Cell nameCell = row.getCell(1);
                Cell cityCell = row.getCell(2);
                Cell addressCell = row.getCell(3);
                Cell constructionYearCell = row.getCell(4);
                Cell capacityCell = row.getCell(5);

                stadium.setId((int) idCell.getNumericCellValue());
                stadium.setName(nameCell.getStringCellValue());
                stadium.setCity(cityCell.getStringCellValue());
                stadium.setAddress(addressCell.getStringCellValue());
                stadium.setConstructionYear((int) constructionYearCell.getNumericCellValue());
                stadium.setCapacity(capacityCell.getNumericCellValue());

                stadiumList.add(stadium);
            }

            System.out.println("Importing Data From Excel is done!\nWaiting for saving to database...");
            for (Stadium stadium : stadiumList) {
                stadiumService.saveOrUpdate(stadium);
            }
            System.out.println("Saving Stadiums From Excel To Database is done!");
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportDataToExcel(String filePath) {
        ArrayList<Stadium> stadiums = (ArrayList<Stadium>) stadiumService.findAll();

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Stadium Data");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Name");
            headerRow.createCell(2).setCellValue("City");
            headerRow.createCell(3).setCellValue("Address");
            headerRow.createCell(4).setCellValue("Construction Year");
            headerRow.createCell(5).setCellValue("Capacity");

            int rowIndex = 1;

            for (Stadium stadium : stadiums) {
                Row dataRow = sheet.createRow(rowIndex++);
                dataRow.createCell(0).setCellValue(stadium.getId());
                dataRow.createCell(1).setCellValue(stadium.getName());
                dataRow.createCell(2).setCellValue(stadium.getCity());
                dataRow.createCell(3).setCellValue(stadium.getAddress());
                dataRow.createCell(4).setCellValue(stadium.getConstructionYear());
                dataRow.createCell(5).setCellValue(stadium.getCapacity());
            }

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            } catch (IOException e) {
                e.printStackTrace();
            }

            workbook.close();
            System.out.println("Exporting Data From Database To Excel is done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
