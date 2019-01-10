package Calculator;

import Exceptions.OutOfFuelException;
import GameUI.ClassicGameMode.ClassicGameManager;
import GameUI.GameManager;
import Interfaces.Observable;
import Interfaces.Observer;
import Exceptions.RocketCrashedException;

import java.util.ArrayList;

public class Threads implements Runnable, Observable {

    private volatile ArrayList<Observer> observersNormal = new ArrayList<>();
    private volatile ArrayList<Observer> observersGraphic = new ArrayList<>();
    private Thread thread;
    private volatile boolean running = false;

    private int actualTime;
    private int refreshTime = 20;
    private int integrationTime = 500;

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

                    updateObservers();
                    Thread.sleep(refreshTime);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();

                }

            }

    }


    @Override
    public void addNormalObserver(Observer observer) {
        if (!observersNormal.contains(observer))
            observersNormal.add(observer);
    }

    @Override
    public void removeNormalObserver(Observer observer) {
        observersNormal.remove(observer);
    }

    @Override
    public void addGraphicObserver(Observer observer) {
        if (!observersGraphic.contains(observer))
            observersGraphic.add(observer);
    }

    @Override
    public void removeGraphicObserver(Observer observer) {
        observersGraphic.remove(observer);
    }


    @Override
    public void updateObservers(){

        for (Observer o : observersGraphic) {
            try {
                o.update();
            } catch (RocketCrashedException e) {
                stop();
            } catch (OutOfFuelException e) {

            }
        }
        if (integrationTime == actualTime){
            for (Observer o : observersNormal) {
                try {
                    o.update();
                } catch (RocketCrashedException e) {
                    stop();
                } catch (OutOfFuelException e) {

                }
            }
            actualTime =0;
        }
        actualTime += refreshTime;
    }
}



