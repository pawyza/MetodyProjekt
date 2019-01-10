package GUI;

import Calculator.ExpandedIntegrator;
import Calculator.Threads;
import Interfaces.Observer;
import Model.Rocket;
import Model.RocketParameters;
import Observers.Angle;
import Observers.GeneralDraw;
import Observers.Thrust;
import Enum.RocketParametersType;
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

public class ExtendedModeController implements Initializable {

    private ArrayList<Observer> observers = new ArrayList<>();

    private final double startYVelocity = -1500;
    private final double startXVelocity = 0;
    private final double startXPosition = 0;
    private final double startYPosition = 50000;
    private final double startMass = 1200.14;
    private final double startAngle = 0;
    private final double step = 0.1;

    private Threads thread;
    private ExpandedIntegrator expandedIntegrator;
    private Rocket rocket;
    private Thrust thrust;
    private Thrust angle;
    private GeneralDraw draw;

    private RocketParameters yPosition;
    private RocketParameters xPosition;
    private RocketParameters mass;
    private RocketParameters xVelocity;
    private RocketParameters yVelocity;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    txt_ThrustY.textProperty().bind(Bindings.format("%.2f",slider_Thrust.valueProperty()));
    txt_ThrustX.textProperty().bind(Bindings.format("%.2f",slider_Angle.valueProperty()));

    rocket = new Rocket(startYVelocity,startXVelocity,startMass,startYPosition,startXPosition,startAngle);
        System.out.println(rocket.toString());
    expandedIntegrator = new ExpandedIntegrator(rocket,step);
    thread = new Threads();

    thrust = new Thrust(0,txt_ThrustY);
    angle = new Thrust(0,txt_ThrustX);

    draw = new GeneralDraw(chart_RocketPosition,expandedIntegrator);


    mass = new RocketParameters(RocketParametersType.MASS,txt_Mass,expandedIntegrator);
    yPosition = new RocketParameters(RocketParametersType.HEIGHT,txt_PositionY,expandedIntegrator);
    yVelocity = new RocketParameters(RocketParametersType.VELOCITY,txt_VelocityY,expandedIntegrator);
    xPosition = new RocketParameters(RocketParametersType.XPOSITION,txt_PositionX,expandedIntegrator);
    xVelocity = new RocketParameters(RocketParametersType.XVELOCITY,txt_VelocityX,expandedIntegrator);

    observers.add(expandedIntegrator);
    observers.add(mass);
    observers.add(yPosition);
    observers.add(xPosition);
    observers.add(xVelocity);
    observers.add(yVelocity);
    observers.add(draw);

    }
    @FXML
    private Pane mainPane;

    @FXML
    private Button btnStart;

    @FXML
    private Button btnReturn;

    @FXML
    private Text txt_PositionX;

    @FXML
    private Text txt_PositionY;

    @FXML
    private Text txt_VelocityX;

    @FXML
    private Text txt_VelocityY;

    @FXML
    private Text txt_Mass;

    @FXML
    private Text txt_ThrustX;

    @FXML
    private Text txt_ThrustY;
    @FXML
    private Slider slider_Angle;

    @FXML
    private Slider slider_Thrust;

    @FXML
    private ScatterChart<Number, Number> chart_RocketPosition;

    @FXML
    void btnReturn_OnAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("startMenu.fxml"));
            gui.Main.stage.setScene(new Scene(root));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    void btnStart_OnAction(ActionEvent event) {
    for(Observer o: observers){
        thread.addObserver(o);
    }
        thread.start();
    }


}
