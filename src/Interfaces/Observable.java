package Interfaces;

import Model.Rocket;

public interface Observable {

    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void updateObservers();

}
