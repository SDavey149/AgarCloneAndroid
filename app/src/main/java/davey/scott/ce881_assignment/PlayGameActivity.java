package davey.scott.ce881_assignment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
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

    public final static String MODEL_KEY = "MODEL_SAVE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        view = (GameView) findViewById(R.id.game_view);
        setupGame();


    }

    private void setupGame() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.bubblepop_sound);
        model = new World(3000,3000);
        model.addRandomParticles(300);
        model.setMediaPlayer(mediaPlayer);
        view.setModel(model);
        controls = new GameControls(view, model);
        gestureDetector = new GestureDetector(view.getContext(), controls);
        controls.setGestureDetector(gestureDetector);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean("accelerometer_controls", false) && accelerometer != null) {
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
        gameLoop = new GameLoopThread(model, view);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play_game, menu);
        return true;
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
