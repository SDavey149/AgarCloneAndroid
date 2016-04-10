package davey.scott.ce881_assignment;

import android.content.Intent;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    public static String TAG = "MainActivity: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void rightArrowClick(View v) {
        Toast toast = Toast.makeText(this, "Not complete", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void leftArrowClick(View v) {
        Toast toast = Toast.makeText(this, "Not complete", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void playNowClick(View v) {
        Intent intent = new Intent(this, PlayGameActivity.class);
        startActivity(intent);
    }

    public void customGameClick(View v) {
        Toast toast = Toast.makeText(this, "Not complete", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void leaderBoardClick(View v) {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
    }

    public void settingsClick(View v) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
