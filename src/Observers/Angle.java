package Observers;

import Exceptions.OutOfFuelException;
import Exceptions.RocketCrashedException;
import Interfaces.Observer;
import javafx.scene.text.Text;

public class Angle implements Observer {
    //TODO SPRWADZIC CZY JEST POTRZEBNE

    private static double angle;
    private static Text text;

    public Angle(double angle) {
        this.angle = angle;
    }

    public Angle(double angle, Text text) {
        this.angle = angle;
        this.text = text;
    }

    public static double getAngle() {
        return angle;
    }

    public static void setAngle(double angle) {
        Angle.angle = angle;
    }

    public static Text getText() {
        return text;
    }

    public static void setText(Text text) {
        Angle.text = text;
    }

    @Override
    public void update() throws RocketCrashedException, OutOfFuelException {
        angle = Double.parseDouble(text.getText().replaceAll(",","."));
    }
}
