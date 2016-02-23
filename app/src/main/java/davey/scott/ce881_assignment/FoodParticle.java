package davey.scott.ce881_assignment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by Scott Davey on 15/02/2016.
 */
public class FoodParticle extends GameObject{

    private Render render;
    private static Random randomGen = new Random();

    public FoodParticle(Vector2D pos) {
        super(pos, new Vector2D(0,0));
        render = new FoodParticleRender(this);
    }

    public static FoodParticle randomParticle(int maxX, int maxY) {
        return new FoodParticle(new Vector2D(randomGen.nextInt(maxX), randomGen.nextInt(maxY)));
    }

    @Override
    public void update(double delta) {
        //do nothing for now in prototype
    }

    public Render getRender() {
        return render;
    }

}
