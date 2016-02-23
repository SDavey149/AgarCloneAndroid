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
    private int speed;

    public Player(Vector2D pos) {
        super(pos, new Vector2D(0,0));
        mass = 30; //starting mass
        speed = 150; //this is just a placeholder, speed will be dynamic in future based on mass
    }

    public void update(double delta) {
        Vector2D copy = new Vector2D(vel);
        copy.mult(delta);
        pos.add(copy);
    }

    @Override
    public void draw(Canvas g, float screenX, float screenY) {
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.BLACK);
        g.drawCircle(screenX, screenY, mass, p);
        p.setColor(Color.WHITE);
        String massString = "" + mass;
        p.setTextSize(28);
        float textWidth = p.measureText(massString, 0, massString.length());
        g.drawText("" + mass, screenX - textWidth / 2f, screenY - (p.ascent() + p.descent()) / 2f, p);
    }

    public void setDirection(Vector2D direction) {
        direction.mult(speed);
        vel.set(direction);
    }

    public void setVelocity(Vector2D vel) {
        this.vel = vel;
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
