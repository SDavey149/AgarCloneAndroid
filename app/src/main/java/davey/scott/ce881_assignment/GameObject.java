package davey.scott.ce881_assignment;

import android.graphics.Canvas;

import java.io.Serializable;

/**
 * Created by Scott Davey on 15/02/2016.
 */
public abstract class GameObject implements Serializable{
    protected Vector2D pos;
    protected Vector2D vel;
    protected boolean isActive;

    public GameObject(Vector2D pos, Vector2D vel) {
        this.pos = pos;
        this.vel = vel;
        isActive = true;
    }

    public Vector2D getPosition() {
        return new Vector2D(pos);
    }

    public Vector2D getVelocity() {
        return vel;
    }

    public abstract void update(double delta);

    public abstract void draw(Canvas g, float screenX, float screenY);
}
