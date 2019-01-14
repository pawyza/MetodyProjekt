package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class StartMenuController {
    @FXML
    private Pane mainPane;

    @FXML
    void onClassicClick(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("classicMode.fxml"));
            GUI.Main.stage.setScene(new Scene(root));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    void onExpandedClick(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("extendedMode.fxml"));
            GUI.Main.stage.setScene(new Scene(root));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    //TODO tutaj  odczyt pliku .txt w ktorym sa przechowywane wyniki. I zrobic z nich observable list obiektow typu score ktore wrzucam potem do tej tabeli
    @FXML
    void onScoreClick(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("scoreMenu.fxml"));
            GUI.Main.stage.setScene(new Scene(root));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    @FXML
    void onSettingsClick(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("settingsMenu.fxml"));
            GUI.Main.stage.setScene(new Scene(root));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}