package ca.horatiu.decanting;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * This activity is the main starting activity of the app.
 * @author Horatiu Lazu
 */
public class MainActivity extends AppCompatActivity {
    Intent svc;
    static Highscores highscores = new Highscores();

    /**
     * This method sets up the content view.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences("playerName", 0);
        Game.playerName = settings.getString("playerName", "Anonymous");
        settings = getSharedPreferences("difficulty", 0);
        SolutionSolver.MIN_MOVES_REQUIRED = settings.getInt("difficulty",5);
    }

    /** This method does a call to the highscores activity.
     * @param v This is the view passed in the onClick event
     */
    public void highscores(View v){
        Intent openHighscores = new Intent(this, Highscores.class);
        startActivity(openHighscores);
    }

    /** This method does a call to the about selection activity.
     * @param v This is the view passed in the onClick event
     */
    public void about(View v){
        Intent openAbout = new Intent(this, About.class);
        startActivity(openAbout);
    }

    /** This method opens settings.
     * @param v This is the view passed in the onClick event
     */
    public void settings(View v){
        Intent openSettings = new Intent(this, Settings.class);
        startActivity(openSettings);
    }

    /** This method does a call to the level selection activity.
     * @param v This is the view passed in the onClick event
     */
    public void play(View v){
        Intent levelSelection = new Intent(this, LevelSelection.class);
        startActivity(levelSelection); //open level selection
    }

    @Override
    public void onStop(){ //better
        super.onStop();
        //stopService(svc);
    }

    @Override
    public void onDestroy(){ //better
        super.onDestroy();
        //stopService(svc);
    }
}
