package GameUI.ClassicGameMode;

import Calculator.Integrator;
import GameUI.GameManager;
import Interfaces.Observer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;



public class ClassicGameManager extends GameManager implements Observer {

    private Pane mapDrawingPane;
    private double mapPaneWidth;
    private double mapPaneHeight;
    private double totalHeight;
    private Integrator integrator;
    //map background
    private Color bColor = Color.valueOf("#5A6784");

    //map ground settings
    private Color gColor = Color.valueOf("#1F232D");
    private double mapGroundHeight = 15;
    private double mapGroundWidth;
    private Rectangle mapGround;

    //map rocket marker setting
    private Color rColor = Color.valueOf("#FCFCFC");
    private double actualHeight;
    private double mapRocketSize = 9;
    private double mapRocketHorizontalPosition;
    private double mapRocketVerticalPosition;
    private Rectangle mapRocket;

    //game rocket
    private Image rocketImage;
    private ImageView rocketView;
    private int rocketFitWidth = 77;
    private int rocketFitHeight = 62;

    //game surface
    private Image surfaceImage;
    private ImageView surfaceView;
    private int earthFitWidth = 150;
    private int earthFitHeight = 150;

    //game background
    private Image earthImage;
    private Image starImage;
    private ImageView earthView;

    public ClassicGameManager(Pane gameDrawingPane, Pane mapDrawingPane, double totalHeight, Integrator integrator) {
        super(gameDrawingPane);
        this.mapDrawingPane = mapDrawingPane;
        mapPaneHeight = mapDrawingPane.getPrefHeight();
        mapPaneWidth = mapDrawingPane.getPrefWidth();
        this.totalHeight = totalHeight;
        this.integrator = integrator;
        loadImages();
        setUpMapPane();
        setUpGamePane();
        calculateRatio();
    }

    private void loadImages(){
        try {
            rocketImage = new Image("/resources/Images/rocket.png");
            starImage = new Image("/resources/Images/star.png");
            surfaceImage = new Image("/resources/Images/surface.png");
            earthImage = new Image("/resources/Images/earth.png");
        } catch (IllegalArgumentException e) {
            System.out.println("Grafiki nie zostaly poprawnie wczytane.");
        }
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
    }

    private void calculateRatio() {
        meterToPixelRatio = totalHeight / ((mapPaneHeight-(mapGroundHeight + mapRocketSize + mapRocketVerticalPosition)));
        System.out.println(meterToPixelRatio);
        System.out.println((mapPaneHeight-(mapGroundHeight + mapRocketSize + mapRocketVerticalPosition)));
    }

    private void setUpGamePane(){
        rocketView = new ImageView(rocketImage);

        rocketView.setFitHeight(rocketFitHeight);
        rocketView.setFitWidth(rocketFitWidth);

        rocketView.setX((getGamePaneWidth()-rocketFitWidth)/2);
        rocketView.setY((getGamePaneHeight()-rocketFitHeight)/2);

        earthView = new ImageView(earthImage);

        earthView.setFitHeight(earthFitHeight);
        earthView.setFitWidth(earthFitWidth);

        earthView.setX(70);
        earthView.setY(getGamePaneHeight()-50);

        getGameDrawingPane().getChildren().addAll(rocketView,earthView);
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
            System.out.println("ruch w gore"+ steps);
        }
    }

    private void checkRocketState() {
        if (Integrator.getRocket().getyPosition() <= 0){
            mapRocket.setTranslateY(mapRocket.getTranslateY() + 1);
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