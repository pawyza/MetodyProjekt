package GameUI.ClassicGameMode;

import Calculator.Integrator;
import GameUI.GameManager;
import Interfaces.Observer;
import javafx.scene.Node;
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
    private double lastHeightUpdateMap;
    private int mapRocketSize = 9;
    private double mapRocketVerticalPosition;
    private Rectangle mapRocket;

    //game rocket
    private Image rocketImage;
    private ImageView rocketView;
    private int rocketFitWidth = 77;
    private int rocketFitHeight = 62;
    private double lastHeightUpdateRocket;
    private double rocketVerticalPosition;

    //game surface
    private Image surfaceImage;
    private ImageView surfaceView;
    private int surfaceFitWidth = 525;
    private int surfaceFitHeight = 87;
    private int surfaceCloseRatio = 5;
    private double lastHeightUpdateSurface;

    //game background
    private Image earthImage;
    private Image starImage;
    private ImageView earthView;
    private int earthFitWidth = 150;
    private int earthFitHeight = 150;
    private int earthMaxHeight = 30;
    private double lastHeightUpdateEarth;
    private double earthVerticalPosition;

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
        mapRocketVerticalPosition = 30;
        lastHeightUpdateMap = totalHeight;

        mapGround = new Rectangle(mapGroundWidth, mapGroundHeight, gColor);
        mapGround.setX(0);
        mapGround.setY(mapPaneHeight - mapGroundHeight);

        mapRocket = new Rectangle(mapRocketSize, mapRocketSize, rColor);
        mapRocket.setX((mapPaneWidth / 2) - (mapRocketSize / 2));
        mapRocket.setY(mapRocketVerticalPosition);
        mapDrawingPane.getChildren().addAll(mapGround, mapRocket);
    }

    private double meterToPixelRatioMap;
    private double meterToPixelRatioEarth;
    private double meterToPixelRatioSurface;
    private double meterToPixelRatioRocket;

    private void calculateRatio() {
        meterToPixelRatioMap = totalHeight / ((mapPaneHeight-(mapGroundHeight + mapRocketSize + mapRocketVerticalPosition)));
        meterToPixelRatioEarth = totalHeight/(earthVerticalPosition - earthMaxHeight);
        meterToPixelRatioSurface = (totalHeight/surfaceCloseRatio)/(surfaceFitHeight);
        meterToPixelRatioRocket = (totalHeight/(surfaceCloseRatio^2))/(getGamePaneHeight()-(rocketFitHeight+surfaceFitHeight/2+rocketVerticalPosition));
    }

    private void setUpGamePane(){
        lastHeightUpdateRocket = totalHeight/(surfaceCloseRatio^2);
        rocketView = new ImageView(rocketImage);
        rocketView.setFitHeight(rocketFitHeight);
        rocketView.setFitWidth(rocketFitWidth);
        rocketVerticalPosition=(getGamePaneHeight()-rocketFitHeight)/2;
        rocketView.setX((getGamePaneWidth()-rocketFitWidth)/2);
        rocketView.setY(rocketVerticalPosition);

        lastHeightUpdateEarth = totalHeight;
        earthView = new ImageView(earthImage);
        earthView.setFitHeight(earthFitHeight);
        earthView.setFitWidth(earthFitWidth);
        earthVerticalPosition = getGamePaneHeight()-50;
        earthView.setX(70);
        earthView.setY(earthVerticalPosition);

        lastHeightUpdateSurface = totalHeight/surfaceCloseRatio;
        surfaceView = new ImageView(surfaceImage);
        surfaceView.setFitHeight(surfaceFitHeight);
        surfaceView.setFitWidth(surfaceFitWidth);
        surfaceView.setX(0);
        surfaceView.setY(getGamePaneHeight());

        getGameDrawingPane().getChildren().addAll(rocketView,earthView,surfaceView);
        rocketView.toFront();
    }

    private int blinkingTime = 0;
    private int timeToVanish = 15;
    private int timeToBlink = 30;
    private boolean visible = true;

    @Override
    public void update() {
        blink();
        moveMap();
        moveEarth();
        moveSurface();
        moveRocket();
        checkRocketState();
    }

    private void moveEarth() {
        lastHeightUpdateEarth = moveUp(lastHeightUpdateEarth,meterToPixelRatioEarth,earthView);
    }

    private void moveMap() {
        lastHeightUpdateMap= moveDown(lastHeightUpdateMap,meterToPixelRatioMap,mapRocket);
    }

    private void moveSurface() {
        if(Integrator.getRocket().getyPosition()<(totalHeight/surfaceCloseRatio)){
            lastHeightUpdateSurface = moveUp(lastHeightUpdateSurface,meterToPixelRatioSurface,surfaceView);
        }
    }

    private void moveRocket(){
        if(Integrator.getRocket().getyPosition()<(totalHeight/(surfaceCloseRatio^2))){
            lastHeightUpdateRocket = moveDown(lastHeightUpdateRocket,meterToPixelRatioRocket,rocketView);
        }
    }

    private double moveDown(double lastHeightUpdate, double ratio, Node view){
        if(Integrator.getRocket().getyPosition()<(lastHeightUpdate - ratio)){
            int steps = (int)((Math.abs(lastHeightUpdate -Integrator.getRocket().getyPosition()))/ ratio);
            lastHeightUpdate = lastHeightUpdate-(ratio*steps);
            double newTranslate = view.getTranslateY() + steps;
            view.setTranslateY(newTranslate);

        }
        else if(Integrator.getRocket().getyPosition()>(lastHeightUpdate + ratio)){
            int steps = (int)((Math.abs(lastHeightUpdate -Integrator.getRocket().getyPosition()))/ ratio);
            lastHeightUpdate = lastHeightUpdate+(ratio*steps);
            double newTranslate = view.getTranslateY() - steps;
            view.setTranslateY(newTranslate);
        }
        return lastHeightUpdate;
    }

    private double moveUp(double lastHeightUpdate, double ratio, Node view){
        if(Integrator.getRocket().getyPosition()<(lastHeightUpdate - ratio)){
            int steps = (int)((Math.abs(lastHeightUpdate -Integrator.getRocket().getyPosition()))/ ratio);
            lastHeightUpdate = lastHeightUpdate-(ratio*steps);
            double newTranslate = view.getTranslateY() - steps;
            view.setTranslateY(newTranslate);

        }
        else if(Integrator.getRocket().getyPosition()>(lastHeightUpdate + ratio)){
            int steps = (int)((Math.abs(lastHeightUpdate -Integrator.getRocket().getyPosition()))/ ratio);
            lastHeightUpdate = lastHeightUpdate+(ratio*steps);
            double newTranslate = view.getTranslateY() + steps;
            view.setTranslateY(newTranslate);
        }
        return lastHeightUpdate;
    }

    private void checkRocketState() {
        if (Integrator.getRocket().getyPosition() <= 0){
            mapRocket.setTranslateY(mapRocket.getTranslateY() + 1);
            if(integrator.isIfLandedSuccess())
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