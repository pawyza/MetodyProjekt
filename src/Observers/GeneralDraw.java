package Observers;

import Calculator.Integrator;
import Interfaces.Observer;
import javafx.application.Platform;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

public class GeneralDraw implements Observer {

    private ScatterChart<Number, Number> chart;
    private XYChart.Series data;
    private Integrator integrator;

    public GeneralDraw(ScatterChart<Number, Number> chart, Integrator integrator) {
        this.chart = chart;
        this.integrator = integrator;
        data = new XYChart.Series();
        chart.getData().addAll(data);
    }
    public void clearChart(){
        data.getData().clear();
    }

    @Override
    public void update() {
        Platform.runLater(() -> {

            data.getData().add(new XYChart.Data<>(integrator.getRocket().getVelocity(),integrator.getRocket().getyPosition()));

        });
    }


}
