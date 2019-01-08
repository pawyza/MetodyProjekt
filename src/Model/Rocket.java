package Model;

public class Rocket {

    private float height;
    private float mass;
    private float yPosition; // height

    public Rocket(float height, float mass, float yPosition) {
        this.height = height;
        this.mass = mass;
        this.yPosition = yPosition;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public float getyPosition() {
        return yPosition;
    }

    public void setyPosition(float yPosition) {
        this.yPosition = yPosition;
    }
}
