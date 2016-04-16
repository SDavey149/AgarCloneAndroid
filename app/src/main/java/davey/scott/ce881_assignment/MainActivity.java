package davey.scott.ce881_assignment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static String TAG = "MainActivity: ";
    public static final int WORLD_SIZE = 3000;
    public static final String COLOR_EXTRA = "Color";

    ImageView characterImage;
    int[] colorChoices = {Color.RED, Color.BLUE, Color.BLACK, Color.GREEN, Color.YELLOW,
        Color.DKGRAY, Color.CYAN};

    int currentChoice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        setContentView(R.layout.activity_main);
        characterImage = (ImageView)findViewById(R.id.character_images);
        currentChoice = 0;
        setCurrentChoice();
    }


    private void setCurrentChoice() {
        /**
         * Set the current imageview to the colour selected.
         *
         * This method is based from an answer on stackoverflow found here:
         * http://stackoverflow.com/a/28514271
         * From the username 'Joby Wilson Matthews'
         */
        Bitmap bitmap = Bitmap.createBitmap(240, 240, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(colorChoices[currentChoice]);
        canvas.drawCircle(120, 120, 120, paint);
        characterImage.setImageBitmap(bitmap);
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
        currentChoice++;
        if (currentChoice >= colorChoices.length) {
            currentChoice = 0;
        }
        setCurrentChoice();
    }

    public void leftArrowClick(View v) {
        currentChoice--;
        if (currentChoice < 0) {
            currentChoice = colorChoices.length-1;
        }
        setCurrentChoice();
    }

    public void playNowClick(View v) {
        Intent intent = new Intent(this, PlayGameActivity.class);
        intent.putExtra(COLOR_EXTRA, colorChoices[currentChoice]);
        startActivity(intent);
    }

    /*
    public void customGameClick(View v) {
        Toast toast = Toast.makeText(this, "Not complete", Toast.LENGTH_SHORT);
        toast.show();
    }
*/
    public void leaderBoardClick(View v) {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
    }

    public void settingsClick(View v) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
