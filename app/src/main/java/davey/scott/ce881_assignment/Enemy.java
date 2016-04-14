package davey.scott.ce881_assignment;

/**
 * Created by Scott Davey on 14/04/2016.
 */
public abstract class Enemy extends Player {

    public Enemy(World world, Vector2D pos) {
        super(world, pos);

    }

    @Override
    public void update(double delta) {
        super.update(delta);
        doMove(delta);
    }

    public abstract void doMove(double delta);
}
