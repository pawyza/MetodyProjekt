package Calculator;

import Model.Landed;
import Exceptions.OutOfFuelException;
import GameUI.GameManager;
import Interfaces.Observable;
import Interfaces.Observer;
import Exceptions.RocketCrashedException;
import Score.HighscoreManager;

import java.util.ArrayList;

public class Threads implements Runnable, Observable {

    private Landed landedRocket;

    private volatile ArrayList<Observer> observers = new ArrayList<>();
    private Thread thread;
    private volatile boolean running = false;
    //TODO PAWYZA -> Zmieniłem czas integrateTime na 1000 -> Krok całkowania też na 1s bo tak wynika z treści zadania.
    private int actualTime = 0;
    private int refreshTime = 50;
    private int integrateTime = 1000;

    public Threads() {


    }

    /**
     * Stop thread
     */

    public void stop() {
        thread.interrupt();
        running = false;
        System.out.println("Thread stopped");

    }

    /**
     * Start new thread
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

    @Override
    public void addObserver(Observer observer) {
        if (!observers.contains(observer))
            observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }


    @Override
    public void updateObservers(){

        for (Observer o : observers) {
            try {
                if(o instanceof Integrator){
                    if(((Integrator) o).isIfLandedSuccess()){
                        /*if(o instanceof GameManager(tutaj zamiast game managera możesz wrzucić nazwe klasy która będzie dostawała wynik))
                            o.update(); wtedy ten update bedzie updateował tą klasę tylko raz w momencie sukcesu przy wyladowaniu i wtedy mozesz pobrac sobie parametry z rakiety
                            przy uzyciu np. Integrator.getRocket().getMass

                            klasa ktora bedzie tworzyla rekord wygrania musi zawierac pole z integratorem (musisz tworzyc obiekt tej klasy w initialize classicGameController)
                            z integratora wyciagasz sobie wtedy calkowity czas getT()
                        */
                        System.out.println(((Integrator) o).getSuccessRocket().getRocket().toString());
                        stop();
                    }
                    // TODO: 10.01.2019 DAVID TUTAJ MASZ ZWROCONA RAKIETE Z PARAMETRAMI  
                }
                if (o instanceof HighscoreManager)
                    o.update();
                if(o instanceof GameManager)
                    o.update();

                else if(integrateTime == actualTime)
                    o.update();
            } catch (RocketCrashedException e) {
                stop();
            } catch (OutOfFuelException e) {

            }

        }
    }
}



