package davey.scott.ce881_assignment;

/**
 * Created by Scott Davey on 15/04/2016.
 */
public class SpikeBall extends GameObject {
    public SpikeBall(World world, Vector2D pos, int mass) {
        super(world, pos, new Vector2D());
        render = new SpikeRender(this);
        this.mass = mass;
    }

    @Override
    public void update(double delta) {
        //doesnt move

    }

    @Override
    public void collided(GameObject obj) {
        if (obj.mass > mass) {
            isActive = false;
        }

    }
}
