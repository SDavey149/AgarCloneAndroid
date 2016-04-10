package davey.scott.ce881_assignment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        DateFormat df = DateFormat.getDateTimeInstance();
        Date date = new Date(timeStamp);
        return df.format(date);
    }

    @Override
    public int compareTo(Object another) {
        Score otherScore = (Score)another;
        return otherScore.score-this.score;
    }
}
