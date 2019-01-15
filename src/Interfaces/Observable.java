package Interfaces;


import Exceptions.RocketCrashedException;

/**
 * Interfejs implementujący metody dla obiektów które mogą być obserwowane
 */
public interface Observable {

    void addObserver(Observer observer);;
    void removeObserver(Observer observer);
    void updateObservers() throws RocketCrashedException;
}
