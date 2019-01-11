package GameUI;

import javafx.scene.layout.Pane;

public class GameManager {

    private Pane gameDrawingPane;
    private double gamePaneHeight;
    private double gamePaneWidth;

    public GameManager(Pane gameDrawingPane) {
        this.gameDrawingPane = gameDrawingPane;
        gamePaneHeight = gameDrawingPane.getPrefHeight();
        gamePaneWidth = gameDrawingPane.getPrefWidth();
    }

    public Pane getGameDrawingPane() {
        return gameDrawingPane;
    }

    public double getGamePaneHeight() {
        return gamePaneHeight;
    }

    public double getGamePaneWidth() {
        return gamePaneWidth;
    }
}
