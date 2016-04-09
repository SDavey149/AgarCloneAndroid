package davey.scott.ce881_assignment;

import android.graphics.Canvas;

import java.io.Serializable;

/**
 * Created by Scott Davey on 23/02/2016.
 */
public interface Render {
    void draw(Canvas g, float screenX, float screenY);
}
