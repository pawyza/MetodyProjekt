package Model;

import Enum.RocketParametersType;

public class Rocket {

    private double velocity;
    private double mass;
    private double yPosition; // velocity
    private double thrust;
    public Rocket(double velocity, double mass, double yPosition) {

        this.velocity = velocity;
        this.mass = mass;
        this.yPosition = yPosition;

    }

    public double getThrust() {

        return thrust;
    }

    public void setThrust(double thrust) {
        this.thrust = thrust;
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

    public double getData(RocketParametersType type) {
        switch (type) {
            case HEIGHT:
                return getyPosition();
            case MASS:
                return getMass();
            case VELOCITY:
                return getVelocity();

            default:
                return 0;
        }
    }


}
