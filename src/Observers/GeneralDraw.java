package Observers;

import Calculator.ExtendedIntegrator;
import Calculator.Integrator;
import Interfaces.Observer;
import javafx.application.Platform;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

public class GeneralDraw implements Observer {

    private ScatterChart<Number, Number> chart;
    private XYChart.Series data;
    private Integrator integrator;
    private ExtendedIntegrator expintegrator;
    private boolean isextended = false;

    /** Konstruktor dla trybu gry podstawowego
     * @param chart Wykres do aktualizacji
     * @param integrator Obiekt całkujący pozycję rakiety
     */
    public GeneralDraw(ScatterChart<Number, Number> chart, Integrator integrator) {
        this.chart = chart;
        this.integrator = integrator;
        data = new XYChart.Series();
        chart.getData().addAll(data);
    }

    /** Konstruktor dla trybu gry rozszerzonego
     * @param chart Wykres do aktualizacji
     * @param expintegrator Obiekt całkujący pozycję rakiety
     *
     * */
    public GeneralDraw(ScatterChart<Number, Number> chart, ExtendedIntegrator expintegrator) {
        this.chart = chart;
        this.isextended = true;
        this.expintegrator = expintegrator;
        data = new XYChart.Series();
        chart.getData().addAll(data);
    }

    /**
     *  Metoda usuwająca dane z wykresu
     */
    public void clearChart() {
        data.getData().clear();
    }

    /**
     *  Metoda dodająca punkt na wykresie
     */
    @Override
    public void update() {
        if (isextended) {
            Platform.runLater(() -> {
                data.getData().add(new XYChart.Data<>(expintegrator.getRocket().getxPosition(), expintegrator.getRocket().getyPosition()));

            });
        } else {
            Platform.runLater(() -> {

                data.getData().add(new XYChart.Data<>(integrator.getRocket().getVelocity(), integrator.getRocket().getyPosition()));

            });
        }
    }


}
