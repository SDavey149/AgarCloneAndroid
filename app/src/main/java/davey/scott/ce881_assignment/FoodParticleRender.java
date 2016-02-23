package davey.scott.ce881_assignment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by Scott Davey on 23/02/2016.
 */
public class FoodParticleRender implements Render {

    public static int RADIUS = 10;
    private static Random randomGen = new Random();
    private static int[] AVAILABLE_COLORS = {Color.BLACK, Color.BLUE,
            Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.MAGENTA};
    int color;
    FoodParticle object;
    Paint p;

    public FoodParticleRender(FoodParticle particle) {
        object = particle;
        color = AVAILABLE_COLORS[randomGen.nextInt(AVAILABLE_COLORS.length)];
        p = new Paint();
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.FILL);
        p.setColor(color);
    }

    @Override
    public void draw(Canvas g, float screenX, float screenY) {
        g.drawCircle(screenX, screenY, RADIUS, p);
    }
}
