package Model;

import Calculator.Integrator;
import Enum.RocketParametersType;
import Interfaces.Observer;

import javafx.application.Platform;
import javafx.scene.text.Text;

import java.text.DecimalFormat;



public class RocketParameters implements Observer{

    private Integrator integrator;
    private Text text;
    private RocketParametersType rocketParametersType;
    private DecimalFormat df = new DecimalFormat("#.##");
    public RocketParameters(RocketParametersType rocketParametersType,Text text,Integrator integrator) {

        this.rocketParametersType = rocketParametersType;
        this.text = text;
        this.integrator = integrator;


    }


    @Override
    public void update() {
        Platform.runLater(() -> {

            text.setText(String.valueOf(df.format(integrator.getRocket().getData(rocketParametersType))));
        });
    }


}
