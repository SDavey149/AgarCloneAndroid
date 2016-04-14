package davey.scott.ce881_assignment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * Created by Scott Davey on 15/02/2016.
 */
public class GameView extends View {
    World worldModel;
    public static int DELAY = 20;
    public static double DT = DELAY/1000.0;
    private int viewHeight;
    private int viewWidth;
    private boolean touchControls;

    public GameView(Context context) {
        super(context);
        setup(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context);
    }

    private void setup(Context context) {
        setKeepScreenOn(true);
        touchControls = true;
        viewHeight = getHeight();
        viewWidth = getWidth();
    }

    public void setModel(World model) {
        worldModel = model;
    }

    public void setTouchControls(boolean touch) {
        touchControls = touch;
    }

    public boolean isTouchNavigationEnabled() {
        return touchControls;
    }



    @Override
    public void draw(Canvas g) {
        super.draw(g);


        Vector2D center = worldModel.getPlayer().getPosition();
        float minX = (float)(center.x - getWidth() /2.0);
        float maxX= (float)(center.x + getWidth() /2.0);
        float minY = (float)(center.y - getHeight() /2.0);
        float maxY = (float)(center.y + getHeight() /2.0);

        synchronized (worldModel) {
            for (GameObject object : worldModel.getObjects()) {
                //handle player drawing separate as it's the center
                if (worldModel.objectInRegion(object, minX, maxX, minY, maxY)) {
                    object.getRender().draw(g, (float) (object.getPosition().x - minX),
                            (float)(object.getPosition().y-minY));
                }

            }
        }
        Player player = worldModel.getPlayer();
        player.getRender().draw(g, getWidth() / 2.0f, getHeight() / 2.0f);


    }

}
