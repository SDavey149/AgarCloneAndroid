package davey.scott.ce881_assignment;

import java.util.Random;

/**
 * Created by Scott Davey on 14/04/2016.
 */
public abstract class Enemy extends Player {

    public static final int VISION_DISTANCE = 500;

    public Enemy(World world, Vector2D pos, int color) {
        super(world, pos, color);
    }

    @Override
    public void update(double delta) {
        super.update(delta);
        doMove(delta);
    }

    public abstract void doMove(double delta);

    protected boolean flee() {
        Player closestPredator = world.getClosestPredator(this);
        if (closestPredator != null) {;
            double distToPredator = closestPredator.getPosition().dist(getPosition());
            if (distToPredator < VISION_DISTANCE) {
                Vector2D direction = new Vector2D(closestPredator.getPosition());
                Vector2D myPos = new Vector2D(getPosition());
                myPos.mult(-1);
                direction.add(myPos);
                direction.normalise();
                //move in opposite direction
                direction.mult(-1);
                setDirection(direction);
                return true;
            }
        }
        return false;
    }

    protected boolean attack() {
        GameObject closestPrey = world.getClosestPrey(this);
        if (closestPrey != null) {;
            double distToPrey = closestPrey.getPosition().dist(getPosition());
            if (distToPrey < VISION_DISTANCE) {
                Vector2D direction = new Vector2D(closestPrey.getPosition());
                Vector2D myPos = new Vector2D(getPosition());
                myPos.mult(-1);
                direction.add(myPos);
                direction.normalise();
                setDirection(direction);
                return true;
            }
        }
        return false;
    }

    protected void moveRandom() {
        Vector2D direction = getRandomDirection();
        direction.normalise();
        setDirection(direction);
    }
}
