package score;

/**
 * Klasa score ułatwia pobieranie wartości do ScoreMenu w celu wstawienia wartości do tabeli
 * @param score
 *
 * @return Map of json file
 */
public class Score  {

    /**
     *
     * @param score
     * @param name
     * @param thrust
     * @param time
     *
     *
     */


    private double score;
    private String name;
    private double thrust;
    private double time;

    public Score(String name,double time, double thrust) {
        this.score = thrust+time;
        this.name = name;
        this.thrust = thrust;
        this.time = time;
    }



    /**
     * Funkcja zwracająca wartość score
     * @return score
     */
    public double getScore(){ return score; }

    /**
     * Funkcja zwracająca wartość name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     *Funkcja zwracająca wartość thrust
     * @return thrust
     */
    public double getThrust() {
        return thrust;
    }
    /**
     *Funkcja zwracająca wartość time
     * @return time
     */
    public double getTime() {
        return time;
    }

}



