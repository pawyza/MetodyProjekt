package Calculator;

import Interfaces.Observer;
import Model.Rocket;
import sample.RocketCrashedException;


public class Integrator implements Observer {


    private final double gravity = 1.63;
    private final double k = 636;
    private double dt; // step
    private Rocket rocket;
    private double thrust;

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
    public void integrate(Rocket rocket, double thrust) throws RocketCrashedException {

            this.thrust = thrust;

            double massNext;
            double heightNext;
            double velocityNext;

            heightNext = rocket.getyPosition() + rocket.getVelocity() * dt;
            velocityNext = rocket.getVelocity() + (-gravity - k * (thrust / rocket.getMass()) * dt);
            massNext = rocket.getMass() + thrust * dt;

            rocket = new Rocket(velocityNext, massNext, heightNext);

            this.rocket = rocket;
        if (!crashed()) {
            System.out.printf("Lot 9/11   H: %2f  V %2f, M %2f \n", heightNext, velocityNext, massNext);
        } else throw new RocketCrashedException();
    }

    private boolean crashed() {
        if (rocket.getyPosition() < 0) {
            return true;
        } else {
            return false;

        }
    }

    public double getThrust() {
        return thrust;
    }

    public void setThrust(double thrust) {
        this.thrust = thrust;
    }

    @Override
    public void update() throws RocketCrashedException {
        integrate(rocket, thrust);
    }
}
