package davey.scott.ce881_assignment;

/**
 * Created by Scott Davey on 16/04/2016.
 */
public class AttackEnemy extends FleeEnemy {
    public AttackEnemy(World world, Vector2D pos, int reactionSpeed, int color) {
        super(world, pos, reactionSpeed, color);
    }

    @Override
    public void doMove(double delta) {
        moveTimeout++;
        if (moveTimeout >= reactionSpeed) {
            moveTimeout = 0;
            if (!attack()) {
                //if we havent attacked, then flee
                if (!flee()) {
                    //if we havent attacked, move at random
                    moveRandom();
                }
            }
        }
    }
}
