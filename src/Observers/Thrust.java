package Observers;


import Interfaces.Observer;
import Exceptions.RocketCrashedException;
import javafx.scene.text.Text;

/**
 *  Klasa odpowiadająca ze wartość siły ciągu silnika
 */
public class Thrust implements Observer {

    private static double thrust;
    private static Text text;
    public Thrust(double thrust){
        this.thrust = thrust;
    }

    /** Konstruktor wartości siły ciągu silnika
     * @param thrust Wartość siły ciągu silnika
     * @param text Pole tekstowe wyświetlające warość siły ciągu silnika
     */
    public Thrust(double thrust, Text text) {
        this.thrust = thrust;
        this.text = text;
    }

    public double getThrust() {
        return thrust;
    }

    public static void setThrust(double thrust) {
        Thrust.thrust = thrust;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    /**
     * Aktualizacja siły ciągu silnika
     */
    @Override
    public void update(){
        thrust = Double.parseDouble(text.getText().replaceAll(",","."));
    }
}
