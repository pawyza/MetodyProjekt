package GUI.startMenu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 *  Kontroler menu głównego
 */
public class StartMenuController {

    @FXML
    private Pane mainPane;

    /** Przycisk wybierający klasyczną wersję gry
     * @param event
     */
    @FXML
    void onClassicClick(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("classicGame/classicMode.fxml"));
            GUI.Main.stage.setScene(new Scene(root));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /** Przycisko wybierający rozszerzoną wersję gry
     * @param event
     */
    @FXML
    void onExpandedClick(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("extendedMode/extendedMode.fxml"));
            GUI.Main.stage.setScene(new Scene(root));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    /** Przycisk wybierający tabele wyników
     * @param event
     */
    @FXML
    void onScoreClick(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("scoreMenu/scoreMenu.fxml"));
            GUI.Main.stage.setScene(new Scene(root));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    /** Przycisk umożliwiający zmianę ustawień
     * @param event
     */
    @FXML
    void onSettingsClick(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("settingsMenu/settingsMenu.fxml"));
            GUI.Main.stage.setScene(new Scene(root));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}