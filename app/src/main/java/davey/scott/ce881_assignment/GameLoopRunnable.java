package davey.scott.ce881_assignment;

/**
 * Created by Scott Davey on 15/02/2016.
 */
public class GameLoopRunnable implements Runnable {

    World game;
    GameView view;
    private long lastTime = 0;


    public GameLoopRunnable(World game, GameView view) {
        this.game = game;
        this.view = view;
    }

    @Override
    public void run() {
        long currentTime = System.currentTimeMillis();
        double delta = (currentTime - lastTime)/1000.0;
        if (lastTime == 0) {
            delta = GameView.DT;
        }
        lastTime = currentTime;
        game.update(delta);
        view.postInvalidate();
    }
}
