package Score;

import java.util.ArrayList;

public class ScoreList {
    private ArrayList<String> nameList;
    private ArrayList<Double> timeList;
    private ArrayList<Double> thrustList;
    private ArrayList<Double> scoreList;

    public ScoreList(ArrayList<String> nameList, ArrayList<Double> timeList, ArrayList<Double> thrustList, ArrayList<Double> scoreList) {
        this.nameList = nameList;
        this.timeList = timeList;
        this.thrustList = thrustList;
        this.scoreList = scoreList;
    }

    public ArrayList<String> getNameList() {
        return nameList;
    }

    public void setNameList(ArrayList<String> nameList) {
        this.nameList = nameList;
    }

    public ArrayList<Double> getTimeList() {
        return timeList;
    }

    public void setTimeList(ArrayList<Double> timeList) {
        this.timeList = timeList;
    }

    public ArrayList<Double> getThrustList() {
        return thrustList;
    }

    public void setThrustList(ArrayList<Double> thrustList) {
        this.thrustList = thrustList;
    }

    public ArrayList<Double> getScoreList() {
        return scoreList;
    }

    public void setScoreList(ArrayList<Double> scoreList) {
        this.scoreList = scoreList;
    }
}
