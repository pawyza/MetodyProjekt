package GameUI.ClassicGameMode;

import Calculator.Integrator;
import GameUI.GameManager;
import Interfaces.Observer;
import Model.DataStore;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


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
    private ImageView earthView;
    private int earthFitWidth = 150;
    private int earthFitHeight = 150;
    private int earthMaxHeight = 30;
    private double lastHeightUpdateEarth;
    private double earthVerticalPosition;


    //animation
    private ImageView flameView;
    private Image[] flameAnimation;
    private int flameFitWidth = 15;
    private int flameFitHeight = 30;
    private double maxFlame;

    public ClassicGameManager(Pane gameDrawingPane, Pane mapDrawingPane, double totalHeight, Integrator integrator, double thrustMax) {
        super(gameDrawingPane);
        this.mapDrawingPane = mapDrawingPane;
        mapPaneHeight = mapDrawingPane.getPrefHeight();
        mapPaneWidth = mapDrawingPane.getPrefWidth();
        this.totalHeight = totalHeight;
        this.integrator = integrator;
        maxFlame = Math.abs(thrustMax);
        loadImages();
        loadAnimation();
        setUpGamePane();
        setUpMapPane();
        calculateRatio();
    }

    public void setIntegrator(Integrator integrator){
        this.integrator = integrator;
    }

    public void resetPane(){
        getGameDrawingPane().getChildren().clear();
        mapDrawingPane.getChildren().clear();
        setUpMapPane();
        setUpGamePane();
    }

    private void loadImages(){
        try {
            rocketImage = new Image("/resources/Images/rocket.png");
            surfaceImage = new Image("/resources/Images/surface.png");
            earthImage = new Image("/resources/Images/earth.png");
        } catch (IllegalArgumentException e) {
            System.out.println("Grafiki nie zostaly poprawnie wczytane.");
        }
    }

    private void loadAnimation(){
        flameAnimation = new Image[6];
        for (int i = 0; i<6; i++){
            flameAnimation[i] = new Image("/resources/Images/FlameAnimation/"+(i+1)+".png");
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

        flameView = new ImageView(flameAnimation[0]);
        flameView.setFitHeight(1);
        flameView.setFitWidth(1);
        flameView.setX(rocketView.getX()+rocketFitWidth/2);
        flameView.setY(rocketView.getY()+50);
        flameView.setRotate(180);

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

        getGameDrawingPane().getChildren().addAll(rocketView,flameView,earthView,surfaceView);
        flameView.toFront();
        rocketView.toFront();
    }

    private int blinkingTime = 0;
    private int timeToVanish = 15;
    private int timeToBlink = 30;
    private boolean visible = true;

    @Override
    public void update() {
        blink();
        scaleFlame();
        actualImageFlame = animate(flameView,flameAnimation,actualImageFlame);
        moveMap();
        moveEarth();
        moveSurface();
        moveRocket();
        checkRocketState();
    }

    private void scaleFlame(){
        flameView.setTranslateX(0);
        double ratio = Math.abs(DataStore.integrator.getThrust().getThrust())/maxFlame;
        double widthChange = ratio * flameFitWidth +1;
        flameView.setTranslateX(-widthChange/2);
        flameView.setFitWidth(widthChange);
        flameView.setFitHeight(ratio * flameFitHeight +1);

    }

    private int actualImageFlame=0;

    private int animate(ImageView imageView,Image[] images,int actualImage){
        actualImage++;
        if(images.length == actualImage){
            actualImage = 0;
        }
        imageView.setImage(images[actualImage]);
        return actualImage;
    }

    private void moveEarth() {
        lastHeightUpdateEarth = moveUp(lastHeightUpdateEarth,meterToPixelRatioEarth,earthView);
    }

    private void moveMap() {
        lastHeightUpdateMap= moveDown(lastHeightUpdateMap,meterToPixelRatioMap,mapRocket);
    }

    private void moveSurface() {
        if(DataStore.integrator.getRocket().getyPosition()<(totalHeight/surfaceCloseRatio)){
            lastHeightUpdateSurface = moveUp(lastHeightUpdateSurface,meterToPixelRatioSurface,surfaceView);
        }
    }

    private void moveRocket(){
        if(DataStore.integrator.getRocket().getyPosition()<(totalHeight/(surfaceCloseRatio^2))){
            moveDown(lastHeightUpdateRocket,meterToPixelRatioRocket,flameView);
            lastHeightUpdateRocket = moveDown(lastHeightUpdateRocket,meterToPixelRatioRocket,rocketView);

        }
    }

    private double moveDown(double lastHeightUpdate, double ratio, Node view){
        if(DataStore.integrator.getRocket().getyPosition()<(lastHeightUpdate - ratio)){
            int steps = (int)((Math.abs(lastHeightUpdate -DataStore.integrator.getRocket().getyPosition()))/ ratio);
            lastHeightUpdate = lastHeightUpdate-(ratio*steps);
            double newTranslate = view.getTranslateY() + steps;
            view.setTranslateY(newTranslate);

        }
        else if(DataStore.integrator.getRocket().getyPosition()>(lastHeightUpdate + ratio)){
            int steps = (int)((Math.abs(lastHeightUpdate -DataStore.integrator.getRocket().getyPosition()))/ ratio);
            lastHeightUpdate = lastHeightUpdate+(ratio*steps);
            double newTranslate = view.getTranslateY() - steps;
            view.setTranslateY(newTranslate);
        }
        return lastHeightUpdate;
    }

    private double moveUp(double lastHeightUpdate, double ratio, Node view){
        if(DataStore.integrator.getRocket().getyPosition()<(lastHeightUpdate - ratio)){
            int steps = (int)((Math.abs(lastHeightUpdate -DataStore.integrator.getRocket().getyPosition()))/ ratio);
            lastHeightUpdate = lastHeightUpdate-(ratio*steps);
            double newTranslate = view.getTranslateY() - steps;
            view.setTranslateY(newTranslate);

        }
        else if(DataStore.integrator.getRocket().getyPosition()>(lastHeightUpdate + ratio)){
            int steps = (int)((Math.abs(lastHeightUpdate -DataStore.integrator.getRocket().getyPosition()))/ ratio);
            lastHeightUpdate = lastHeightUpdate+(ratio*steps);
            double newTranslate = view.getTranslateY() + steps;
            view.setTranslateY(newTranslate);
        }
        return lastHeightUpdate;
    }

    private void lineUpLanding(){
        earthView.setTranslateY(0);
        earthView.setY(30);
        surfaceView.setTranslateY(0);
        surfaceView.setY(getGamePaneHeight()-surfaceFitHeight);
        rocketView.setTranslateY(0);
        rocketView.setY(getGamePaneHeight()-(surfaceFitHeight/2+rocketFitHeight));
        mapRocket.setTranslateY(0);
        mapRocket.setY((mapPaneHeight-(mapGroundHeight + mapRocketSize)));
        flameView.setTranslateY(0);
        flameView.setY(rocketView.getY()+45);
        flameView.setFitHeight(1);
        flameView.setFitWidth(1);
    }

    private void checkRocketState() {
        if (DataStore.integrator.getRocket().getyPosition() <= 0){
            System.out.println(integrator.isIfLandedSuccess());
            if(integrator.isIfLandedSuccess())
                mapRocket.setFill(Color.GREEN);
            else{
                mapRocket.setFill(Color.RED);
                rocketImage = new Image("/resources/Images/destroyedRocket.png");
                rocketView.setImage(rocketImage);

            }
            lineUpLanding();
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