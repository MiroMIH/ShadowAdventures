package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    private void handleStart() {
        // Handle starting the game (load next scene)
        try {
            App.setRoot("gameScene");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleClose() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?", ButtonType.YES,
                ButtonType.NO);
        alert.setTitle("Exit Confirmation");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.close();
            }
        });
    }
}
