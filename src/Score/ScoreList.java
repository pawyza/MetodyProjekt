package Score;

import java.util.ArrayList;

public class ScoreList {
    private ArrayList<String> name;
    private ArrayList<Double> time;
    private ArrayList<Double> thrust;
    private ArrayList<Double> score;

    public ScoreList(ArrayList<String> name, ArrayList<Double> time, ArrayList<Double> thrust, ArrayList<Double> score) {
        this.name = name;
        this.time = time;
        this.thrust = thrust;
        this.score = score;
    }

    public ArrayList<String> getName() {
        return name;
    }

    public void setName(ArrayList<String> name) {
        this.name = name;
    }

    public ArrayList<Double> getTime() {
        return time;
    }

    public void setTime(ArrayList<Double> time) {
        this.time = time;
    }

    public ArrayList<Double> getThrust() {
        return thrust;
    }

    public void setThrust(ArrayList<Double> thrust) {
        this.thrust = thrust;
    }

    public ArrayList<Double> getScore() {
        return score;
    }

    public void setScore(ArrayList<Double> score) {
        this.score = score;
    }
}
