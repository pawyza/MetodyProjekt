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

/**
 * Klasa odpowiadajaca za animacje w trybie klasycznym dziedzicząca po Klasie GameManager
 */
public class ClassicGameManager extends GameManager implements Observer {

    /**
     * Deklaracje pol klasy
     */
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

    /**
     * Konstruktor klasy ClassicGameManager
     * @param gameDrawingPane pane wyswietlajacy animacje poruszajacego sie statku oraz tla
     * @param mapDrawingPane pane wyswietlajacy minimape pokazujacy odleglosc statku od podloza
     * @param totalHeight wysokosc od ktorej rozpoczyna sie ruch statku
     * @param integrator integrator wykonujacy obliczenia
     * @param thrustMax maksymalna wartosc przyspieszenia
     */
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

    /**
     * Metoda pozwalajaca na ustawienie pola typu Integrator
     * @param integrator integrator wykonujacy obliczenia
     */
    public void setIntegrator(Integrator integrator){
        this.integrator = integrator;
    }

    /**
     * Metoda usuwajaca wszystkie elementy z obu pane'ow oraz ponownie przygotowujaca pane'y do wyswietlania
     */
    public void resetPane(){
        getGameDrawingPane().getChildren().clear();
        mapDrawingPane.getChildren().clear();
        setUpMapPane();
        setUpGamePane();
    }

    /**
     * Metoda pobierajaca obrazy wyswietlanych elementow z katalogu /resources/Images/...
     */
    private void loadImages(){
        try {
            rocketImage = new Image("/resources/Images/rocket.png");
            surfaceImage = new Image("/resources/Images/surface.png");
            earthImage = new Image("/resources/Images/earth.png");
        } catch (IllegalArgumentException e) {
            System.out.println("Grafiki nie zostaly poprawnie wczytane.");
        }
    }

    /**
     * Metoda pobierajaca obrazy potrzebne do wygenerowani animacji plomienia z katalogu /resources/Images/FlameAnimation/...
     */
    private void loadAnimation(){
        flameAnimation = new Image[6];
        for (int i = 0; i<6; i++){
            flameAnimation[i] = new Image("/resources/Images/FlameAnimation/"+(i+1)+".png");
        }
    }

    /**
     * Metoda przygotowujaca pane minimapy
     */
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

    /**
     * Metoda sluzaca do kalkulowania stosunkow metr/piksel dla kazdego z pol meterToPixel obiektu
     */
    private void calculateRatio() {
        meterToPixelRatioMap = totalHeight / ((mapPaneHeight-(mapGroundHeight + mapRocketSize + mapRocketVerticalPosition)));
        meterToPixelRatioEarth = totalHeight/(earthVerticalPosition - earthMaxHeight);
        meterToPixelRatioSurface = (totalHeight/surfaceCloseRatio)/(surfaceFitHeight);
        meterToPixelRatioRocket = (totalHeight/(surfaceCloseRatio^2))/(getGamePaneHeight()-(rocketFitHeight+surfaceFitHeight/2+rocketVerticalPosition));
    }

    /**
     * Metoda przygotowujaca pane wyswietlajacy animacje rozgrywki
     */
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

    /**
     * Metoda wywolujaca metody odpowiedzialne za zmiane wyglodu oraz polozenia elementow graficznych
     */
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

    /**
     * Metoda odpowiadajaca za skalowanie plomienia w zaleznosci od aktualnego ciagu
     */
    private void scaleFlame(){
        flameView.setTranslateX(0);
        double ratio = Math.abs(DataStore.integrator.getThrust().getThrust())/maxFlame;
        double widthChange = ratio * flameFitWidth +1;
        flameView.setTranslateX(-widthChange/2);
        flameView.setFitWidth(widthChange);
        flameView.setFitHeight(ratio * flameFitHeight +1);

    }

    private int actualImageFlame=0;

    /**
     * Metoda generujaca animacje obiektu na podstawie dostarczonych obrazow
     * @param imageView pole odpowiadajace za wyswietlanie animacji, do niego ladowane sa obrazy
     * @param images lista tablicowa przygotowanych do animacji obrzow
     * @param actualImage ostatnio wyswietlony obraz
     * @return zwraca numer najnowszego wyswietlanego obrazu
     */
    private int animate(ImageView imageView,Image[] images,int actualImage){
        actualImage++;
        if(images.length == actualImage){
            actualImage = 0;
        }
        imageView.setImage(images[actualImage]);
        return actualImage;
    }

    /**
     * Metoda wywolujca metode moveUp dla odpowiednio zadanych parametrow dla animacji ziemi. Odpowiada za ruch ziemi
     */
    private void moveEarth() {
        lastHeightUpdateEarth = moveUp(lastHeightUpdateEarth,meterToPixelRatioEarth,earthView);
    }

    /**
     * Metoda wywolujca metode moveDown dla odpowiednio zadanych parametrow dla animacji znacznika na minimapie. Odpowiada za ruch na minimapie
     */
    private void moveMap() {
        lastHeightUpdateMap= moveDown(lastHeightUpdateMap,meterToPixelRatioMap,mapRocket);
    }

    /**
     * Metoda wywolujca metode moveUp dla odpowiednio zadanych parametrow dla animacji podloza. Odpowiada za przyblizanie powierzchni w koncowej fazie lotu
     */
    private void moveSurface() {
        if(DataStore.integrator.getRocket().getyPosition()<(totalHeight/surfaceCloseRatio)){
            lastHeightUpdateSurface = moveUp(lastHeightUpdateSurface,meterToPixelRatioSurface,surfaceView);
        }
    }

    /**
     * Metoda wywolujca metode moveDown dla odpowiednio zadanych parametrow dla animacji rakiety oraz jej plomieni. Odpowiada za ruch rakiety oraz plomieni w koncowej fazie lotu
     */
    private void moveRocket(){
        if(DataStore.integrator.getRocket().getyPosition()<(totalHeight/(surfaceCloseRatio^2))){
            moveDown(lastHeightUpdateRocket,meterToPixelRatioRocket,flameView);
            lastHeightUpdateRocket = moveDown(lastHeightUpdateRocket,meterToPixelRatioRocket,rocketView);

        }
    }

    /**
     * Metoda wywolujaca przesuniecie odpowiedniego obiektu w dol o zadana wartosc
     * @param lastHeightUpdate ostatnia wartosc, ktora spowodowala przesuniecie obiektu
     * @param ratio stosunek metr/piksel dla odpowiedniego obiektu
     * @param view node do przesuwanego obiektu
     * @return zwraca najnowsza wartosc, ktora spowodowala przesuniecie obiektu
     */
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
    /**
     * Metoda wywolujaca przesuniecie odpowiedniego obiektu w gore o zadana wartosc
     * @param lastHeightUpdate ostatnia wartosc, ktora spowodowala przesuniecie obiektu
     * @param ratio stosunek metr/piksel dla odpowiedniego obiektu
     * @param view node do przesuwanego obiektu
     * @return zwraca najnowsza wartosc, ktora spowodowala przesuniecie obiektu
     *      */
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

    /**
     * Metoda ustawiajca ostatnia wyswietlana klatke, dba o poprawne koncowe ulozenie elementow
     */
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

    /**
     * Metoda zmieniajaca wyglad rakiety oraz jej znacznik na minimapie w momencie poprawnego wyladowania lub rozbicia
     */
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

    /**
     * Metoda odpowiadajaca za miganie znacznika
     */
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