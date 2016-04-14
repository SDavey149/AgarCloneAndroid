package davey.scott.ce881_assignment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by Scott Davey on 15/02/2016.
 */
public class FoodParticle {

    private static Random randomGen = new Random();

    private Vector2D position;
    private World world;
    private Render render;
    protected boolean isActive = true;

    public FoodParticle(World w, Vector2D pos) {
        world = w;
        position = pos;
        render = new FoodParticleRender(this);
    }

    public static FoodParticle randomParticle(World w, int maxX, int maxY) {
        return new FoodParticle(w, new Vector2D(randomGen.nextInt(maxX), randomGen.nextInt(maxY)));
    }

    public Vector2D getPosition() {
        return position;
    }

    public Render getRender() {
        return render;
    }


}
