package GUI;

import Score.Score;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ScoreMenuController {

    private ObservableList<Score> scores;
    String filePath = "scores.txt";
    int number = 0;
    BufferedReader fileReader = null;

    @FXML
    private Pane mainPane;

    @FXML
    private TableView<?> tableFrajerow;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) throws IOException {

            try {
                fileReader = new BufferedReader(new FileReader(filePath));
                String numberAsString = fileReader.readLine();
                number = Integer.parseInt(numberAsString);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileReader != null) {
                    fileReader.close();
                }
            }

}

