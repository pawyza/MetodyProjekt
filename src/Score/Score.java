package Score;


import java.io.Serializable;

public class Score  implements Serializable {
    private double score;
    private String name;
    private double thrust;
    private double time;



    public Score(String name,double time,double thrust) {
        this.score = thrust+time;
        this.name = name;
        this.thrust = thrust;
        this.time = time;
    }

    public double getScore() { return score; }

    public String getName() {
        return name;
    }

    public double getThrust() {
        return thrust;
    }

    public double getTime() {
        return time;
    }
}



