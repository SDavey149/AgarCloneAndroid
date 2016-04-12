package davey.scott.ce881_assignment;

import android.provider.BaseColumns;

/**
 * Created by Scott Davey on 12/04/2016.
 */
public class ScoreDBContract {
    public static abstract class ScoreEntry implements BaseColumns {
        public static final String TABLE_NAME = "Leaderboard";
        public static final String COLUMN_NAME_SCORE = "score";
        public static final String COLUMN_NAME_TIMESTAMP = "dateTimeOccured";
        public static final String COLUMN_NAME_NULLABLE = "null";
    }
}
