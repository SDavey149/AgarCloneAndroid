package davey.scott.ce881_assignment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        /*ArrayList<Score> scores = new ArrayList<Score>();
        scores.add(new Score(1042404234l, 100));
        scores.add(new Score(35235325235l, 200));
        Collections.sort(scores);*/
        LeaderboardHelper helper = new LeaderboardHelper(this);
        ArrayList<Score> scores = new ArrayList<>(helper.topN(20));
        ScoreAdapter adapter = new ScoreAdapter(this, scores);
        ListView scoreListView = (ListView) findViewById(R.id.scoreList);
        scoreListView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_leaderboard, menu);
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
}
