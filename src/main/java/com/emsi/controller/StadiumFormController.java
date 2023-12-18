package com.emsi.controller;

import com.emsi.dao.impl.DB;
import com.emsi.entities.Stadium;
import com.emsi.service.StadiumService;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StadiumFormController {
    @FXML
    public TextField nameTextField;
    @FXML
    public TextField cityTextField;
    @FXML
    public TextField addressTextField;
    @FXML
    public TextField constructionYearTextField;
    @FXML
    public TextField capacityTextField;
    @FXML
    public Button createUpdateButton;

    public StadiumService stadiumService = new StadiumService();

    public void initialize() {
        setupDatabaseConnection();
        if (StadiumListController.tableStadium != null) {
            createUpdateButton.setText("Update");
            fillForm();
        } else {
            createUpdateButton.setText("Create");
        }
    }

    public void setupDatabaseConnection() {
        DB.getConnection();
    }

    @FXML
    public void createStadium() {
        if (StadiumListController.tableStadium != null) {
            updateStadium();
            return;
        }
        if (nameTextField.getText().isEmpty() || cityTextField.getText().isEmpty()
                || addressTextField.getText().isEmpty() || constructionYearTextField.getText().isEmpty()
                || capacityTextField.getText().isEmpty()) {
            showErrorMessage("error", "Missing input", "Please enter all the required fields.");
            return;
        }
        String name = nameTextField.getText();
        String city = cityTextField.getText();
        String address = addressTextField.getText();
        int constructionYear = Integer.parseInt(constructionYearTextField.getText());
        double capacity = Double.parseDouble(capacityTextField.getText());

        Stadium stadium = new Stadium( name, city, address, constructionYear, capacity);
        stadiumService.save(stadium);

        System.out.println("Stadium inserted successfully!");
        showErrorMessage("information", "Valid input", "Stadium inserted successfully!");
        Stage formStage = (Stage) nameTextField.getScene().getWindow();
        formStage.close();
    }

    @FXML
    public void fillForm() {
        Stadium selectedStadium = StadiumListController.tableStadium;
        nameTextField.setText(selectedStadium.getName());
        cityTextField.setText(selectedStadium.getCity());
        addressTextField.setText(selectedStadium.getAddress());
        constructionYearTextField.setText(String.valueOf(selectedStadium.getConstructionYear()));
        capacityTextField.setText(String.valueOf(selectedStadium.getCapacity()));
    }

    @FXML
    public void updateStadium() {
        if (nameTextField.getText().isEmpty() || cityTextField.getText().isEmpty()
                || addressTextField.getText().isEmpty() || constructionYearTextField.getText().isEmpty()
                || capacityTextField.getText().isEmpty()) {
            showErrorMessage("error", "Missing input", "Please enter all the required fields.");
            return;
        }
        Stadium selectedStadium = StadiumListController.tableStadium;
        if (selectedStadium == null) {
            System.out.println("Please select a stadium to update.");
            showErrorMessage("error", "Invalid Selection", "Please select a stadium to update.");
            return;
        }
        int id = selectedStadium.getId();
        String name = nameTextField.getText();
        String city = cityTextField.getText();
        String address = addressTextField.getText();
        int constructionYear = Integer.parseInt(constructionYearTextField.getText());
        double capacity = Double.parseDouble(capacityTextField.getText());

        Stadium stadium = new Stadium(id, name, city, address, constructionYear, capacity);
        stadiumService.update(stadium);

        System.out.println("Stadium updated successfully!");
        showErrorMessage("information", "Valid input", "Stadium updated successfully!");
        Stage formStage = (Stage) nameTextField.getScene().getWindow();
        formStage.close();
    }

    @FXML
    public void clearStadium() {
        nameTextField.clear();
        cityTextField.clear();
        addressTextField.clear();
        constructionYearTextField.clear();
        capacityTextField.clear();
    }

    public void showErrorMessage(String type, String title, String message) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        if (type.equals("error")) {
            alert = new Alert(Alert.AlertType.ERROR);
        } else if (type.equals("information")) {
            alert = new Alert(Alert.AlertType.INFORMATION);
        } else if (type.equals("warning")) {
            alert = new Alert(Alert.AlertType.WARNING);
        } else if (type.equals("confirm")) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
        }
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/com/emsi/View/styles.css").toExternalForm());
        alert.showAndWait();
    }
}
