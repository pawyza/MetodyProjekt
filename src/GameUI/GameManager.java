package GameUI;

import javafx.scene.layout.Pane;

public class GameManager {

    private Pane gameDrawingPane;
    private double gamePaneHeight;
    private double gamePaneWidth;

    /**
     * Konstruktor klasy GameManager
     * @param gameDrawingPane  pane wyswietlajacy animacje poruszajacego sie statku oraz tla
     */
    public GameManager(Pane gameDrawingPane) {
        this.gameDrawingPane = gameDrawingPane;
        gamePaneHeight = gameDrawingPane.getPrefHeight();
        gamePaneWidth = gameDrawingPane.getPrefWidth();
    }

    /**
     * @return pole gameDrawingPane pedace panem wyswietlajacym animacje gry
     */
    public Pane getGameDrawingPane() {
        return gameDrawingPane;
    }

    /**
     * @return wysokosc gameDrawingPane
     */
    public double getGamePaneHeight() {
        return gamePaneHeight;
    }

    /**
     * @return szerokosc gameDrawingPane
     */
    public double getGamePaneWidth() {
        return gamePaneWidth;
    }
}
