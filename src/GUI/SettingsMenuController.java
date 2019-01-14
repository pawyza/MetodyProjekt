package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;


import java.io.IOException;

public class SettingsMenuController {

    @FXML
    private Button backBtn;

    @FXML
    public void BackToMenu(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("startMenu.fxml"));
            GUI.Main.stage.setScene(new Scene(root));

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


}


