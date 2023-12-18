package com.emsi.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.emsi.dao.impl.DB;
import com.emsi.entities.Stadium;
import com.emsi.service.StadiumService;
import com.emsi.service.StadiumServiceImpl;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class StadiumListController {
    @FXML
    public TableView<Stadium> stadiumTableView;
    
    @FXML
    public Label usernameLabel;
    
    public StadiumService stadiumService = new StadiumService();
    public static Stadium tableStadium = null;
    
    public void initialize() {
        setupDatabaseConnection();
        setupTableSelectionListener();
        usernameLabel.setText("WELCOME TO OUR LIST OF STADIUMS !") ;
    }

    public void setupDatabaseConnection() {
        DB.getConnection();
    }

    @FXML
    public void createStadium() throws IOException {
        tableStadium = null;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/emsi/View/StadiumForm.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 500, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMinWidth(500); 
        stage.setMinHeight(600);
        stage.setMaxWidth(500); 
        stage.setMaxHeight(600);
        stage.setTitle("Stadium Creation");
        stage.setOnHidden(event -> {
            readStadium();
        });
        stage.show();
    }

    @FXML
    public void readStadium() {
        
        ArrayList<Stadium> stadiums = (ArrayList<Stadium>) stadiumService.findAll();
        for (Stadium stadium : stadiums) {
            stadiumTableView.getItems().add(stadium);
        }
    }

    @FXML
    public void updateStadium() throws IOException {
        Stadium selectedStadium = stadiumTableView.getSelectionModel().getSelectedItem();
        if (selectedStadium == null) {
            System.out.println("Please select a stadium to update.");
            showErrorMessage("error", "Invalid Selection", "Please select a stadium to update.");
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/emsi/View/StadiumForm.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 500, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMinWidth(500); 
        stage.setMinHeight(600);
        stage.setMaxWidth(500); 
        stage.setMaxHeight(600);
        stage.setTitle("Stadium Modification");
        stage.setOnHidden(event -> {
            readStadium();
        });
        stage.show();
        
    }

    @FXML
    public void deleteStadium() {
        Stadium selectedStadium = stadiumTableView.getSelectionModel().getSelectedItem();

        if (selectedStadium == null) {
            System.out.println("Please select a stadium to delete.");
            showErrorMessage("error", "Invalid Selection", "Please select a stadium to delete.");
            return;
        }
        stadiumService.remove(selectedStadium);
        System.out.println("Stadium deleted successfully!");
        showErrorMessage("information", "Valid input", "Stadium deleted successfully!");
        readStadium(); // Refresh the table view after deleting a stadium
    }

    public void setupTableSelectionListener() {
        stadiumTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Stadium>() {
            @Override
            public void changed(ObservableValue<? extends Stadium> observable, Stadium oldValue, Stadium newValue) {
                if (newValue != null) {
                    tableStadium = newValue;
                } else {
                    tableStadium = null;
                }
            }
        });
    }

    @FXML
    public void importData() {
        StadiumServiceImpl stadiumServiceImpl = new StadiumServiceImpl();
        stadiumServiceImpl.importStadiumsFromExcel(Stadium.path + "stadiumInputData.xlsx");
        System.out.println("Stadiums imported successfully!");
        showErrorMessage("information", "Valid input", "Stadiums imported successfully!");
        readStadium(); // Refresh the table view after importing stadiums
    }

    @FXML
    public void exportData() {
        StadiumServiceImpl stadiumServiceImpl = new StadiumServiceImpl();
        stadiumServiceImpl.exportStadiumsToExcel(Stadium.path + "stadiumOutputData.xlsx");
        System.out.println("Stadiums exported successfully!");
        showErrorMessage("information", "Valid input", "Stadiums exported successfully!");
        readStadium(); // Refresh the table view after exporting the stadium
    }

    public void showErrorMessage(String type, String title, String message) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        if (type.equals("error")) {
            alert = new Alert(Alert.AlertType.ERROR);
        }
        else if (type.equals("information")) {
            alert = new Alert(Alert.AlertType.INFORMATION);
        }
        else if (type.equals("warning")) {
            alert = new Alert(Alert.AlertType.WARNING);
        }
        else if (type.equals("confirm")) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
        }
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/com/emsi/View/styles.css").toExternalForm());
        alert.showAndWait();
    }
}
