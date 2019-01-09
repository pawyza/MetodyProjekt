package Interfaces;

import Exceptions.OutOfFuelException;
import Exceptions.RocketCrashedException;

public interface Observer {
    void update() throws RocketCrashedException, OutOfFuelException;

}
