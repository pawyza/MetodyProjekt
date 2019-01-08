package Calculator;

import Interfaces.Observable;
import Interfaces.Observer;

import java.util.ArrayList;

public class Threads implements Runnable, Observable {

    //temp


    private volatile ArrayList<Observer> observers = new ArrayList<>();
    private Thread thread;
    private volatile boolean running = false;


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
                    Thread.sleep(500);

                } catch (InterruptedException e) {
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
    public void updateObservers() {

        for (Observer o : observers) {
            o.update();
        }
    }
}



