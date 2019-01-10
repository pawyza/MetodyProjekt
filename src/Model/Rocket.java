package Model;

import Enum.RocketParametersType;

public class Rocket{

    private double velocity;
    private double velocityX;
    private double mass;
    private double yPosition; //height
    private double xPosition;
    private double angle;
    private double thrust;

    public Rocket(double velocity, double mass, double yPosition) {

        this.velocity = velocity;
        this.mass = mass;
        this.yPosition = yPosition;

    }

    public Rocket(double velocity, double velocityX, double mass, double yPosition, double xPosition, double angle) {
        this.velocity = velocity;
        this.velocityX = velocityX;
        this.mass = mass;
        this.yPosition = yPosition;
        this.xPosition = xPosition;

        this.angle = angle;
    }

    public double getxPosition() {
        return xPosition;
    }

    public void setxPosition(double xPosition) {
        this.xPosition = xPosition;
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

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getData(RocketParametersType type) {
        switch (type) {
            case HEIGHT:
                return getyPosition();
            case MASS:
                return getMass();
            case VELOCITY:
                return getVelocity();
            case XPOSITION:
                return getxPosition();
            case XVELOCITY:
                return getVelocityX();
            default:
                return 0;
        }
    }

    @Override
    public String toString() {
        return "Rocket{" +
                "velocity=" + velocity +
                ", velocityX=" + velocityX +
                ", mass=" + mass +
                ", yPosition=" + yPosition +
                ", xPosition=" + xPosition +
                ", angle=" + angle +
                ", thrust=" + thrust +
                '}';
    }
}
