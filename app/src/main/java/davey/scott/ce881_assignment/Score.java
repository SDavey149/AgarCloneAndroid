package davey.scott.ce881_assignment;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Scott Davey on 10/04/2016.
 */
public class Score implements Comparable {
    public long timeStamp;
    public int score;

    public Score(long timeStamp, int score) {
        this.timeStamp = timeStamp;
        this.score = score;
    }

    public String getDate() {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timeStamp*1000);
        String date = DateFormat.format("dd-MM-yyyy HH:mm", cal).toString();
        return date;

    }

    @Override
    public int compareTo(Object another) {
        Score otherScore = (Score)another;
        return otherScore.score-this.score;
    }
}
