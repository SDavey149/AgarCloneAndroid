package davey.scott.ce881_assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott Davey on 12/04/2016.
 */public class LeaderboardHelper extends SQLiteOpenHelper implements LeaderboardDB {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "leaderboard.db";
    // static int id =

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String TABLE_NAME = ScoreDBContract.ScoreEntry.TABLE_NAME;
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ScoreDBContract.ScoreEntry._ID + " INTEGER PRIMARY KEY," +
                    ScoreDBContract.ScoreEntry.COLUMN_NAME_TIMESTAMP + INT_TYPE + COMMA_SEP +
                    ScoreDBContract.ScoreEntry.COLUMN_NAME_SCORE + INT_TYPE + " ) ";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public LeaderboardHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void dropTable(SQLiteDatabase db) {
        db.execSQL(SQL_DELETE_ENTRIES);
    }


    public static void addEntry(SQLiteDatabase db, long timestamp, int score) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ScoreDBContract.ScoreEntry.COLUMN_NAME_TIMESTAMP, timestamp);
        values.put(ScoreDBContract.ScoreEntry.COLUMN_NAME_SCORE, score);


        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(
                ScoreDBContract.ScoreEntry.TABLE_NAME,
                ScoreDBContract.ScoreEntry.COLUMN_NAME_NULLABLE,
                values);
        if (newRowId == -1) {
            System.out.println("An error occurred");
        }

    }


    @Override
    public Score getHighScore() {
        return null;
    }

    @Override
    public List<Score> topN(int n) {
        List<Score> scores = new ArrayList<>();
        try {
            SQLiteDatabase db = getReadableDatabase();
            try{
                Cursor cursor = db.rawQuery("SELECT * FROM " + ScoreDBContract.ScoreEntry.TABLE_NAME +
                                " ORDER BY " + ScoreDBContract.ScoreEntry.COLUMN_NAME_SCORE +
                                " DESC LIMIT " + n,
                        new String[]{});
                cursor.moveToFirst();
                int scoreIndex = cursor.getColumnIndex(ScoreDBContract.ScoreEntry.COLUMN_NAME_SCORE);
                int timestampIndex = cursor.getColumnIndex(ScoreDBContract.ScoreEntry.COLUMN_NAME_TIMESTAMP);
                while (!cursor.isAfterLast()) {
                    Score score = new Score(cursor.getLong(timestampIndex),
                            cursor.getInt(scoreIndex));
                    scores.add(score);
                    cursor.moveToNext();
                }
            }   catch(Exception e) {
                System.out.println(e.toString());
            }
            db.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return scores;
    }

    @Override
    public void saveScore(Score score) {
        SQLiteDatabase db = getWritableDatabase();
        addEntry(db, score.timeStamp, score.score);
        db.close();
    }
}
