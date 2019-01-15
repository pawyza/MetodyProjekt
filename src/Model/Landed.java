package Model;

public class Landed {
    Rocket rocket;
    public Landed(Rocket rocket){
        this.rocket = rocket;
        System.out.println("Wyladowane");
    }

    public Rocket getRocket() {
        return rocket;
    }

    public void setRocket(Rocket rocket) {
        this.rocket = rocket;
    }
}
