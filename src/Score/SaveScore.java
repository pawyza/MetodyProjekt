package Score;

import Calculator.Integrator;
import Interfaces.Observer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class SaveScore implements Observer {

    private ArrayList<String> Name= new ArrayList<>();
    private ArrayList<Double> Time= new ArrayList<>();
    private ArrayList<Double> Thrust= new ArrayList<>();
    private ArrayList<Double> Score= new ArrayList<>();

    private Integrator integrator;



    public ArrayList<String> getName() { return Name; }

    public ArrayList<Double> getTime() { return Time; }

    public ArrayList<Double> getThrust() { return Thrust; }

    public ArrayList<Double> getScore() { return Score; }


    public void save(String pathname) {

        File file = new File(pathname);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            for (int i = 0; i < Time.size(); i++) {
                printWriter.printf("%.4f \t", Name.get(i));
                printWriter.printf("%.4f \t", Time.get(i));
                printWriter.printf("%.4f \t", Thrust.get(i));
                printWriter.printf("%.4f %n", Score.get(i));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update()  {
        integrator.getRocket().getThrust();
    }
}
