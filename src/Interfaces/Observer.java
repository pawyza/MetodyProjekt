package Interfaces;

import Exceptions.OutOfFuelException;
import Exceptions.RocketCrashedException;
import Model.Rocket;

public interface Observer {
    void update() throws RocketCrashedException, OutOfFuelException;


}
