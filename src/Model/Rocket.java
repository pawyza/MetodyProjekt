package Model;

import Interfaces.Observer;
import sample.RocketCrashedException;

public class Rocket{

    private double velocity;
    private double mass;
    private double yPosition; // velocity

    public Rocket(double velocity, double mass, double yPosition) {
        this.velocity = velocity;
        this.mass = mass;
        this.yPosition = yPosition;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getyPosition() {
        return yPosition;
    }

    public void setyPosition(double yPosition) {
        this.yPosition = yPosition;
    }


}
