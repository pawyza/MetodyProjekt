package Interfaces;


import Exceptions.RocketCrashedException;

public interface Observable {

    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void updateObservers() throws RocketCrashedException;

}
