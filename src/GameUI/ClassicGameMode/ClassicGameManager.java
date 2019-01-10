package GameUI.ClassicGameMode;

import Exceptions.OutOfFuelException;
import Exceptions.RocketCrashedException;
import GameUI.GameManager;
import Interfaces.Observer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class ClassicGameManager extends GameManager implements Observer {

    private Pane mapDrawingPane;
    private double mapPaneWidth;
    private double mapPaneHeight;

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


    public ClassicGameManager(Pane gameDrawingPane,Pane mapDrawingPane) {
        super(gameDrawingPane);
        this.mapDrawingPane = mapDrawingPane;
        mapPaneHeight = mapDrawingPane.getPrefHeight();
        mapPaneWidth = mapDrawingPane.getPrefWidth();
        mapGroundWidth = mapPaneWidth;
        mapRocketHorizontalPosition = (mapPaneWidth/2)-(mapRocketSize/2);
        mapRocketVerticalPosition = 30;
        setUpMapPane();
    }

    private void setUpMapPane(){
        System.out.println(mapRocketHorizontalPosition);
        mapGround = new Rectangle(mapGroundWidth, mapGroundHeight,gColor);
        mapGround.setX(0);
        mapGround.setY(mapPaneHeight-mapGroundHeight);

        mapRocket = new Rectangle(mapRocketSize, mapRocketSize,rColor);
        mapRocket.setX(mapRocketHorizontalPosition);
        mapRocket.setY(mapRocketVerticalPosition);
        mapDrawingPane.getChildren().addAll(mapGround,mapRocket);
    }


    @Override
    public void update() {
        mapRocket.setFill(bColor);
    }
}
