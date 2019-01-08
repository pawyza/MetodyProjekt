package Interfaces;

import Model.Rocket;
import sample.RocketCrashedException;

public interface Observable {

    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void updateObservers() throws RocketCrashedException;

}
