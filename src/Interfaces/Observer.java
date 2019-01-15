package Interfaces;

import Exceptions.OutOfFuelException;
import Exceptions.RocketCrashedException;
import Model.Rocket;

/**
 *  Interfejs implementujący metody dla obiektów które mogą obserwować
 */
public interface Observer {
    void update() throws RocketCrashedException, OutOfFuelException;


}
