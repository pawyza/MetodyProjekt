package GameUI;

import javafx.scene.layout.Pane;

public class GameManager {

    private Pane gameDrawingPane;
    private double gamePaneHeight;
    private double gamePaneWidth;

    public GameManager(Pane gameDrawingPane) {
        this.gameDrawingPane = gameDrawingPane;
        gamePaneHeight = gameDrawingPane.getHeight();
        gamePaneWidth = gameDrawingPane.getWidth();
    }
}
