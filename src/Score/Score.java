package Score;



public class Score  {




    private double score;
    private String name;
    private double thrust;
    private double time;

    public Score(String name,double time, double thrust ,double score) {
        this.score = score;
        this.name = name;
        this.thrust = thrust;
        this.time = time;
    }



    public void setScore(double score) {
        this.score = thrust+time;
    }

    public double getScore(){ return score; }

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



