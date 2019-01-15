package Model;

import Enum.RocketParametersType;

/**
 *  Klasa obrazująca rakietę
 */
public class Rocket{

    private double velocity;
    private double velocityX;
    private double mass;
    private double yPosition; //height
    private double xPosition;
    private double angle;
    private double thrust;

    /** Konstruktor dla rakiety w trybie standardowym
     * @param velocity Prędkość rakiety
     * @param mass Masa rakiety
     * @param yPosition Wysokość rakiety
     */
    public Rocket(double velocity, double mass, double yPosition) {

        this.velocity = velocity;
        this.mass = mass;
        this.yPosition = yPosition;

    }

    /** Konstruktor rakiety do trybu rozszerzonego
     * @param velocity Prędkość Y rakiety
     * @param velocityX Prędkość X rakiety
     * @param mass Masa rakiety
     * @param yPosition Wysokość (Pozycja Y) rakiety
     * @param xPosition Pozycja X rakiety
     * @param angle Kąt rakiety
     * @param thrust Ciąg silnika rakiety
     */
    public Rocket(double velocity, double velocityX, double mass, double yPosition, double xPosition, double angle,double thrust) {
        this.velocity = velocity;
        this.velocityX = velocityX;
        this.mass = mass;
        this.yPosition = yPosition;
        this.xPosition = xPosition;
        this.thrust = thrust;
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

    /**
     * Metoda do pobierania poszczególnych wartości obiektu rakiety
     * @param type Typ wartości do pobrania
     * @return Pobrana wartość
     */
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
