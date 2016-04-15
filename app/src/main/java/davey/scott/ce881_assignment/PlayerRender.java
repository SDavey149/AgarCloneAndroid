package davey.scott.ce881_assignment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.io.Serializable;

/**
 * Created by Scott Davey on 23/02/2016.
 */
public class PlayerRender implements Render, Serializable {

    Player player;
    Paint p;

    public PlayerRender(Player player) {
        this.player = player;
        p = new Paint();
        p.setAntiAlias(true);
        p.setTextSize(28);
    }

    @Override
    public void draw(Canvas g, float screenX, float screenY) {
        int mass = player.getRadius();
        p.setColor(player.color);
        p.setStyle(Paint.Style.FILL);
        g.drawCircle(screenX, screenY, mass, p);

        p.setColor(Color.WHITE);
        String massString = "" + mass;
        float textWidth = p.measureText(massString, 0, massString.length());
        g.drawText("" + mass, screenX - textWidth / 2f, screenY - (p.ascent() + p.descent()) / 2f, p);

    }
}
