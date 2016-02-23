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

    private int mass;
    private double speed;
    private int baseSpeed;
    private static double E_RESTITUTION = 0.9;

    Render render;

    public Player(Vector2D pos) {
        super(pos, new Vector2D(0,0));
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

    public Render getRender() {
        return render;
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
