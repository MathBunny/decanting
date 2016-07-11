package ca.horatiu.decanting;

import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.Stack;

/**
 * This class handles highscores and draws them with a nice UI.
 * @author Horatiu Lazu
 */

public class Highscores extends AppCompatActivity {
    /** datasource HighscoresDataSource This is used to link with the SQLite database. */
    private HighscoresDataSource datasource = new HighscoresDataSource(this);
    /** This variable is used to add items to the SQLite database from other locations. */
    public static Stack<HighscoresItem> toAdd = new Stack<>();

    /**
     * This method resets the database, with a confirmation message box.
     * @param view View This is a view reference.
     */
    public void resetDB(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you want to clear highscores?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                datasource.reset();
                finish();
                startActivity(getIntent());
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    public Highscores(){}

    /**
     * This method sets up the UI and adds the table rows from the SQLite database.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        /* Setup a table and open the SQLite database */
        TableLayout table = (TableLayout) findViewById(R.id.table);
        datasource = new HighscoresDataSource(this);
        datasource.open();

        /* Add the scores from the stack to the database */
        while(!toAdd.isEmpty()){
            HighscoresItem temp = toAdd.pop();
            try {
                datasource.addScore(temp.getMoves() + "", temp.getLevel() + "", temp.getDate() + "", temp.getPerformanceRating() + "", temp.getPlayer() + "");
            }
            catch(Exception e){
                datasource.reset();
                datasource.create();
                Log.d("Created", "Created)");
                datasource.addScore(temp.getMoves() + "", temp.getLevel() + "", temp.getDate() + "", temp.getPerformanceRating() + "", temp.getPlayer() + "");
            }
        }

        //This is in case there was no DB created.
        try{
            datasource.getAllRecords();
        }
        catch(Exception e){datasource.create();}

        /* Create a row for each entry */
        for(HighscoresItem a : datasource.getAllRecords()) {
            TableRow tr = new TableRow(this);
            tr.setGravity(Gravity.CENTER_VERTICAL);

            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            /* Add the level number */
            TextView levelInfo = new TextView(this);
            levelInfo.setText("Level " + a.getLevel());
            levelInfo.setPadding(0, 0, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()), 0);
            if (Build.VERSION.SDK_INT < 23) {
                levelInfo.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Material_Medium);
            } else {
                levelInfo.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
            }
            levelInfo.setLayoutParams(new TableRow.LayoutParams(1));

            /* Add the player name */
            TextView playerInfo = new TextView(this);
            playerInfo.setText((a.getPlayer().length() >= 9) ? (a.getPlayer().substring(0, 9)) : (a.getPlayer()));
            playerInfo.setPadding((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()), 0, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()), 0);
            playerInfo.setLayoutParams(new TableRow.LayoutParams(2));

            if (Build.VERSION.SDK_INT < 23) {
                playerInfo.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Material_Medium);
            } else {
                playerInfo.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
            }

            /* Add the number of moves */
            TextView movesInfo = new TextView(this);
            movesInfo.setText(a.getMoves() + ((a.getMoves() == 1) ? (" move") : (" moves")));
            movesInfo.setPadding((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()), 0, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()), 0);
            movesInfo.setLayoutParams(new TableRow.LayoutParams(3));

            if (Build.VERSION.SDK_INT < 23) {
                movesInfo.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Material_Medium);
            } else {
                movesInfo.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
            }

            /* Add the date label */
            TextView dateInfo = new TextView(this);
            dateInfo.setText(a.getDate());
            dateInfo.setPadding((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()), 0, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()), 0);
            dateInfo.setLayoutParams(new TableRow.LayoutParams(4));

            if (Build.VERSION.SDK_INT < 23) {
                dateInfo.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Material_Medium);
            } else {
                dateInfo.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
            }

            /* Add the rating bar */
            RatingBar rating = new RatingBar(this);
            rating.setNumStars(5);
            rating.setRating((float)a.getPerformanceRating());
            rating.setLayoutParams(new TableRow.LayoutParams(5));
            rating.setIsIndicator(true);
            rating.setFocusable(false);
            rating.setFocusableInTouchMode(false);

            /* Add Button to row. */
            tr.addView(levelInfo);
            tr.addView(playerInfo);
            tr.addView(movesInfo);
            tr.addView(dateInfo);
            tr.addView(rating);
            /* Add row to TableLayout. */
            table.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }

    }
}
