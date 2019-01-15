package Observers;

import Exceptions.OutOfFuelException;
import Exceptions.RocketCrashedException;
import Interfaces.Observer;
import javafx.scene.text.Text;

/**
 *  Klasa obserwująca kąt rakiety
 */
public class Angle implements Observer {

    private static double angle;
    private static Text text;

    public Angle(double angle) {
        this.angle = angle;
    }

    /** Konstruktor klasy kąt
     * @param angle Kąt rakiety
     * @param text Pole tekstowe w które ma zostać wpisana wartość kąt
     */
    public Angle(double angle, Text text) {
        this.angle = angle;
        this.text = text;
    }

    public  double getAngle() {
        return angle;
    }

    public  void setAngle(double angle) {
        Angle.angle = angle;
    }

    public static Text getText() {
        return text;
    }

    public static void setText(Text text) {
        Angle.text = text;
    }

    /**
     * Aktualizowania pola tekstowego
     */
    @Override
    public void update(){
        angle = Double.parseDouble(text.getText().replaceAll(",","."));
    }
}
