package davey.scott.ce881_assignment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;

import java.io.Serializable;

/**
 * Created by Scott Davey on 15/02/2016.
 */
public class Player extends GameObject {

    private World world;
    private int mass;
    private double speed;
    private int baseSpeed;
    private static double E_RESTITUTION = 0.9;
    public static final int CELL_CREATION_MASS = 15;


    public Player(World world, Vector2D pos) {
        super(pos, new Vector2D(0,0));
        this.world = world;
        mass = 30; //starting mass
        speed = 1; //this is just a placeholder, speed will be dynamic in future based on mass
        baseSpeed = 150;
        render = new PlayerRender(this);
    }

    public void update(double delta) {
        Vector2D copy = new Vector2D(vel);
        copy.mult(speed);
        copy.mult(delta);
        pos.add(copy);
        if (speed > 1) {
            speed *= E_RESTITUTION;
        } else {
            speed = 1;
        }
    }

    public void setDirection(Vector2D direction) {
        direction.mult(baseSpeed);
        vel.set(direction);
    }

    public void setVelocity(Vector2D vel) {
        this.vel = vel;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


    public void dash() {
        if (mass > 30) {
            this.speed = 10;
            Vector2D cellVel = new Vector2D(vel);
            cellVel.mult(-1);
            Vector2D direction = new Vector2D(cellVel);
            direction.normalise();
            direction.mult(mass + 1);
            direction.add(pos); //cell position
            cellVel.mult(0.05);

            Cell cell = new Cell(new Vector2D(direction), new Vector2D(cellVel), CELL_CREATION_MASS,
                    Color.BLUE);
            mass -= CELL_CREATION_MASS;
            world.addGameObject(cell);
        }


    }

    public int getRadius() {
        return mass;
    }

    public boolean collidesWith(GameObject obj) {
        Vector2D otherPos = obj.getPosition();
        if (pos.dist(otherPos) < mass) {
            return true;
        }
        return false;
    }

    public void addMass(int add) {
        mass+=add;
    }
}
