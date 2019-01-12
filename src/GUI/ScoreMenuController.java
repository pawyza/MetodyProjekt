package GUI;

import Calculator.Integrator;
import Score.Score;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static Calculator.Integrator.getRocket;


public class ScoreMenuController implements Initializable {

    private ObservableList<Score> scores;
    private Integrator integrator;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scores= FXCollections.observableArrayList(new Score((new Score(Score.getName(),integrator.getT(),getRocket().getThrust(),Score.getScore())); //nie umiem tu pobrać metody która zwróci mi scores
        tableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        tableThrust.setCellValueFactory(new PropertyValueFactory<>("thrust"));
        tableScores.setCellValueFactory(new PropertyValueFactory<>("score"));
        tableScore.setItems(scores);


    }
// nie wiem czy start potrzeby bo mamy przecież w startMenuController przeniesienia do okna po kliknięciu

    public void start(Stage primaryStage) throws IOException {
        GUI.Main.stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("scoreMenu.fxml"));
        primaryStage.setTitle("Score Menu");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
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






}






