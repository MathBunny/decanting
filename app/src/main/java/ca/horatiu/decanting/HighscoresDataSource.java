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
    private highscoresDB dbHelper;
    private String[] allColumns = {highscoresDB.COLUMN_LEVEL,
            highscoresDB.COLUMN_MOVES};

    public HighscoresDataSource(Context context){
        dbHelper = new highscoresDB(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addScore(String moves, String level) { //you need a level too?
        ContentValues values = new ContentValues();
        values.put(highscoresDB.COLUMN_LEVEL, level); //add the level too?
        values.put(highscoresDB.COLUMN_MOVES, moves);

        Log.d("null", values.toString());
        long insertId = database.insert(highscoresDB.TABLE_COMMENTS, null, values);
        Cursor cursor = database.query(highscoresDB.TABLE_COMMENTS,
                allColumns, highscoresDB.COLUMN_LEVEL + " = " + insertId, null,
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
        Cursor cursor = database.rawQuery("SELECT * FROM HIGHSCORES " + "ORDER BY " + highscoresDB.COLUMN_LEVEL + " ASC, " + highscoresDB.COLUMN_MOVES + " ASC", null);
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
        dbHelper.onUpgrade(database, 1, 2); //not db!
    }

    private HighscoresItem cursorToComment(Cursor cursor) {
        HighscoresItem item = new HighscoresItem();
        item.setLevel(cursor.getInt(1));
        //item.setId(cursor.getLong(0)); //1 & 2
        item.setMoves(Integer.parseInt(cursor.getString(2)));
        return item;
    }
}
