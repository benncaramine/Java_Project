package com.emsi.service;

import com.emsi.ImportExportToDataBase.StadiumDataExcel;
import com.emsi.ImportExportToDataBase.StadiumDataJson;
import com.emsi.ImportExportToDataBase.StadiumDataTxt;

public class StadiumServiceImpl extends StadiumService {

    private StadiumDataExcel stadiumDataExcel;
	private StadiumDataTxt stadiumDataTxt;
	private StadiumDataJson stadiumDataJson;
	
    public StadiumServiceImpl() {
        stadiumDataExcel = new StadiumDataExcel();
        stadiumDataTxt = new StadiumDataTxt();
		stadiumDataJson = new StadiumDataJson();
    }

    public void importStadiumsFromExcel(String filePath) {
		stadiumDataExcel.importDataFromExcel(filePath);
	}

    public void exportStadiumsToExcel(String filePath) {
        stadiumDataExcel.exportDataToExcel(filePath);
    }
    
    public void importStadiumsFromTextFile(String filePath) {
		stadiumDataTxt.importDataFromTextFile(filePath);
	}

	public void exportStadiumsToTextFile(String filePath) {
		stadiumDataTxt.exportDataToTextFile(filePath);
	}

    public void importStadiumsFromJson(String filePath) {
        stadiumDataJson.importDataFromJson(filePath);
    }

	public void exportStadiumsToJson(String filePath) {
		stadiumDataJson.exportDataToJson(filePath);
	}
}