package GUI;

import Calculator.ExtendedIntegrator;
import Calculator.Threads;
import Enum.RocketParametersType;
import Interfaces.Observer;
import Model.Rocket;
import Model.RocketParameters;
import Observers.Angle;
import Observers.GeneralDraw;
import Observers.Thrust;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 *  Kontroler dla rozszerzonego trybu gry
 */
public class ExtendedModeController implements Initializable {

    /**
     *  Deklarowanie pól
     */
    private ArrayList<Observer> observers = new ArrayList<>();

    private final double startYVelocity = -150;
    private final double startXVelocity = 0;
    private final double startXPosition = 0;
    private final double startYPosition = 50000;
    private final double startMass = 2130.14;
    private final double startAngle = 0;
    private final double step = 0.1;

    private Threads thread;
    private ExtendedIntegrator extendedIntegrator;
    private Rocket rocket;
    private Thrust thrust;
    private Angle angle;
    private GeneralDraw draw;

    private RocketParameters yPosition;
    private RocketParameters xPosition;
    private RocketParameters mass;
    private RocketParameters xVelocity;
    private RocketParameters yVelocity;

    /** Inicjalizacja pól
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        txt_Thrust.textProperty().bind(Bindings.format("%.2f", slider_Thrust.valueProperty()));
        txt_Angle.textProperty().bind(Bindings.format("%.2f", slider_Angle.valueProperty()));

        rocket = new Rocket(startYVelocity, startXVelocity, startMass, startYPosition, startXPosition, startAngle, 0);
        System.out.println(rocket.toString());


        extendedIntegrator = new ExtendedIntegrator(rocket, step);

        thread = new Threads();

        thrust = new Thrust(0, txt_Thrust);
        angle = new Angle(startAngle, txt_Angle);

        extendedIntegrator.setThrust(thrust);
        extendedIntegrator.setAngle(angle);

        draw = new GeneralDraw(chart_RocketPosition, extendedIntegrator);
        mass = new RocketParameters(RocketParametersType.MASS, txt_Mass, extendedIntegrator);
        yPosition = new RocketParameters(RocketParametersType.HEIGHT, txt_PositionY, extendedIntegrator);
        yVelocity = new RocketParameters(RocketParametersType.VELOCITY, txt_VelocityY, extendedIntegrator);
        xPosition = new RocketParameters(RocketParametersType.XPOSITION, txt_PositionX, extendedIntegrator);
        xVelocity = new RocketParameters(RocketParametersType.XVELOCITY, txt_VelocityX, extendedIntegrator);

        System.out.println(thrust.getThrust());
        System.out.println(angle.getAngle());

        observers.add(extendedIntegrator);
        observers.add(mass);
        observers.add(yPosition);
        observers.add(xPosition);
        observers.add(xVelocity);
        observers.add(yVelocity);
        observers.add(thrust);
        observers.add(angle);
        observers.add(draw);

    }

    @FXML
    private Pane mainPane;

    /**
     *  Przycisk startu
     */
    @FXML
    private Button btnStart;

    /**
     *  Przycisk powrotu
     */
    @FXML
    private Button btnReturn;

    /**
     *  Pole tekstowe wyświetlające pozycje X rakiety
     */
    @FXML
    private Text txt_PositionX;

    /**
     *  Pole tekstowe wyświetlające pozycję Y rakiety
     */
    @FXML
    private Text txt_PositionY;

    /**
     * Pole tekstowe wyświetlające prędkość X rakiety
     */
    @FXML
    private Text txt_VelocityX;

    /**
     * Pole tekstowe wyświetlające prędkość Y rakiety
     */
    @FXML
    private Text txt_VelocityY;

    /**
     * Pole tekstowe wyświetlające mase rakiety
     */
    @FXML
    private Text txt_Mass;

    /**
     * Pole tekstowe wyświetlające kąt rakiety
     */
    @FXML
    private Text txt_Angle;

    /**
     *Pole tekstowe wyświetlające ciąg silnika rakiety
     */
    @FXML
    private Text txt_Thrust;
    /**
     *  Slider odpowiadający za kąt rakiety
     */
    @FXML
    private Slider slider_Angle;

    /**
     *  Slider odpowiadający za wartość ciągu silnika rakiety
     */
    @FXML
    private Slider slider_Thrust;

    /**
     *  Wykres obrazujący aktualną pozycję rakiety
     */
    @FXML
    private ScatterChart<Number, Number> chart_RocketPosition;

    private boolean pressed = false;

    /** Metoda rozpoczynająca lub stopująca grę
     * @param event
     */
    @FXML
    void btnStart_OnAction(ActionEvent event) {
        if (pressed) {
            thread.stop();
            draw.clearChart();
        } else {
            thread = new Threads();
            for (Observer o : observers) {
                if (o instanceof ExtendedIntegrator) o = new ExtendedIntegrator(rocket, step);

                thread.addObserver(o);
            }
            thread.start();
        }
        pressed = !pressed;
    }

    /** Przycisk powrotu do menu
     * @param event
     */
    @FXML
    void btnReturn_OnAction(ActionEvent event) {
        try {
            if(pressed){
                thread.stop();
            }
            Parent root = FXMLLoader.load(getClass().getResource("startMenu.fxml"));
            GUI.Main.stage.setScene(new Scene(root));

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }



}
