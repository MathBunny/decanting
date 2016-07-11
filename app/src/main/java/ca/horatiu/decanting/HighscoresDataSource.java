package ca.horatiu.decanting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used for highscores to store the SQLiteDatabase reference variable.
 * @author Horatiu Lazu
 */

public class HighscoresDataSource {
    /** database SQLiteDatabase This is the database reference. */
    private SQLiteDatabase database;
    /** dbHelper HighscoresDB This is the reference to the database class */
    private HighscoresDB dbHelper;

    private String[] allColumns = {HighscoresDB.COLUMN_LEVEL,
            HighscoresDB.COLUMN_MOVES};

    /** This is a class constructor for the datasource.
     * @param context Context This is the context.
     */
    public HighscoresDataSource(Context context){
        dbHelper = new HighscoresDB(context);
    }

    /** This method opens the SQLite database. */
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void addScore(String moves, String level, String date, String performance, String name) {
        if (database == null)
            open();
        ContentValues values = new ContentValues();
        values.put(HighscoresDB.COLUMN_LEVEL, level); //add the level too?
        values.put(HighscoresDB.COLUMN_DATE, date); //add the new fields :-)
        values.put(HighscoresDB.COLUMN_PLAYER, name); //Game.playerName
        values.put(HighscoresDB.COLUMN_RATING, performance);
        values.put(HighscoresDB.COLUMN_MOVES, moves); //old

        long insertId = database.insert(HighscoresDB.TABLE_COMMENTS, null, values);
        Cursor cursor = database.query(HighscoresDB.TABLE_COMMENTS, allColumns, HighscoresDB.COLUMN_LEVEL + " = " + insertId, null, null, null, null); //useless?
        cursor.moveToFirst();
        cursor.close();
    }

    /** This method returns all highscore entries.
     * @return List<> This is the list of items.
     */
    public List<HighscoresItem> getAllRecords() {
        List<HighscoresItem> comments = new ArrayList<HighscoresItem>();
        Cursor cursor = database.rawQuery("SELECT * FROM HIGHSCORES " + "ORDER BY " + HighscoresDB.COLUMN_LEVEL + " ASC, " + HighscoresDB.COLUMN_MOVES + " ASC", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            HighscoresItem comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }

        cursor.close();
        return comments;
    }

    /** This method resets the database and upgrades the version number. */
    public void reset(){
        dbHelper.onUpgrade(database, 2, 3); //not db!
    }

    /** This method converts a cursor to a comment.
     * @param cursor Cursor This is the cursor.
     * @return HighscoresItem This is the newly constructed object.
     */
    private HighscoresItem cursorToComment(Cursor cursor) {
        HighscoresItem item = new HighscoresItem();
        item.setLevel(cursor.getInt(1));
        item.setDate(cursor.getString(2));
        item.setPlayer(cursor.getString(3));
        item.setPerformanceRating(Double.parseDouble(cursor.getString(4)));
        item.setMoves(Integer.parseInt(cursor.getString(5)));
        return item;
    }

    /** This method creates the database if it was never created before. */
    public void create(){
        dbHelper.onCreate(database);
    }
}
