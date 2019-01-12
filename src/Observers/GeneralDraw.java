package Observers;

import Calculator.ExpandedIntegrator;
import Calculator.Integrator;
import Interfaces.Observer;
import javafx.application.Platform;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

public class GeneralDraw implements Observer {

    private ScatterChart<Number, Number> chart;
    private XYChart.Series data;
    private Integrator integrator;
    private ExpandedIntegrator expintegrator;
    private boolean isextended = false;

    public GeneralDraw(ScatterChart<Number, Number> chart, Integrator integrator) {
        this.chart = chart;
        this.integrator = integrator;
        data = new XYChart.Series();
        chart.getData().addAll(data);
    }

    public GeneralDraw(ScatterChart<Number, Number> chart, ExpandedIntegrator expintegrator) {
        this.chart = chart;
        this.isextended = true;
        this.expintegrator = expintegrator;
        data = new XYChart.Series();
        chart.getData().addAll(data);
    }

    public void clearChart() {
        data.getData().clear();
    }

    @Override
    public void update() {
        if (isextended) {
            Platform.runLater(() -> {
                System.out.println("X : " + String.valueOf(expintegrator.getRocket().getxPosition()));
                data.getData().add(new XYChart.Data<>(expintegrator.getRocket().getxPosition(), expintegrator.getRocket().getyPosition()));

            });
        } else {
            Platform.runLater(() -> {

                data.getData().add(new XYChart.Data<>(integrator.getRocket().getVelocity(), integrator.getRocket().getyPosition()));

            });
        }
    }


}
