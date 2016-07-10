package ca.horatiu.decanting;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.database.sqlite.*;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Highscores extends AppCompatActivity {
    private HighscoresDataSource datasource = new HighscoresDataSource(this);
    public static Stack<HighscoresItem> toAdd = new Stack<>();
    public static int [] MIN_MOVES_SOLUTIONS = new int [] {2, 6, 10, 12, 14, 10, 18, 13, 5, 9};

    public HighscoresDataSource getDatasource(){
        return datasource;
    }

    public Highscores(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        TableLayout table = (TableLayout) findViewById(R.id.table);


        datasource = new HighscoresDataSource(this);
        datasource.open();

        //datasource.reset();
        while(!toAdd.isEmpty()){
            HighscoresItem temp = toAdd.pop();
            datasource.addScore(temp.getMoves() + "", temp.getLevel() + "", temp.getDate() + "", temp.getPerformanceRating() + "", temp.getPlayer() + "");
        }
        //datasource.addScore("32", "1", "2016/09/09", "4.5");
        /*datasource.reset();
        datasource.addScore("15", "1");
        datasource.addScore("7", "3");
        datasource.addScore("16", "3");
        datasource.addScore("2", "3");
        datasource.addScore("16", "4");
        datasource.addScore("16", "2");*/

        for(HighscoresItem a : datasource.getAllRecords()) {
            Log.d("Moves: ", " Level: " + a.getLevel() + " | Moves: " + a.getMoves() + " |Date: " + a.getDate() + " | Score:" + a.getPerformanceRating() + "| Name: " + a.getPlayer());
            TableRow tr = new TableRow(this);
            tr.setGravity(Gravity.CENTER_VERTICAL);

            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView levelInfo = new TextView(this);
            levelInfo.setText("Level " + a.getLevel());
            levelInfo.setPadding(0, 0, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()), 0);
            if (Build.VERSION.SDK_INT < 23) {
                levelInfo.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Material_Medium);
            } else {
                levelInfo.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
            }

            //levelInfo.setTextAppearance(R.style.AppTheme);
            levelInfo.setLayoutParams(new TableRow.LayoutParams(1));
            //levelInfo.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));


            TextView playerInfo = new TextView(this);
            playerInfo.setText(a.getPlayer());
            playerInfo.setPadding((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()), 0, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()), 0);
            playerInfo.setLayoutParams(new TableRow.LayoutParams(2));

            if (Build.VERSION.SDK_INT < 23) {
                playerInfo.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Material_Medium);
            } else {
                playerInfo.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
            }

            TextView movesInfo = new TextView(this);
            movesInfo.setText(a.getMoves() + ((a.getMoves() == 1) ? (" move") : (" moves")));
            movesInfo.setPadding((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()), 0, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()), 0);
            movesInfo.setLayoutParams(new TableRow.LayoutParams(3));

            if (Build.VERSION.SDK_INT < 23) {
                movesInfo.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Material_Medium);
            } else {
                movesInfo.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
            }

            TextView dateInfo = new TextView(this);
            dateInfo.setText(a.getDate());
            dateInfo.setPadding((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()), 0, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()), 0);
            dateInfo.setLayoutParams(new TableRow.LayoutParams(4));

            if (Build.VERSION.SDK_INT < 23) {
                dateInfo.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Material_Medium);
            } else {
                dateInfo.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
            }

           // playerInfo.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            RatingBar rating = new RatingBar(this);
            rating.setNumStars(5);
            rating.setRating((float)a.getPerformanceRating());
            rating.setLayoutParams(new TableRow.LayoutParams(5));

            /* Add Button to row. */
            tr.addView(levelInfo);
            tr.addView(playerInfo);
            tr.addView(movesInfo);
            tr.addView(dateInfo);
            tr.addView(rating);
            /* Add row to TableLayout. */
            //tr.setBackgroundResource(R.drawable.sf_gradient_03);
            table.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }

    }

    private void resetScores(){
        datasource.reset();
    }
}
