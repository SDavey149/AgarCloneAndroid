package davey.scott.ce881_assignment;

/**
 * Created by Scott Davey on 09/04/2016.
 */
public class Cell extends GameObject {

    private int mass;
    private static double e = 0.9;

    public Cell(Vector2D pos, Vector2D vel, int mass, int color) {
        super(pos, vel);
        this.mass = mass;
        render = new CellRender(this, color);
    }


    @Override
    public void update(double delta) {
        Vector2D velCopy = new Vector2D(vel);
        velCopy.mult(delta);
        pos.add(vel);
        vel.mult(e);
    }

    public int getMass() {
        return mass;
    }
}
