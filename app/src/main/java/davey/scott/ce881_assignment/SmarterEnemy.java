package davey.scott.ce881_assignment;

import java.util.Random;

/**
 * Created by Scott Davey on 14/04/2016.
 */
public class SmarterEnemy extends Enemy {
    //ai reaction speed
    int moveTimeout;
    private int reactionSpeed;
    private Random random;

    public static final int ALERT_DISTANCE = 500;

    public SmarterEnemy(World world, Vector2D pos, int reactionSpeed) {
        super(world, pos);
        this.reactionSpeed = reactionSpeed;
        moveTimeout = reactionSpeed;
        random = new Random();
    }

    @Override
    public void doMove(double delta) {
        moveTimeout++;
        if (moveTimeout >= reactionSpeed) {
            moveTimeout = 0;
            Player closestPredator = world.getClosestPredator(this);
            if (closestPredator != null) {;
                double distToPredator = closestPredator.getPosition().dist(getPosition());
                if (distToPredator < ALERT_DISTANCE) {
                    Vector2D direction = new Vector2D(closestPredator.getPosition());
                    Vector2D myPos = new Vector2D(getPosition());
                    myPos.mult(-1);
                    direction.add(myPos);
                    direction.normalise();
                    //move in opposite direction
                    direction.mult(-1);
                    setDirection(direction);
                } else {
                    Vector2D direction = new Vector2D(random.nextInt(10)-5, random.nextInt(10)-5);
                    direction.normalise();
                    setDirection(direction);
                }
            }
        }



    }
}
