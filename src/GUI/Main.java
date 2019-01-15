package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.stage = primaryStage;
        stage.setResizable(false);
        stage.getIcons().add(new Image("/resources/Images/star.png"));
        Parent root = FXMLLoader.load(getClass().getResource("startMenu/startMenu.fxml"));
        primaryStage.setTitle("Moon Landing Simulator");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
