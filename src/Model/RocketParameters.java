package Model;

import Calculator.ExtendedIntegrator;
import Calculator.Integrator;
import Enum.RocketParametersType;
import Interfaces.Observer;
import javafx.application.Platform;
import javafx.scene.text.Text;

import java.text.DecimalFormat;


/**
 *  Klasa uzupełniająca pola interfejsu użytkownika w czasie rzeczywistym
 */
public class RocketParameters implements Observer {

    private Integrator integrator;
    private ExtendedIntegrator expintegrator;
    private Text text;
    private RocketParametersType rocketParametersType;
    private DecimalFormat df = new DecimalFormat("#.##");
    private boolean extended = false;

    /** Parametry rakiety dla gry trybu klasycznego
     * @param rocketParametersType Typ parametru
     * @param text Obserwowane pole tekstowe
     * @param integrator Obiekt klasy obliczającej pozycje rakiety
     */
    public RocketParameters(RocketParametersType rocketParametersType, Text text, Integrator integrator) {

        this.rocketParametersType = rocketParametersType;
        this.text = text;
        this.integrator = integrator;

    }

    /** Parametry rakiety dla trybu rozszerzonego
     * @param rocketParametersType Typ parametru
     * @param text Obserwowane pole tekstowe
     * @param expintegrator Obiekt klasy obliczającej pozycje rakiety
     */
    public RocketParameters(RocketParametersType rocketParametersType, Text text, ExtendedIntegrator expintegrator) {

        this.rocketParametersType = rocketParametersType;
        this.text = text;
        this.expintegrator = expintegrator;
        this.extended = true;

    }


    /**
     *  Aktualizowanie pola tekstowego przez wartość pobraną z klasy całkującej
     */
    @Override
    public void update() {
        if (extended) {
            Platform.runLater(() -> {
                text.setText(String.valueOf(df.format(expintegrator.getRocket().getData(rocketParametersType))));
            });
        } else {
            Platform.runLater(() -> {
                text.setText(String.valueOf(df.format(integrator.getRocket().getData(rocketParametersType))));
            });
        }
    }


}
