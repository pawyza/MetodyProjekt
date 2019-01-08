package Interfaces;


import sample.RocketCrashedException;

public interface Observer {
    void update() throws RocketCrashedException;
}
