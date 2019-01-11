package Calculator;

import Model.Landed;
import Exceptions.OutOfFuelException;
import Exceptions.RocketCrashedException;
import Interfaces.Observer;
import Model.Rocket;
import Observers.Thrust;

public class Integrator implements Observer {

    private static Rocket rocket;
    private static Thrust thrust;
    public Landed successRocket;

    private boolean ifLandedSuccess;

    private final double gravity = 1.63;
    private final double k = 636;
    private double dt;
    private double t;

    private double massNext;
    private double heightNext;
    private double velocityNext;

    public Integrator(Rocket rocket, double dt) {
        this.rocket = rocket;
        this.dt = dt;
        System.out.println("Created integrator");
    }

    /**
     * Integrete position of rocket
     *
     * @param rocket
     * @param thrust Thrust of engine
     * @return Position of rocket after step time
     */
    public Rocket integrate(Rocket rocket, Thrust thrust) throws RocketCrashedException, OutOfFuelException {

        if (noFuel()) {
            this.thrust = new Thrust(0);
        } else this.thrust = thrust;


        heightNext = rocket.getyPosition() + rocket.getVelocity() * dt;
        velocityNext = rocket.getVelocity() + (-gravity - k * (thrust.getThrust() / rocket.getMass())) * dt;
        massNext = rocket.getMass() + thrust.getThrust() * dt;
        t = t+dt;
        rocket = new Rocket(velocityNext, massNext, heightNext);

        System.out.printf("Lot 9/11 H: %.2f  V %.2f, M %.2f TH %.2f\n", heightNext, velocityNext, massNext, thrust.getThrust());

        if(landed()){
            System.out.println("Landed succesfully");
            successRocket = new Landed(this.rocket);
            ifLandedSuccess = true;
            return this.rocket;
        }

        this.rocket = rocket;
        if (!crashed()) {


            if (noFuel() && thrust.getThrust() != 0) {
                this.rocket = new Rocket(velocityNext, 1000, heightNext);

                throw new OutOfFuelException();
            }
            return rocket;

        } else {
            this.rocket = new Rocket(0, massNext, 0);
            throw new RocketCrashedException();
        }
    }

    private boolean landed() {
        if ((rocket.getyPosition() <= 0) && rocket.getVelocity() > -10) return true;
        else return false;
    }

    private boolean crashed() {
        if (rocket.getyPosition() < 0) {
            return true;
        } else if (heightNext < 0) {
            return true;
        } else
            return false;

    }

    private boolean noFuel() {
        if (rocket.getMass() <= 1000) {
            return true;
        } else return false;
    }


    public static Rocket getRocket() {

        return rocket;
    }

    public Thrust getThrust() {

        return thrust;
    }

    public double getT() {
        return t;
    }

    public void setThrust(Thrust thrust) {
        this.thrust = thrust;
    }

    public Landed getSuccessRocket() {
        return successRocket;
    }

    public void setSuccessRocket(Landed successRocket) {
        this.successRocket = successRocket;
    }

    public boolean isIfLandedSuccess() {
        return ifLandedSuccess;
    }

    public void setIfLandedSuccess(boolean ifLandedSuccess) {
        this.ifLandedSuccess = ifLandedSuccess;
    }

    @Override
    public void update() throws RocketCrashedException {
        try {
            integrate(rocket, getThrust());
        } catch (OutOfFuelException e) {
            thrust.getText().setVisible(false);

        }
    }


}

