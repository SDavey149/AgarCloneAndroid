package davey.scott.ce881_assignment;

import java.util.Random;

/**
 * Created by Scott Davey on 14/04/2016.
 */
public class FleeEnemy extends Enemy {
    //ai reaction speed
    int moveTimeout;
    protected int reactionSpeed;

    public FleeEnemy(World world, Vector2D pos, int reactionSpeed, int color) {
        super(world, pos, color);
        this.reactionSpeed = reactionSpeed;
        moveTimeout = reactionSpeed;
        random = new Random();
    }

    @Override
    public void doMove(double delta) {
        moveTimeout++;
        if (moveTimeout >= reactionSpeed) {
            moveTimeout = 0;
            if (!flee()) {
                //if we havent fled, then attack
                if (!attack()) {
                    //if we havent attacked, move at random
                    moveRandom();
                }
            }
        }
    }


}
