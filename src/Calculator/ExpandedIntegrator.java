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
import Model.Rocket;
import Observers.Thrust;

public class ExpandedIntegrator extends Integrator implements Observer {

    private static Rocket rocket;
    private static Thrust thrust;
    private static Thrust angle;
    private final double gravity = 1.63;
    private final double k = 636;

    private double massNext;
    private double positionYNext;
    private double positionXNext;
    private double velocityYNext;
    private double velocityXNext;

    private double dt;

    public ExpandedIntegrator(Rocket rocket, double dt) {
        super(rocket, dt);
    }


    public Rocket expandedIntegrate(Rocket rocket,Thrust thrust, Thrust angle){
        this.angle = angle;
        this.thrust = new Thrust(0);

        positionYNext = rocket.getyPosition() + rocket.getVelocity() * Math.cos(angle.getThrust()) * dt;
        positionXNext = rocket.getxPosition() + rocket.getVelocity() * Math.sin(angle.getThrust()) * dt;
        positionYNext = 5;
        positionXNext = 8;
        velocityYNext = 0;//rocket.getVelocity() + (-gravity-k * thrust.getThrust()/rocket.getMass()) * dt;
        velocityXNext = 1;
        massNext =7;// rocket.getMass()+thrust.getThrust()*dt;


        rocket = new Rocket(velocityYNext,velocityXNext,massNext,positionYNext,positionXNext,thrust.getThrust());
        this.rocket = rocket;
        System.out.println(rocket.toString());

        return rocket;

    }

    @Override
    public void update() throws RocketCrashedException {
        System.out.println("Jestem w expanded integ");
        expandedIntegrate(rocket,thrust,angle);
    }
}
