package ca.horatiu.decanting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * This activity is the main starting activity of the app.
 * @author Horatiu Lazu
 */
public class MainActivity extends AppCompatActivity {

    /**
     * This method sets up the content view.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    /** This method does a call to the level selection activity.
     * @param v This is the view passed in the onClick event
     */
    public void play(View v){
        Intent levelSelection = new Intent(this, LevelSelection.class);
        startActivity(levelSelection); //open level selection
    }
}
