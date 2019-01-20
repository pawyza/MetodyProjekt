package Calculator;

import Exceptions.OutOfFuelException;
import GameUI.GameManager;
import Interfaces.Observable;
import Interfaces.Observer;
import Exceptions.RocketCrashedException;
import score.Score;
import score.ScoreDAO;
import score.TextScoreDAO;

import java.io.File;
import java.util.ArrayList;

/**
 *  Klasa odpowiadająca za wprowadzanie wątków do programu
 */
public class Threads implements Runnable, Observable {

    private volatile ArrayList<Observer> observers = new ArrayList<>();
    private ScoreDAO scoreDAO = new TextScoreDAO(new File("resources/scores.txt"));
    private Thread thread;
    private volatile boolean running = false;
    private int actualTime = 0;
    private int refreshTime = 100; //50
    private int integrateTime = 1000;
    private int landingNo = 1;

    public Threads() {

    }

    /**
     * Zatrzymaj wątek
     */

    public void stop() {
        thread.interrupt();
        running = false;
        System.out.println("Thread stopped");

    }

    /**
     * Rozpocznij nowy wątek
     */
    public void start() {
        System.out.println("Starting new thread");
        if (thread == null) {
            thread = new Thread(this);
            thread.setDaemon(true);
            thread.start();

        }
    }


    @Override
    public void run() {
        // update parametrów
        running = true;

            while (running) {

                try {
                    if(actualTime == integrateTime)
                        actualTime = 0;
                    actualTime += refreshTime;
                    updateObservers();

                    Thread.sleep(refreshTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();

                }

            }

    }

    /** Dodaj obserwatora do listy
     * @param observer obserwator do dodania
     */
    @Override
    public void addObserver(Observer observer) {
        if (!observers.contains(observer))
            observers.add(observer);
    }

    /** Usuń obserwatora z listy
     * @param observer obserwator do usunięcia
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }


    /**
     *  Metoda do aktualizowania obserwatorów
     */
    @Override
    public void updateObservers(){

        for (Observer o : observers) {
            try {
                if(o instanceof Integrator){
                    if(((Integrator) o).isIfLandedSuccess()){
                        Integrator i = (Integrator) o;
                        double time = i.getT();
                        double thrust = i.getSuccessRocket().getRocket().getMass()-1000;
                        Score score = new Score("landing no "+ landingNo++ ,time,thrust);
                        scoreDAO.add(score);
                        System.out.println(((Integrator) o).getSuccessRocket().getRocket().toString());
                        stop();
                    }

                }
                if(o instanceof GameManager)
                    o.update();

                else if(integrateTime == actualTime)
                    o.update();
            } catch (RocketCrashedException e) {
                stop();
            } catch (OutOfFuelException e) {

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}



