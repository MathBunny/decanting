package ca.horatiu.decanting;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/** This class acts as a helper class.
 * @author Horatiu Lazu
 * Created by Horatiu on 05/07/2016.
 */
public class HighscoresDB extends SQLiteOpenHelper {
    /** TABLE_COMMENTS String This is the name of the database. */
    public static final String TABLE_COMMENTS = "HIGHSCORES";
    /** COLUMN_LEVELS String This is the name of the level column. */
    public static final String COLUMN_LEVEL = "Level";
    /** COLUMN_MOVES String This is the name of the moves column. */
    public static final String COLUMN_MOVES = "Moves";
    /** COLUMN_PLAYER String This is the name of the player column. */
    public static final String COLUMN_PLAYER = "Player";
    /** COLUMN_DATE String This is the name of the date column. */
    public static final String COLUMN_DATE = "Date";
    /** COLUMN_RATING String This is the name of the rating column. */
    public static final String COLUMN_RATING = "Rating";
    /** COLUMN_ROW_NUMBER String This is the row number. */
    public static final String COLUMN_ROW_NUMBER = "Row";
    /** DATABASE_NAME String This is the name of the database's file name. */
    private static final String DATABASE_NAME = "highscores.db";
    /** DATABASE_VERSION int This is the database version number. */
    private static final int DATABASE_VERSION = 1;

    /**DATABASE_CREATE String This is the create command for the database. */
    private static final String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_COMMENTS + " (" + COLUMN_ROW_NUMBER
            + " INTEGER AUTO_INCREMENT, "
            + COLUMN_LEVEL
            + " INTEGER, "
            + COLUMN_DATE
            + " TEXT, "
            + COLUMN_PLAYER
            + " TEXT, "
            + COLUMN_RATING
            + " FLOAT, "
            + COLUMN_MOVES
            + " INTEGER )"
            + ";"
            ;

    /** This method is called whenever the database has to be created.
     * @param db This is the database reference.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    /**
     * This is the class constructor.
     * @param context Context This is the context for the class.
     */
    public HighscoresDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This method is used whenever the database changes its version number.
     * @param db SQLiteDatabase This is the reference to the database.
     * @param oldVersion int This is the old version number.
     * @param newVersion int This is the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        onCreate(db);
    }
}
