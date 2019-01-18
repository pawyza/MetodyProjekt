package Calculator;
/*
    Zakładając że wysokość (H) rakiety czyli położenie X to:
     Wedlug wzoru z MN:
     h'(t) = v(t)
     tak naprawdę to jest jeszcze *cos 180 czyli 1
     h'(t) = v(t) * cos(180);
     zatem:
     h'(t) = v(t) * cos(angle)
     Euler:
     h(t)_2  = h(t)_1  + ( v(t) * cos(angle) ) * dt;

     Składowa X analogicznie:

     posX(t)_2 = posX(t)_1 + (v(t) * sin(angle) ) * dt;

     Problem jest z prędkościami bo nie wiem czy Thrusta obliczamy analogicznie ? Tzn dla Vy to bedzie:
     vy = - g - k -  ( THRUST * COS(ANGLE) ) / masa ?


 */

import Exceptions.OutOfFuelException;
import Exceptions.RocketCrashedException;
import Interfaces.Observer;
import Model.Landed;
import Model.Rocket;
import Observers.Angle;
import Observers.Thrust;

/**
 *  Klasa do obliczania pozycji rakiety dla opcji rozszerzonej
 */
public class ExtendedIntegrator extends Integrator implements Observer {

    private static Rocket rocket;
    private static Thrust thrust;
    private static Angle angle;
    private final double gravity = 1.63;
    private final double k = 636;
    private boolean ifLandedSuccess;
    private double massNext;
    private double positionYNext;
    private double positionXNext;
    private double velocityYNext;
    private double velocityXNext;

    private double dt;
    private double t;


    public static Thrust getThrustExt() {
        return thrust;
    }


    public void setThrust(Thrust thrust) {
        ExtendedIntegrator.thrust = thrust;
    }

    public static Angle getAngle() {
        return angle;
    }

    public static void setAngle(Angle angle) {
        ExtendedIntegrator.angle = angle;
    }

    public ExtendedIntegrator(Rocket rocket, double dt) {
        super(rocket, dt);
        this.rocket = rocket;
        this.dt = dt;


    }

    public Rocket getRocket() {
        return rocket;
    }

    public static void setRocket(Rocket rocket) {
        ExtendedIntegrator.rocket = rocket;
    }

    /** Metoda do obliczania pozycji [x,y] rakiety
     * @param rocket Badany obiekt rakiety
     * @param thrust Siła ciągu silnika
     * @param angle Kąt rakiety
     * @return Pozycja rakiety
     * @throws OutOfFuelException
     * @throws RocketCrashedException
     */
    public Rocket expandedIntegrate(Rocket rocket, Thrust thrust, Angle angle) throws OutOfFuelException, RocketCrashedException {

        if (noFuel()) {
            this.thrust = new Thrust(0);

        } else this.thrust = thrust;


        this.angle = angle;

        double vHalfY = rocket.getVelocity() + dt* (-gravity - k * (thrust.getThrust() * Math.cos(Math.toRadians(angle.getAngle())) / rocket.getMass()));
        positionYNext = rocket.getyPosition() + vHalfY * Math.cos(Math.toRadians(angle.getAngle())) * dt;
        double vHalfX = rocket.getVelocity() + dt*(-gravity - k * (thrust.getThrust() * Math.sin(Math.toRadians(angle.getAngle())) / rocket.getMass()));
        positionXNext = rocket.getxPosition() + vHalfX * Math.sin(Math.toRadians(angle.getAngle())) * dt;
        velocityYNext = rocket.getVelocity() + (-gravity - k * (thrust.getThrust() * Math.cos(Math.toRadians(angle.getAngle())) / rocket.getMass())) * dt;
        velocityXNext = rocket.getVelocityX() + (-gravity - k * (thrust.getThrust() * Math.sin(Math.toRadians(angle.getAngle())) / rocket.getMass())) * dt;
        massNext = rocket.getMass() + thrust.getThrust() * dt;
        t = t + dt;

        rocket = new Rocket(velocityYNext, velocityXNext, massNext, positionYNext, positionXNext, angle.getAngle(), thrust.getThrust());
        this.rocket = rocket;

        if(landed()){
            System.out.println("Landed succesfully");
            successRocket = new Landed(ExtendedIntegrator.rocket);
            ifLandedSuccess = true;
            return this.rocket;
        }
        if (!crashed()) {

            if (noFuel() && thrust.getThrust() != 0) {
                this.rocket = new Rocket(velocityYNext, velocityXNext, 1000, positionYNext, positionXNext, angle.getAngle(), thrust.getThrust());
                throw new OutOfFuelException();
            }
            return rocket;
        } else {
            this.rocket = new Rocket(0, massNext, 0);
            throw new RocketCrashedException();

        }
    }

    /** Funkcja do sprawdzania stanu paliwa rakiety
     * @return Boolean zawierający informacje o stanie paliwa
     */
    private boolean noFuel() {
        if (this.rocket.getMass() < 1000) return true;
        else return false;
    }

    /** Funkcja do sprawdzania czy rakieta została rozbita
     * @return Boolean zawierający informacje o rozbiciu rakiety
     */
    private boolean crashed() {
        if (this.rocket.getyPosition() < 0) {
            return true;
        } else if (positionYNext < 0) {
            return true;
        } else
            return false;

    }

    /** Funkcja do sprawdzania czy rakieta wylądowała
     * @return Boolean zawierający informacje o wylądowaniu rakiety
     */
    private boolean landed() {
        if ((this.rocket.getyPosition() <= 0) && this.rocket.getVelocity() > -100)
            return true;
        else return false;
    }

    /** Funkcja do aktualizowania pozycji rakiety
     * @throws RocketCrashedException
     * @throws OutOfFuelException
     */
    @Override
    public void update() throws RocketCrashedException, OutOfFuelException {

        expandedIntegrate(rocket, getThrustExt(), getAngle());
    }
}
