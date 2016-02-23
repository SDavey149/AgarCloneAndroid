package davey.scott.ce881_assignment;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Scott Davey on 23/02/2016.
 */
public class GameControls implements
        View.OnClickListener, View.OnTouchListener,
        GestureDetector.OnGestureListener {

    GameView view;
    World world;
    GestureDetector detector;

    public GameControls(GameView v, World w) {
        view = v;
        world = w;
    }

    @Override
    public void onClick(View v) {

    }

    public void setGestureDetector(GestureDetector detector) {
        this.detector = detector;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        detector.onTouchEvent(event);
        float curX = event.getX();
        float curY = event.getY();
        Vector2D direction = new Vector2D(curX-view.getWidth()/2.0,
                curY-view.getHeight()/2.0);
        direction.normalise();
        world.getPlayer().setDirection(direction);
        return false;
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        System.out.println("flung");
        return false;
    }
}
