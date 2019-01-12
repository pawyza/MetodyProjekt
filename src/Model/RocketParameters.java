package Model;

import Calculator.ExpandedIntegrator;
import Calculator.Integrator;
import Enum.RocketParametersType;
import Interfaces.Observer;
import javafx.application.Platform;
import javafx.scene.text.Text;

import java.text.DecimalFormat;


public class RocketParameters implements Observer {

    private Integrator integrator;
    private ExpandedIntegrator expintegrator;
    private Text text;
    private RocketParametersType rocketParametersType;
    private DecimalFormat df = new DecimalFormat("#.##");
    private boolean extended = false;

    public RocketParameters(RocketParametersType rocketParametersType, Text text, Integrator integrator) {

        this.rocketParametersType = rocketParametersType;
        this.text = text;
        this.integrator = integrator;

    }
    public RocketParameters(RocketParametersType rocketParametersType, Text text, ExpandedIntegrator expintegrator) {

        this.rocketParametersType = rocketParametersType;
        this.text = text;
        this.expintegrator = expintegrator;
        this.extended = true;

    }


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
