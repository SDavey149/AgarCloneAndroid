package davey.scott.ce881_assignment;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * Created by Scott Davey on 10/04/2016.
 */
public class AccelerometerControls implements SensorEventListener, Controller {

    private GameView view;
    private World world;
    private float xStart, yStart;
    public static final double ACCELEROMETER_SENSITIVITY = 0.5;

    public AccelerometerControls(GameView view, World world) {
        this.view = view;
        this.world = world;
        xStart = Float.MAX_VALUE;
        yStart = Float.MAX_VALUE;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];

        if (xStart == Float.MAX_VALUE && yStart == Float.MAX_VALUE) {
            xStart = x;
            yStart = y;
        } else {
            float deltaX = x-xStart;
            float deltaY = y-yStart;
            Vector2D direction = new Vector2D(-deltaX,
                    deltaY);
            if (direction.mag() > ACCELEROMETER_SENSITIVITY) {
                direction.normalise();
                world.getPlayer().setDirection(direction);
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
