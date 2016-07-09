package ca.horatiu.decanting;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/** This class acts as a helper class.
 * @author Horatiu Lazu
 * Created by Horatiu on 05/07/2016.
 */
public class HighscoresDB extends SQLiteOpenHelper {
    public static final String TABLE_COMMENTS = "HIGHSCORES"; //table name
    public static final String COLUMN_LEVEL = "Level";
    public static final String COLUMN_MOVES = "Moves";
    public static final String COLUMN_ROW_NUMBER = "Row";

    private static final String DATABASE_NAME = "highscores.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_SORT = "ORDER BY " + COLUMN_LEVEL + " ASC, " + COLUMN_MOVES + " DESC";

    private static final String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_COMMENTS + " (" + COLUMN_ROW_NUMBER
            + " INTEGER AUTO_INCREMENT, "
            + COLUMN_LEVEL
            + " INTEGER, "
            + COLUMN_MOVES
            + " INTEGER )" //+ DATABASE_SORT
            + ";"
            ; //text not null

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    public HighscoresDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void sort(SQLiteDatabase db){
        db.rawQuery("SELECT * FROM HIGHSCORES ORDER BY Level,Moves;", null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        onCreate(db);
    }
}
