package davey.scott.ce881_assignment;

import java.util.Random;

/**
 * Created by Scott Davey on 14/04/2016.
 */
public class RandomEnemy extends Enemy {
    int moveTimeout = 0;
    Random random;


    public RandomEnemy(World world, Vector2D pos, int color) {
        super(world, pos, color);
        moveTimeout = 100;
        random = new Random();
    }

    @Override
    public void doMove(double delta) {
        moveTimeout++;
        if (moveTimeout >= 100) {
            moveTimeout = 0;
            Vector2D direction = new Vector2D(random.nextInt(10)-5, random.nextInt(10)-5);
            direction.normalise();
            setDirection(direction);
        }
    }
}
