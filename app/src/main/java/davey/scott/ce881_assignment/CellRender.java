package davey.scott.ce881_assignment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.io.Serializable;

/**
 * Created by Scott Davey on 09/04/2016.
 */
public class CellRender implements Render, Serializable {
    Cell cell;
    Paint p;
    int color;
    public static final int CELL_RADIUS = 15;

    public CellRender(Cell cell, int color) {
        this.cell = cell;
        p = new Paint();
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.FILL);
        this.color = color;
    }

    @Override
    public void draw(Canvas g, float screenX, float screenY) {
        p.setColor(color);
        g.drawCircle(screenX, screenY, CELL_RADIUS, p);

        /*p.setColor(Color.WHITE);
        String massString = "" + mass;
        float textWidth = p.measureText(massString, 0, massString.length());
        g.drawText("" + mass, screenX - textWidth / 2f, screenY - (p.ascent() + p.descent()) / 2f, p);

        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.GRAY);
        g.drawCircle(screenX, screenY, mass, p);*/
    }
}
