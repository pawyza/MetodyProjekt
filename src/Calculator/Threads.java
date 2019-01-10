package Calculator;

import Exceptions.OutOfFuelException;
import GameUI.GameManager;
import Interfaces.Observable;
import Interfaces.Observer;
import Exceptions.RocketCrashedException;
import Observers.Thrust;

import java.util.ArrayList;

public class Threads implements Runnable, Observable {

    private volatile ArrayList<Observer> observers = new ArrayList<>();
    private Thread thread;
    private volatile boolean running = false;

    private int actualTime = 0;
    private int refreshTime = 50;
    private int integrateTime = 500;

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



