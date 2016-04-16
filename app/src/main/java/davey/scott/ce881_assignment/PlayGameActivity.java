package davey.scott.ce881_assignment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;



public class PlayGameActivity extends Activity {


    World model;
    GameView view;

    private GestureDetector gestureDetector;
    private GameControls controls;
    private AccelerometerControls accelControls;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    GameLoopThread gameLoop;
    private LeaderboardHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        view = (GameView) findViewById(R.id.game_view);
        helper = new LeaderboardHelper(this);
        setupGame();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        long timestamp = System.currentTimeMillis() / 1000;
        int score = model.getPlayer().getMass();
        helper.saveScore(new Score(timestamp, score));
    }

    private void setupGame() {
        Bundle b = getIntent().getExtras();
        int color = b.getInt(MainActivity.COLOR_EXTRA, Color.BLACK);

        model = new World(MainActivity.WORLD_SIZE,MainActivity.WORLD_SIZE, color);
        model.addRandomParticles(300);
        view.setModel(model);
        controls = new GameControls(view, model);
        gestureDetector = new GestureDetector(view.getContext(), controls);
        controls.setGestureDetector(gestureDetector);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean(getString(R.string.accelerometer_key), false) && accelerometer != null) {
            accelControls = new AccelerometerControls(view, model);
            view.setTouchControls(false);
            view.setOnTouchListener(controls);
            view.setOnClickListener(controls);
        } else {
            view.setOnTouchListener(controls);
            view.setOnClickListener(controls);
        }


    }

    private void startExecutor() {
        gameLoop = new GameLoopThread(this);
        gameLoop.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (accelControls != null)
            sensorManager.registerListener(accelControls, accelerometer,
                    SensorManager.SENSOR_DELAY_GAME);
        startExecutor();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (accelControls != null)
            sensorManager.unregisterListener(accelControls);
        gameLoop.running = false;
        try {
            gameLoop.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play_game, menu);
        return true;
    }

    protected void displayGameOverDialog() {
        long timestamp = System.currentTimeMillis() / 1000;
        int score = model.getPlayer().getMass();
        helper.saveScore(new Score(timestamp, score));

        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Game Over");
        dialog.setMessage("Game over. Want to play again?");
        dialog.setCancelable(false);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE,
                "Play again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        model.reset();
                        startExecutor();
                    }
                });

        dialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                "Main menu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(PlayGameActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

        dialog.show();
    }


}
