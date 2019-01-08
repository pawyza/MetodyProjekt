package Calculator;

import Interfaces.Observer;
import Model.Rocket;

import java.util.ArrayList;

public class Threads implements Runnable, Observer {

    //temp
    private Rocket rocket;
    private ArrayList<Rocket> rocketArrayList = new ArrayList<>();


    private Thread thread;
    private volatile boolean running = false;
    private boolean suspended = false;


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
        while (running) {
            // calculate velocity,mass,position

         //   integrate;





            update(rocket);

        }


    }

    @Override
    // obliczanie parametrow
    public void update(Rocket rocket) {

        rocketArrayList.add(rocket);

    }
}
