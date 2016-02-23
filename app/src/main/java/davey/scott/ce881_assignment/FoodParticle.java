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

    public static int RADIUS = 10;
    private static Random randomGen = new Random();
    private static int[] AVAILABLE_COLORS = {Color.BLACK, Color.BLUE,
    Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.MAGENTA};
    int color;

    public FoodParticle(Vector2D pos) {
        super(pos, new Vector2D(0,0));
        color = AVAILABLE_COLORS[randomGen.nextInt(AVAILABLE_COLORS.length)];
    }

    public static FoodParticle randomParticle(int maxX, int maxY) {
        return new FoodParticle(new Vector2D(randomGen.nextInt(maxX), randomGen.nextInt(maxY)));
    }

    @Override
    public void update(double delta) {
        //do nothing for now in prototype
    }

    @Override
    public void draw(Canvas g, float screenX, float screenY) {
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.FILL);
        p.setColor(color);
        g.drawCircle(screenX, screenY, RADIUS, p);
    }
}
