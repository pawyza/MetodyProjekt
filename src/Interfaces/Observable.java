package Interfaces;


import Exceptions.RocketCrashedException;

public interface Observable {

    void addNormalObserver(Observer observer);
    void addGraphicObserver(Observer observer);
    void removeNormalObserver(Observer observer);
    void removeGraphicObserver(Observer observer);
    void updateObservers() throws RocketCrashedException;
}
