package GUI;

import Calculator.Integrator;
import Calculator.Threads;
import Enum.RocketParametersType;
import Exceptions.RocketCrashedException;
import GameUI.ClassicGameMode.ClassicGameManager;
import GameUI.GameManager;
import Interfaces.Observer;
import Model.DataStore;
import Model.Rocket;
import Model.RocketParameters;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import java.io.IOException;
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

    private double startVelocity = 0;
    private double startHeight = 1000;
    private double startMass = 2730.14;
    private final double step = 1;

    private Threads thread;
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
        Rocket rocket = new Rocket(startVelocity, startMass, startHeight);
        DataStore.integrator = new Integrator(rocket, step);
        draw = new GeneralDraw(chart_Phase,DataStore.integrator);


        height = new RocketParameters(RocketParametersType.HEIGHT, txtHeight, DataStore.integrator);
        mass = new RocketParameters(RocketParametersType.MASS, txtFuelAmount, DataStore.integrator);
        velocity = new RocketParameters(RocketParametersType.VELOCITY, txtSpeed, DataStore.integrator);
        thrust = new Thrust(0, txtSliderValue);

        DataStore.integrator.setThrust(thrust);

        classicGameManager = new ClassicGameManager(gameDrawingPane,mapDrawingPane,startHeight,DataStore.integrator,slider_Thrust.getMin());

// add observers to list

        observers.add(DataStore.integrator);
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


    private boolean pressed = false;

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
                    o = new Integrator( DataStore.integrator.getRocket(), step);
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

    @FXML
    private Button backBtn;

    @FXML
    void backToMenu(ActionEvent event) {
        try {
            if (pressed) {
                thread.stop();
            }
            Parent root = FXMLLoader.load(getClass().getResource("startMenu.fxml"));
            GUI.Main.stage.setScene(new Scene(root));

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
