package davey.scott.ce881_assignment;

import android.graphics.Canvas;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by Scott Davey on 15/02/2016.
 */
public abstract class GameObject implements Serializable, Comparable {
    protected Vector2D pos;
    protected Vector2D vel;
    protected boolean isActive = true;
    protected Render render;
    protected int mass;
    protected World world;
    protected Random random;

    public GameObject(World world, Vector2D pos, Vector2D vel) {
        this.world = world;
        this.pos = pos;
        this.vel = vel;
        random = new Random();
    }

    public Vector2D getPosition() {
        return new Vector2D(pos);
    }

    public void setPosition(Vector2D pos) {
        this.pos = pos;
    }

    public Vector2D getVelocity() {
        return vel;
    }

    public abstract void update(double delta);

    public Render getRender() {
        return render;
    }

    @Override
    public int compareTo(Object o) {
        GameObject other = (GameObject) o;
        return mass-other.mass;
    }

    public boolean collidesWith(GameObject obj) {
        int m = Math.max(obj.mass, mass);
        if (pos.dist(obj.getPosition()) < m) {
            return true;
        }
        return false;
    }

    public boolean collidesWith(FoodParticle obj) {
        if (pos.dist(obj.getPosition()) < mass) {
            return true;
        }
        return false;
    }

    public abstract void collided(GameObject obj);

    protected Vector2D getRandomDirection() {
        return new Vector2D(random.nextGaussian(), random.nextGaussian());
    }
}
