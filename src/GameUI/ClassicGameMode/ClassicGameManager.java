package GameUI.ClassicGameMode;

import Calculator.Integrator;
import Exceptions.OutOfFuelException;
import Exceptions.RocketCrashedException;
import GameUI.GameManager;
import Interfaces.Observer;
import Model.Rocket;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class ClassicGameManager extends GameManager implements Observer {

    private Pane mapDrawingPane;
    private double mapPaneWidth;
    private double mapPaneHeight;
    private double totalHeigt;
    private Integrator integrator;

    //map background
    private Color bColor = Color.valueOf("#5A6784");

    //map ground settings
    private Color gColor = Color.valueOf("#1F232D");
    private double mapGroundHeight = 15;
    private double mapGroundWidth;
    Rectangle mapGround;

    //map rocket marker setting
    private Color rColor = Color.valueOf("#FCFCFC");
    private double mapRocketSize = 9;
    private double mapRocketHorizontalPosition;
    private double mapRocketVerticalPosition;
    Rectangle mapRocket;


    public ClassicGameManager(Pane gameDrawingPane, Pane mapDrawingPane, double totalHeight, Integrator integrator) {
        super(gameDrawingPane);
        this.mapDrawingPane = mapDrawingPane;
        mapPaneHeight = mapDrawingPane.getPrefHeight();
        mapPaneWidth = mapDrawingPane.getPrefWidth();
        mapGroundWidth = mapPaneWidth;
        mapRocketHorizontalPosition = (mapPaneWidth / 2) - (mapRocketSize / 2);
        mapRocketVerticalPosition = 30;
        this.totalHeigt = totalHeight;
        this.integrator = integrator;
        setUpMapPane();
    }

    private void setUpMapPane() {
        System.out.println(mapRocketHorizontalPosition);
        mapGround = new Rectangle(mapGroundWidth, mapGroundHeight, gColor);
        mapGround.setX(0);
        mapGround.setY(mapPaneHeight - mapGroundHeight);

        mapRocket = new Rectangle(mapRocketSize, mapRocketSize, rColor);
        mapRocket.setX(mapRocketHorizontalPosition);
        mapRocket.setY(mapRocketVerticalPosition);
        mapDrawingPane.getChildren().addAll(mapGround, mapRocket);

        Rocket rocket = Integrator.getRocket();

        meterToPixelRatio = totalHeigt / (mapRocketVerticalPosition + mapRocketSize);
        System.out.println(meterToPixelRatio);
    }

    private double meterToPixelRatio;

    private int blinkingTime = 0;
    private int timeToVanish = 15;
    private int timeToBlink = 30;
    private boolean visible = true;

    @Override
    public void update() {
        blink();
        moveMap();
    }

    private void moveMap() {
        System.out.println(integrator.getRocket().getyPosition());
    }

    private void blink() {

        if (!visible && blinkingTime == timeToBlink) {
            mapRocket.setFill(rColor);
            visible = true;
        } else if (visible && blinkingTime == timeToVanish) {
            mapRocket.setFill(bColor);
            visible = false;
        }
        blinkingTime++;
        if (blinkingTime > timeToBlink)
            blinkingTime = 0;


    }
}