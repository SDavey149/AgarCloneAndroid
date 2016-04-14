package davey.scott.ce881_assignment;

/**
 * Created by Scott Davey on 09/04/2016.
 */
public class Cell extends GameObject {

    private static double e = 0.9;

    public Cell(World w, Vector2D pos, Vector2D vel, int mass, int color) {
        super(w, pos, vel);
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

    @Override
    public void collided(GameObject obj) {
        if (obj.mass > mass) {
            //they can eat the cell
            isActive = false;
        }
    }
}
