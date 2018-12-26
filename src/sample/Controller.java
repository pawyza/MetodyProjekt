package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 *
 */
public class Controller {

    /**
     * Pole tekstowe wyswietlajace informacje o wysokosci nad ziemia podana w metrach.
     */
    @FXML
    private Text txtHeight;

    /**
     * Pole tekstowe wyswietlajace informacje o predkosci poruszania sie statku w m/s.
     */
    //uwazam ze wyswietlane powinno byc ze znakiem dodatnim w momencie zblizania do ziemi i ujemnym w wypadku oddalania(w przeciwnienstwie do obliczen)
    @FXML
    private Text txtSpeed;

    /**
     * Pole tekstowe wyswietlajace informacje o ilosci paliwa pozostalego na statku wysiwetlana w kilogramach.
     */
    @FXML
    private Text txtFuelAmount;

    /**
     * Pole tekstowe wyswietlajace stan silnika
     */
    @FXML
    private Text txtState;

    /**
     * Pole sluzace do wyswietlania animacji poruszania sie statku oraz ladowania
     */
    @FXML
    private Pane gameDrawingPane;

    /**
     * Pole sluzace do wyswietlania mapy wraz z animacja poruszania sie
     */
    @FXML
    private Pane mapDrawingPane;

    /**
     *  Przycisk sluzacy do wywolywania metody changeState()
     */
    @FXML
    private Button btnChangeState;

    /**
     * Metoda ktorej celem jest zmiana stanu txtState
     * @param event powstajacy w wypadku nacisniecia przycisku bynChangeState
     */
    @FXML
    void changeState(ActionEvent event) {

    }

}
