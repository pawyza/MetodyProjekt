package GUI;

import Calculator.Integrator;
import Model.DataStore;
import Score.Score;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class ScoreMenuController implements Initializable {

    private ObservableList<Score> scores;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Integrator integrator = DataStore.integrator;

        if (integrator != null) {
            //nie umiem tu pobrać metody która zwróci mi scores
            scores = FXCollections.observableArrayList(new Score("attempt 1", integrator.getT(), integrator.getSuccessRocket().getRocket().getMass() - 1000));
            tableName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tableTime.setCellValueFactory(new PropertyValueFactory<>("time"));
            tableThrust.setCellValueFactory(new PropertyValueFactory<>("thrust"));
            tableScores.setCellValueFactory(new PropertyValueFactory<>("score"));
            tableScore.setItems(scores);
        }


    }


    @FXML
    private Pane mainPane;

    @FXML
    private TableView<Score> tableScore;

    @FXML
    private TableColumn<Score, ArrayList<String>> tableName;

    @FXML
    private TableColumn<Score, ArrayList<Double>> tableTime;

    @FXML
    private TableColumn<Score, ArrayList<Double>> tableThrust;

    @FXML
    private TableColumn<Score, ArrayList<Double>> tableScores;

    @FXML
    private Button btn_Return;
    @FXML
     public void backToMenu(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("startMenu.fxml"));
            GUI.Main.stage.setScene(new Scene(root));

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


}






