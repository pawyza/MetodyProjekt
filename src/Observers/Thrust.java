package Observers;


import Interfaces.Observer;
import Exceptions.RocketCrashedException;
import javafx.scene.text.Text;



public class Thrust implements Observer {

    private static double thrust;
    private static Text text;
    public Thrust(double thrust){
        this.thrust = thrust;
    }
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

    @Override
    public void update() throws RocketCrashedException {
        thrust = Double.parseDouble(text.getText().replaceAll(",","."));
    }
}
