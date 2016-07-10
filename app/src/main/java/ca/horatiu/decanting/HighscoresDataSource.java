package ca.horatiu.decanting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.database.sqlite.*;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HighscoresDataSource {
    private SQLiteDatabase database;
    private HighscoresDB dbHelper;
    private String[] allColumns = {HighscoresDB.COLUMN_LEVEL,
            HighscoresDB.COLUMN_MOVES};

    public HighscoresDataSource(Context context){
        dbHelper = new HighscoresDB(context);
    }

    public void open() throws SQLException {
        if (dbHelper == null)
            Log.d("ERROR", "NULL!!");
        else
            Log.d("SHOULD BE OK!", "Ok!");
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addScore(String moves, String level, String date, String performance, String name) { //you need a level too?
        if (database == null)
            open();
        ContentValues values = new ContentValues();
        values.put(HighscoresDB.COLUMN_LEVEL, level); //add the level too?

        //new
        values.put(HighscoresDB.COLUMN_DATE, date); //add the new fields :-)



        values.put(HighscoresDB.COLUMN_PLAYER, name); //Game.playerName
        values.put(HighscoresDB.COLUMN_RATING, performance);

        values.put(HighscoresDB.COLUMN_MOVES, moves); //old



        Log.d("null", values.toString());
        long insertId = database.insert(HighscoresDB.TABLE_COMMENTS, null, values);
        Cursor cursor = database.query(HighscoresDB.TABLE_COMMENTS,
                allColumns, HighscoresDB.COLUMN_LEVEL + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst(); //sketchy below.
       // HighscoresItem newComment = cursorToComment(cursor);
        cursor.close();
       // return newComment;
    }

    public void sort(){
        dbHelper.sort(database);
    }

    public List<HighscoresItem> getAllRecords() {
        List<HighscoresItem> comments = new ArrayList<HighscoresItem>();

        //Cursor cursor = database.query(highscoresDB.TABLE_COMMENTS, null, null, null, null, highscoresDB.COLUMN_LEVEL+" DESC", null);
        Cursor cursor = database.rawQuery("SELECT * FROM HIGHSCORES " + "ORDER BY " + HighscoresDB.COLUMN_LEVEL + " ASC, " + HighscoresDB.COLUMN_MOVES + " ASC", null);
        //Cursor cursor = database.query(highscoresDB.TABLE_COMMENTS, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            HighscoresItem comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    public void reset(){
        //db = new
        dbHelper.onUpgrade(database, 2, 3); //not db!
    }

    private HighscoresItem cursorToComment(Cursor cursor) {
        HighscoresItem item = new HighscoresItem();
        item.setLevel(cursor.getInt(1));
        //item.setId(cursor.getLong(0)); //1 & 2
        Log.d("COMMENT", cursor.getString(2));

        item.setDate(cursor.getString(2));
        item.setPlayer(cursor.getString(3));
        item.setPerformanceRating(Double.parseDouble(cursor.getString(4)));
        item.setMoves(Integer.parseInt(cursor.getString(5)));
        return item;
    }
}
