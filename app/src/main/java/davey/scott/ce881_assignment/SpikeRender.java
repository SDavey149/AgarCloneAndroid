package davey.scott.ce881_assignment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Scott Davey on 15/04/2016.
 */
public class SpikeRender implements Render {

    SpikeBall spike;
    Paint p;

    public SpikeRender(SpikeBall spike) {
        this.spike = spike;
        p = new Paint();
        p.setAntiAlias(true);
        p.setTextSize(28);
    }

    @Override
    public void draw(Canvas g, float screenX, float screenY) {
        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.FILL);
        g.drawCircle(screenX, screenY, spike.mass, p);

        p.setColor(Color.WHITE);
        String massString = "SPIKE";
        float textWidth = p.measureText(massString, 0, massString.length());
        g.drawText(massString, screenX - textWidth / 2f, screenY - (p.ascent() + p.descent()) / 2f, p);

        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.GRAY);
        g.drawCircle(screenX, screenY, spike.mass, p);
    }
}
