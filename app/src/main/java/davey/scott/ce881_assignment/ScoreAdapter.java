package davey.scott.ce881_assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Scott Davey on 10/04/2016.
 *
 * I made use of the tutorial found at: https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 * When creating this class
 */
public class ScoreAdapter extends ArrayAdapter<Score> {
    public ScoreAdapter(Context context, ArrayList<Score> scores) {
        super(context, 0, scores);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Score score = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_score,
                    parent, false);
        }
        TextView positionText = (TextView) convertView.findViewById(R.id.position);
        TextView scoreText = (TextView) convertView.findViewById(R.id.score);
        TextView dateText = (TextView) convertView.findViewById(R.id.date);

        positionText.setText("" + (position+1));
        scoreText.setText("" + score.score);
        dateText.setText(score.getDate());
        return convertView;
    }
}
