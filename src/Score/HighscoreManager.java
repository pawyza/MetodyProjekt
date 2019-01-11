package Score;
import Calculator.Integrator;
import Exceptions.OutOfFuelException;
import Exceptions.RocketCrashedException;
import Interfaces.Observer;
import javafx.collections.ObservableList;

import java.util.*;
import java.io.*;

import static Calculator.Integrator.getRocket;

public class HighscoreManager  implements Observer {
    // An arraylist of the type "score" we will use to work with the scores inside the class
    private ObservableList<Score> scores;

    private Integrator integrator;


    // The name of the file where the highscores will be saved
    String filePath = "scores.txt";
    int number = 0;
    FileWriter fileWriter = null;

    //Initialising an in and outputStream for working with the file
    ObjectOutputStream outputStream = null;
    ObjectInputStream inputStream = null;

    public HighscoreManager(ObservableList<Score> scores) {
        //initialising the scores-arraylist
        this.scores = scores;

    }

    public ObservableList<Score> getScores() throws IOException {
        saveScoreFile();
        sort();
        return scores;
    }

    private void sort() {
        ScoreComparator comparator = new ScoreComparator();
        Collections.sort(scores, comparator);
    }

    public void addScore(String name, double time, double thrust) throws IOException {
        saveScoreFile();


        scores.add(new Score(name, integrator.getT(), getRocket().getMass() - 1000));
        updateScoreFile();
    }

    private void updateScoreFile() {
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(filePath));
            outputStream.writeObject(scores);
        } catch (FileNotFoundException e) {
            System.out.println("[Update] FNF Error: " + e.getMessage() + ",the program will try and make a new file");
        } catch (IOException e) {
            System.out.println("[Update] IO Error: " + e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println("[Update] Error: " + e.getMessage());
            }
        }
    }


    public void saveScoreFile() throws IOException {
        try {
            fileWriter = new FileWriter(filePath);
            fileWriter.write(Integer.toString(number));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                fileWriter.close();
            }
        }


    }

    @Override
    public void update() throws RocketCrashedException, OutOfFuelException {
        integrator.getT();
        integrator.getSuccessRocket().getRocket();
    }
}
