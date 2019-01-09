package gui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class startMenuController {

    @FXML
    private Pane mainPane;

    @FXML
    void onClassicClick(ActionEvent event) {
        try {
        Parent root = FXMLLoader.load(getClass().getResource("classicMode.fxml"));
         Main.stage.setScene(new Scene(root));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    void onExpandedClick(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("extendedMode.fxml"));
            Main.stage.setScene(new Scene(root));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }



    @FXML
    void onScoreClick(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("scoreMenu.fxml"));
            Main.stage.setScene(new Scene(root));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    @FXML
    void onSettingsClick(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("settingsMenu.fxml"));
            Main.stage.setScene(new Scene(root));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
