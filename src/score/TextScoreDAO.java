package score;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * Klasa służąca do zapisu i odczytu wyników podawanych do tabeli w ScoreMenu
 *
 * */
public class TextScoreDAO implements ScoreDAO {
    private File src;
    /**
     * @param src
     * plik do zapisu danych do tabeli wyników
     *
     */
    public TextScoreDAO(File src) {
        this.src = src;
    }
    /**
     *
     * Metoda służąca do odczytu z pliku i dodaniu jej do listy scores
     *
     */
    @Override
    public List<Score> findAll() throws FileNotFoundException {
        Scanner sc = new Scanner(src);
        Scanner lineScanner;
        List<Score> scores = new ArrayList<>();
        while (sc.hasNextLine()){
            lineScanner = new Scanner(sc.nextLine()).useDelimiter(",").useLocale(Locale.ENGLISH);
            String name = lineScanner.next();
            double time = lineScanner.nextDouble();
            double thrust = lineScanner.nextDouble();
            // dodanie wartości z odczytanego pliku do listy scores
            scores.add(new Score(name,time,thrust));
        }
        sc.close();
        return scores;
    }

    /**
     *
     * Metoda służąca do zapisu wartości do pliku scores.txt
     */
    @Override
    public void add(Score score) throws IOException {
        FileWriter fw= new FileWriter(src,true);
        String line = score.getName()+","+score.getTime()+","+score.getThrust();
        fw.write(line+"\n");
        fw.close();
    }
}
