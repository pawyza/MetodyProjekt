package GUI;

import Calculator.Integrator;
import Calculator.Threads;
import Enum.RocketParametersType;
import Exceptions.RocketCrashedException;
import GameUI.ClassicGameMode.ClassicGameManager;
import GameUI.GameManager;
import Interfaces.Observer;
import Model.Rocket;
import Model.RocketParameters;
import Observers.GeneralDraw;
import Observers.Thrust;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 *
 */
public class ClassicGameController implements Initializable {


    /**
     * Initialize parameters
     */
    private ArrayList<Observer> observers = new ArrayList<>();

    private final double startVelocity = 0;
    private final double startHeight = 1000;
    private final double startMass = 2730.14;
    private final double step = 1;

    private Threads thread;
    private Integrator integrator;
    private Rocket rocket;
    private RocketParameters height;
    private RocketParameters mass;
    private RocketParameters velocity;
    private Thrust thrust;
    private GeneralDraw draw;

    private ClassicGameManager classicGameManager;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtSliderValue.textProperty().bind(Bindings.format("%.2f", slider_Thrust.valueProperty()));

// initialize
        thread = new Threads();
        rocket = new Rocket(startVelocity, startMass, startHeight);
        integrator = new Integrator(rocket, step);
        draw = new GeneralDraw(chart_Phase,integrator);


        height = new RocketParameters(RocketParametersType.HEIGHT, txtHeight, integrator);
        mass = new RocketParameters(RocketParametersType.MASS, txtFuelAmount, integrator);
        velocity = new RocketParameters(RocketParametersType.VELOCITY, txtSpeed, integrator);
        thrust = new Thrust(0, txtSliderValue);

        integrator.setThrust(thrust);

        classicGameManager = new ClassicGameManager(gameDrawingPane,mapDrawingPane,startHeight,integrator);

// add observers to list

        observers.add(integrator);
        observers.add(height);
        observers.add(mass);
        observers.add(velocity);
        observers.add(thrust);
        observers.add(draw);
        observers.add(classicGameManager);

    }
    @FXML
    private ScatterChart<Number,Number> chart_Phase;

    @FXML
    private Text txtSliderValue;

    @FXML
    private Slider slider_Thrust;


    /**
     * Odpowiada za grafike obiektu rakiety w grze
     */
    @FXML
    private ImageView rocketGameObject;

    /**
     * Pole tekstowe wyswietlajace informacje o wysokosci nad ziemia podana w metrach.
     */
    @FXML
    private Text txtHeight;

    /**
     * Pole tekstowe wyswietlajace informacje o predkosci poruszania sie statku w m/s.
     */
    //uwazam ze wyswietlane powinno byc ze znakiem dodatnim w momencie zblizania do ziemi i ujemnym w wypadku oddalania(w przeciwnienstwie do obliczen)
    @FXML
    private Text txtSpeed;

    /**
     * Pole tekstowe wyswietlajace informacje o ilosci paliwa pozostalego na statku wysiwetlana w kilogramach.
     */
    @FXML
    private Text txtFuelAmount;

    /**
     * Pole tekstowe wyswietlajace stan silnika
     */
    @FXML
    private Text txtState;

    /**
     * Pole sluzace do wyswietlania animacji poruszania sie statku oraz ladowania
     */
    @FXML
    private Pane gameDrawingPane;

    /**
     * Pole sluzace do wyswietlania mapy wraz z animacja poruszania sie
     */
    @FXML
    private Pane mapDrawingPane;

    /**
     * Przycisk sluzacy do wywolywania metody changeState()
     */
    @FXML
    private Button btnChangeState;

    /**
     * Metoda ktorej celem jest zmiana stanu txtState
     *
     * @param event powstajacy w wypadku nacisniecia przycisku bynChangeState
     */


    boolean pressed = false;

    @FXML
    void changeState(ActionEvent event) {

        if (pressed) {
            thread.stop();
            draw.clearChart();
            classicGameManager.resetPane();
        } else {
            thread = new Threads();
            txtState.setText("Running");
            for (Observer o : observers) {

                if (o instanceof Integrator) {
                    o = new Integrator(rocket, step);
                    classicGameManager.setIntegrator((Integrator) o);
                }
                thread.addObserver(o);

            }

            thread.start();

        }

        pressed = !pressed;
    }


    @FXML
    void sliderOnSlide(ActionEvent event) throws RocketCrashedException {


    }


}
