package Calculator;

//TODO SPRAWDZIC CO JEST NIE TAK, NIE INICJALIZUJE OBIEKTOW, SPR. CONTROLLER, ROWNANIA !!
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

public class ExpandedIntegrator extends Integrator implements Observer {

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
        ExpandedIntegrator.thrust = thrust;
    }

    public static Angle getAngle() {
        return angle;
    }

    public static void setAngle(Angle angle) {
        ExpandedIntegrator.angle = angle;
    }

    public ExpandedIntegrator(Rocket rocket, double dt) {
        super(rocket, dt);
        this.rocket = rocket;
        this.dt = dt;


    }

    public Rocket getRocket() {
        return rocket;
    }

    public static void setRocket(Rocket rocket) {
        ExpandedIntegrator.rocket = rocket;
    }

    public Rocket expandedIntegrate(Rocket rocket, Thrust thrust, Angle angle) throws OutOfFuelException, RocketCrashedException {

        if (noFuel()) {
            this.thrust = new Thrust(0);

        } else this.thrust = thrust;


        this.angle = angle;

        positionYNext = rocket.getyPosition() + rocket.getVelocity() * Math.cos(angle.getAngle()) * dt;
        positionXNext = rocket.getxPosition() + rocket.getVelocity() * Math.sin(angle.getAngle()) * dt;
        velocityYNext = rocket.getVelocity() + (-gravity - k * (thrust.getThrust() * Math.cos(angle.getAngle()) / rocket.getMass())) * dt;
        velocityXNext = rocket.getVelocityX() + (-gravity - k * (thrust.getThrust() * Math.sin(angle.getAngle()) / rocket.getMass())) * dt;
        massNext = rocket.getMass() + thrust.getThrust() * dt;
        t = t + dt;

        rocket = new Rocket(velocityYNext, velocityXNext, massNext, positionYNext, positionXNext, angle.getAngle(), thrust.getThrust());
        this.rocket = rocket;

        if(landed()){
            System.out.println("Landed succesfully");
            successRocket = new Landed(ExpandedIntegrator.rocket);
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

    private boolean noFuel() {
        if (this.rocket.getMass() < 1000) return true;
        else return false;
    }

    private boolean crashed() {
        if (this.rocket.getyPosition() < 0) {
            return true;
        } else if (positionYNext < 0) {
            return true;
        } else
            return false;

    }

    private boolean landed() {
        if ((this.rocket.getyPosition() <= 0) && this.rocket.getVelocity() > -100)
            return true;
        else return false;
    }

    @Override
    public void update() throws RocketCrashedException, OutOfFuelException {

        expandedIntegrate(rocket, getThrustExt(), getAngle());
    }
}
