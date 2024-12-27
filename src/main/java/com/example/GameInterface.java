package com.example;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameInterface extends Application {

    private Personnage selectedCharacter;
    private Personnage guerrier = new Guerrier("Thor");
    private Personnage mage = new Mage("Gandalf");
    private Personnage voleur = new Voleur("Loki");
    private Personnage enemy1;
    private Personnage enemy2;
    private boolean playerTurn = true; // Controls turn-based logic

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Game Menu");

        VBox layout = new VBox(15);
        layout.setStyle("-fx-alignment: center; -fx-padding: 20;");

        Button playButton = new Button("Play");
        playButton.setStyle("-fx-font-size: 16px; -fx-background-color: #28C59F; -fx-text-fill: white;");
        playButton.setFont(Font.font("Arial", 18));
        playButton.setOnAction(e -> showCharacterSelectionScreen(primaryStage));

        addHoverEffect(playButton);

        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-font-size: 16px; -fx-background-color: #FF5252; -fx-text-fill: white;");
        closeButton.setFont(Font.font("Arial", 18));
        closeButton.setOnAction(e -> closeGame(primaryStage));

        addHoverEffect(closeButton);

        layout.getChildren().addAll(playButton, closeButton);

        Scene menuScene = new Scene(layout, 400, 300); // 400x300 window
        primaryStage.setScene(menuScene);

        primaryStage.show();
    }

    private void showCharacterSelectionScreen(Stage primaryStage) {
        VBox layout = new VBox(15);
        layout.setStyle("-fx-alignment: center; -fx-padding: 20;");

        Button guerrierButton = new Button("Choose Guerrier (Thor)");
        guerrierButton.setOnAction(e -> {
            selectedCharacter = guerrier;
            enemy1 = mage;
            enemy2 = voleur;
            showCombatScreen(primaryStage);
        });

        Button mageButton = new Button("Choose Mage (Gandalf)");
        mageButton.setOnAction(e -> {
            selectedCharacter = mage;
            enemy1 = guerrier;
            enemy2 = voleur;
            showCombatScreen(primaryStage);
        });

        Button voleurButton = new Button("Choose Voleur (Loki)");
        voleurButton.setOnAction(e -> {
            selectedCharacter = voleur;
            enemy1 = guerrier;
            enemy2 = mage;
            showCombatScreen(primaryStage);
        });

        layout.getChildren().addAll(guerrierButton, mageButton, voleurButton);

        Scene characterSelectionScene = new Scene(layout, 400, 300);
        primaryStage.setScene(characterSelectionScene);
    }

    private void showCombatScreen(Stage primaryStage) {
        VBox combatLayout = new VBox(10);

        // Create health text for each character
        HBox characterInfo = new HBox(15);
        Text selectedCharacterHealth = new Text("Health: " + selectedCharacter.pointsDeVie);
        Text enemy1Health = new Text("Health: " + enemy1.pointsDeVie);
        Text enemy2Health = new Text("Health: " + enemy2.pointsDeVie);

        characterInfo.getChildren().addAll(
                new Text(selectedCharacter.getNom() + " - "), selectedCharacterHealth,
                new Text(enemy1.getNom() + " - "), enemy1Health,
                new Text(enemy2.getNom() + " - "), enemy2Health);

        // Create buttons for attacking or using special ability
        Button attackButton = new Button("Attack");
        Button specialButton = new Button("Use Special Ability");
        Button backButton = new Button("Back to Menu");

        attackButton.setOnAction(e -> {
            if (playerTurn) {
                performAttack(selectedCharacter, selectedCharacterHealth, enemy1Health, enemy2Health);
                playerTurn = false; // Switch turn to AI
                aiTurn(selectedCharacterHealth, enemy1Health, enemy2Health);
            }
        });

        specialButton.setOnAction(e -> {
            if (playerTurn) {
                performSpecialAbility(selectedCharacter, selectedCharacterHealth, enemy1Health, enemy2Health);
                playerTurn = false; // Switch turn to AI
                aiTurn(selectedCharacterHealth, enemy1Health, enemy2Health);
            }
        });

        backButton.setOnAction(e -> start(primaryStage));

        // Adding elements to the layout
        combatLayout.getChildren().addAll(characterInfo, attackButton, specialButton, backButton);
        combatLayout.setStyle("-fx-alignment: center; -fx-padding: 20;");

        Scene combatScene = new Scene(combatLayout, 400, 300);
        primaryStage.setScene(combatScene);
    }

    private void performAttack(Personnage attacker, Text selectedCharacterHealth, Text enemy1Health,
            Text enemy2Health) {
        // Randomly select an enemy to attack
        if (Math.random() > 0.5) {
            attacker.attaquer(enemy1);
            System.out.println(attacker.getNom() + " attacks " + enemy1.getNom());
        } else {
            attacker.attaquer(enemy2);
            System.out.println(attacker.getNom() + " attacks " + enemy2.getNom());
        }
        updateHealth(selectedCharacterHealth, enemy1Health, enemy2Health);
    }

    private void performSpecialAbility(Personnage attacker, Text selectedCharacterHealth, Text enemy1Health,
            Text enemy2Health) {
        if (Math.random() > 0.5) {
            attacker.utiliserCompetence(enemy1);
            System.out.println(attacker.getNom() + " uses special ability on " + enemy1.getNom());
        } else {
            attacker.utiliserCompetence(enemy2);
            System.out.println(attacker.getNom() + " uses special ability on " + enemy2.getNom());
        }
        updateHealth(selectedCharacterHealth, enemy1Health, enemy2Health);
    }

    private void aiTurn(Text selectedCharacterHealth, Text enemy1Health, Text enemy2Health) {
        if (enemy1.estVivant()) {
            enemy1.attaquer(selectedCharacter);
            System.out.println(enemy1.getNom() + " attacks " + selectedCharacter.getNom());
        }

        if (enemy2.estVivant()) {
            enemy2.attaquer(selectedCharacter);
            System.out.println(enemy2.getNom() + " attacks " + selectedCharacter.getNom());
        }

        updateHealth(selectedCharacterHealth, enemy1Health, enemy2Health);

        playerTurn = true;

        checkGameOver();
    }

    private void updateHealth(Text selectedCharacterHealth, Text enemy1Health, Text enemy2Health) {
        selectedCharacterHealth.setText("Health: " + selectedCharacter.pointsDeVie);
        enemy1Health.setText("Health: " + enemy1.pointsDeVie);
        enemy2Health.setText("Health: " + enemy2.pointsDeVie);
    }

    private void checkGameOver() {
        if (!selectedCharacter.estVivant()) {
            System.out.println(selectedCharacter.getNom() + " has been defeated!");
            showGameOverScreen();
        } else if (!enemy1.estVivant() && !enemy2.estVivant()) {
            System.out.println("You win!");
            showVictoryScreen();
        }
    }

    private void showGameOverScreen() {
        Alert gameOverAlert = new Alert(Alert.AlertType.INFORMATION);
        gameOverAlert.setTitle("Game Over");
        gameOverAlert.setHeaderText("You have been defeated!");
        gameOverAlert.setContentText("Better luck next time.");
        gameOverAlert.showAndWait();

        resetHealth();

        Stage primaryStage = (Stage) gameOverAlert.getDialogPane().getScene().getWindow();
        start(primaryStage);
    }

    private void showVictoryScreen() {
        Alert victoryAlert = new Alert(Alert.AlertType.INFORMATION);
        victoryAlert.setTitle("Victory!");
        victoryAlert.setHeaderText("You have defeated all enemies!");
        victoryAlert.setContentText("Congratulations on your victory!");
        victoryAlert.showAndWait();

        resetHealth();

        Stage primaryStage = (Stage) victoryAlert.getDialogPane().getScene().getWindow();
        start(primaryStage);
    }

    private void resetHealth() {
        selectedCharacter.pointsDeVie = selectedCharacter.getMaxHealth();
        enemy1.pointsDeVie = enemy1.getMaxHealth();
        enemy2.pointsDeVie = enemy2.getMaxHealth();
    }

    private void addHoverEffect(Button button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
        scaleTransition.setByX(0.1);
        scaleTransition.setByY(0.1);
        button.setOnMouseEntered(e -> scaleTransition.play());
        button.setOnMouseExited(e -> scaleTransition.setRate(-1.0));
    }

    private void closeGame(Stage primaryStage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?",
                javafx.scene.control.ButtonType.YES, javafx.scene.control.ButtonType.NO);
        alert.setTitle("Exit Confirmation");
        alert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.YES) {
                primaryStage.close();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
