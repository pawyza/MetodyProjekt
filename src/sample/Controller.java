package sample;

import Calculator.Integrator;
import Calculator.Threads;
import Interfaces.Observer;
import Model.Rocket;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 *
 */
public class Controller implements Initializable {



    /**
     *
     * Initialize parameters
     *
     *
     */

    private ArrayList<Observer> observers = new ArrayList<>();

    private final double startVelocity = -150;
    private final double startHeight = 200;
    private final double startMass = 2730.14;
    private final double step = 0.1;


    private Threads thread;
    private  Integrator integrator;
    private Rocket rocket;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


// initialize
        thread = new Threads();
        rocket = new Rocket(startVelocity,startMass,startHeight);
        integrator = new Integrator(rocket,step);

// add observers to list
        observers.add(integrator);






    }


    @FXML
    private Text txtSliderValue;

    @FXML
    private Slider slider_Thrust;


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
     *  Przycisk sluzacy do wywolywania metody changeState()
     */
    @FXML
    private Button btnChangeState;

    /**
     * Metoda ktorej celem jest zmiana stanu txtState
     * @param event powstajacy w wypadku nacisniecia przycisku bynChangeState
     */



    boolean pressed = false;

    @FXML
    void changeState(ActionEvent event) {

        if(pressed){
            thread.stop();
        }
        else {

         thread = new Threads();

         for(Observer o:observers){

             if(o instanceof Integrator) o = new Integrator(rocket,step);

             thread.addObserver(o);
         }
         thread.start();


        }

        pressed = !pressed;
    }


    @FXML
    void sliderOnSlide(ActionEvent event){

    }


}
