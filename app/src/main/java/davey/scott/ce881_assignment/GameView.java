package davey.scott.ce881_assignment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * Created by Scott Davey on 15/02/2016.
 */
public class GameView extends View{
    World worldModel;
    private GameControls controls;
    private GestureDetector gestureDetector;
    public static double DT = 20/1000.0;


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
        controls = new GameControls(this, worldModel);
        gestureDetector = new GestureDetector(context, controls);
        controls.setGestureDetector(gestureDetector);
        setOnTouchListener(controls);
        setOnClickListener(controls);


    }

    public void setModel(World model) {
        worldModel = model;
        controls.world = worldModel;
    }

    @Override
    public void draw(Canvas g) {
        super.draw(g);
        Vector2D center = worldModel.getPlayer().getPosition();
        float minX = (float)(center.x - getWidth() /2.0);
        float maxX= (float)(center.x + getWidth() /2.0);
        float minY = (float)(center.y - getHeight() /2.0);
        float maxY = (float)(center.y + getHeight() /2.0);
        Player player = worldModel.getPlayer();
        player.draw(g, getWidth() / 2.0f, getHeight() / 2.0f);
        synchronized (worldModel) {
            for (GameObject object : worldModel.getObjects()) {
                if (worldModel.objectInRegion(object, minX, maxX, minY, maxY)) {
                    object.draw(g, (float)(object.getPosition().x-minX),
                            (float)(object.getPosition().y-minY));
                }

            }
        }
        float offsetX = (float)(worldModel.worldWidth-center.x + player.getRadius());
        float offsetY = (float)(worldModel.worldHeight-center.y + player.getRadius());
        //drawBoundaries(g, minX, maxX, minY, maxY, offsetX, offsetY);



    }

    private void drawBoundaries(Canvas g, float minX, float maxX, float minY, float maxY,
                                float offsetX, float offsetY) {
        //TODO - needs fixing, not working properly at all, use new solution maybe
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(Color.BLACK);
        float midWidth = getWidth()/2f;
        float midHeight = getHeight()/2f;
        if (maxX > worldModel.worldWidth) {
            g.drawLine(midWidth + offsetX, 0, midWidth + offsetX, maxY, p);
        } else if (minX < worldModel.worldWidth) {
            g.drawLine(worldModel.worldWidth-offsetX, 0, worldModel.worldWidth-offsetX, maxY, p);
        }
        if (maxY > worldModel.worldHeight) {
            g.drawLine(0, midHeight + offsetY, maxX, midHeight + offsetY, p);
        } else if (minY < worldModel.worldHeight) {
            g.drawLine(0, worldModel.worldHeight-offsetY, maxX, worldModel.worldHeight-offsetY, p);
        }
    }
}
