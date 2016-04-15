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

    private static double E_RESTITUTION = 0.9;
    public static final int CELL_CREATION_MASS = 15;
    public static final int INITIAL_MASS = 30;
    public static final int BASE_SPEED = 1;
    public static final int EXPLOSION_SEPERATIONS = 10;
    public static final double EXPLOSION_LOSS = 0.08;

    protected boolean dash;
    protected boolean absorbed;
    protected double speed;
    private int baseSpeed;
    private double normalMovementSpeed;
    protected int color;


    public Player(World world, Vector2D pos, int c) {
        super(world, pos, new Vector2D(0, 0));
        mass = INITIAL_MASS; //starting mass
        speed = BASE_SPEED; //this is just a placeholder, speed will be dynamic in future based on mass
        baseSpeed = 150;
        normalMovementSpeed = speed;
        render = new PlayerRender(this);
        dash = false;
        color = c;
    }

    public void update(double delta) {
        updateNormalSpeed();
        Vector2D copy = new Vector2D(vel);
        copy.mult(speed);
        copy.mult(delta);
        pos.add(copy);
        if (speed > normalMovementSpeed) {
            speed *= E_RESTITUTION;
        } else {
            speed = normalMovementSpeed;
        }
        if (dash) {
            if (mass > 30) {
                this.speed = 6;
                Vector2D cellVel = new Vector2D(vel);
                cellVel.mult(-1);
                Vector2D direction = new Vector2D(cellVel);
                direction.normalise();
                direction.mult(mass + 1);
                direction.add(pos); //cell position
                cellVel.mult(0.08);

                Cell cell = new Cell(world, new Vector2D(direction), new Vector2D(cellVel), CELL_CREATION_MASS,
                        color);
                mass -= CELL_CREATION_MASS;
                world.addGameObject(cell);
            }
            dash = false;
        }
    }

    private void updateNormalSpeed() {
        //max speed based on mass of object
        normalMovementSpeed = BASE_SPEED*Math.pow(0.994, mass-INITIAL_MASS);
    }

    @Override
    public void collided(GameObject obj) {
        if (obj.mass > mass && obj instanceof Player) {
            //im absorbed, nooooo
            isActive = false;
        } else if (obj.mass < mass) {
            addMass(obj.mass);
            if (obj instanceof SpikeBall) {
                explode();
            }


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
        dash = true;
    }

    public int getRadius() {
        return mass;
    }

    public void addMass(int add) {
        mass+=add;
    }

    public int getMass() {
        return mass;
    }

    public void absorbed() {
        absorbed = true;
    }



    public boolean didAbsorb() {
        boolean copy = absorbed;
        absorbed = false;
        return absorbed;
    }

    private void explode() {
        for (int i = 0; i < EXPLOSION_SEPERATIONS; i++) {
            Vector2D cellVel = new Vector2D(getRandomDirection());
            cellVel.mult(random.nextInt(15) + 10);
            Vector2D position = new Vector2D(cellVel);
            position.normalise();
            position.mult(mass + 1); //move it out of the way so we dont collide
            position.add(pos); //cell position

            Cell cell = new Cell(world, new Vector2D(position), new Vector2D(cellVel), (int)(EXPLOSION_LOSS*mass),
                    color);
            world.addGameObject(cell);
            mass -= (int)(EXPLOSION_LOSS*mass);
        }

    }
}
