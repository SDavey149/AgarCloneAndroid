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
            if (!flee()) {
                //if we havent fleed, then attack
                if (!attack()) {
                    //if we havent attacked, move at random
                    moveRandom();
                }
            }
        }



    }


}
