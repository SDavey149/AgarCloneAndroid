package davey.scott.ce881_assignment;

/**
 * Created by Scott Davey on 15/02/2016.
 */
public class GameLoopThread extends Thread {

    World game;
    GameView view;
    private long lastTime = 0;
    boolean running = true;


    public GameLoopThread(World game, GameView view) {
        super();
        this.game = game;
        this.view = view;
    }

    @Override
    public void run() {
        while (running) {
            long currentTime = System.currentTimeMillis();
            double delta = (currentTime - lastTime)/1000.0;
            if (lastTime == 0) {
                delta = GameView.DT;
            }
            lastTime = currentTime;
            game.update(delta);
            view.postInvalidate();
            try {
                Thread.sleep(GameView.DELAY);
            } catch (InterruptedException e) {
            }
        }

    }
}
