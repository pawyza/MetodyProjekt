package GameUI.ClassicGameMode;

import Calculator.Integrator;
import GameUI.GameManager;
import Interfaces.Observer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class ClassicGameManager extends GameManager implements Observer {

    private Pane mapDrawingPane;
    private double mapPaneWidth;
    private double mapPaneHeight;
    private double totalHeight;
    Integrator integrator;
    //map background
    private Color bColor = Color.valueOf("#5A6784");

    //map ground settings
    private Color gColor = Color.valueOf("#1F232D");
    private double mapGroundHeight = 15;
    private double mapGroundWidth;
    Rectangle mapGround;

    //map rocket marker setting
    private Color rColor = Color.valueOf("#FCFCFC");
    private double actualHeight;
    private double mapRocketSize = 9;
    private double mapRocketHorizontalPosition;
    private double mapRocketVerticalPosition;
    Rectangle mapRocket;


    public ClassicGameManager(Pane gameDrawingPane, Pane mapDrawingPane, double totalHeight, Integrator integrator) {
        super(gameDrawingPane);
        this.mapDrawingPane = mapDrawingPane;
        mapPaneHeight = mapDrawingPane.getPrefHeight();
        mapPaneWidth = mapDrawingPane.getPrefWidth();
        this.totalHeight = totalHeight;
        this.integrator = integrator;
        setUpMapPane();
    }

    private void setUpMapPane() {

        mapGroundWidth = mapPaneWidth;
        mapRocketHorizontalPosition = (mapPaneWidth / 2) - (mapRocketSize / 2);
        mapRocketVerticalPosition = 30;
        actualHeight = totalHeight;

        mapGround = new Rectangle(mapGroundWidth, mapGroundHeight, gColor);
        mapGround.setX(0);
        mapGround.setY(mapPaneHeight - mapGroundHeight);

        mapRocket = new Rectangle(mapRocketSize, mapRocketSize, rColor);
        mapRocket.setX(mapRocketHorizontalPosition);
        mapRocket.setY(mapRocketVerticalPosition);
        mapDrawingPane.getChildren().addAll(mapGround, mapRocket);

        meterToPixelRatio = totalHeight / ((mapPaneHeight-(mapGroundHeight + mapRocketSize + mapRocketVerticalPosition)));
        System.out.println(meterToPixelRatio);
        System.out.println((mapPaneHeight-(mapGroundHeight + mapRocketSize + mapRocketVerticalPosition)));
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
        checkRocketState();
    }

    private void moveMap() {
        int steps;
        if(Integrator.getRocket().getyPosition()<(actualHeight-meterToPixelRatio)){
            steps = (int)((Math.abs(actualHeight-Integrator.getRocket().getyPosition()))/meterToPixelRatio);
            actualHeight = actualHeight-(meterToPixelRatio*steps);
            double newTranslate = mapRocket.getTranslateY() + steps;
            mapRocket.setTranslateY(newTranslate);
            System.out.println("ruch w dol" + steps);

        }
        else if(Integrator.getRocket().getyPosition()>(actualHeight+meterToPixelRatio)){
            steps = (int)((Math.abs(actualHeight-Integrator.getRocket().getyPosition()))/meterToPixelRatio);
            actualHeight = actualHeight+(meterToPixelRatio*steps);
            double newTranslate = mapRocket.getTranslateY() - steps;
            mapRocket.setTranslateY(newTranslate);
            System.out.println("ruch w gore"+ newTranslate);
        }
    }

    private void checkRocketState() {
        if (Integrator.getRocket().getyPosition() <= 0){
            if(integrator.ifLandedSuccess)
                mapRocket.setFill(Color.GREEN);
            else{
                mapRocket.setFill(Color.RED);
            }
        }
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