package davey.scott.ce881_assignment;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class PlayGameActivity extends Activity {

    World model;
    GameView view;
    ScheduledExecutorService executor;
    public final static String MODEL_KEY = "MODEL_SAVE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        view = (GameView) findViewById(R.id.game_view);
        setupGame();


    }

    private void setupGame() {
        model = new World(3000,3000);
        model.addRandomParticles(300);
        view.setModel(model);
        //view.postInvalidate();
    }

    private void startExecutor() {
        GameLoopRunnable gameLoop = new GameLoopRunnable(model, view);
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(gameLoop, 20, 20l, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onResume() {
        super.onResume();
        startExecutor();
    }

    @Override
    public void onPause() {
        super.onPause();
        executor.shutdownNow();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_pause) {
            if (!executor.isShutdown()) {
                item.setTitle(R.string.menu_resume);
                executor.shutdownNow();
            } else {
                item.setTitle(R.string.menu_pause);
                startExecutor();
            }
        }

        else if (id == R.id.menu_reset) {
            setupGame();
            executor.shutdownNow();
            startExecutor();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSaveInstanceState(Bundle savedInstance) {
        savedInstance.putSerializable(MODEL_KEY, model);
        super.onSaveInstanceState(savedInstance);

    }

    public void onRestoreInstanceState(Bundle savedInstance) {
        super.onRestoreInstanceState(savedInstance);
        model = (World) savedInstance.getSerializable(MODEL_KEY);
        view.setModel(model);
        view.postInvalidate();
    }

}
