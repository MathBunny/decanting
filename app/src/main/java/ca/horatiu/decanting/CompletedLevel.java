package ca.horatiu.decanting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This activity encapsulates a completed level, and shows the winner screen. It also adds a highscores item to the stack.
 * @author Horatiu Lazu
 */
public class CompletedLevel extends AppCompatActivity {
    /**
     * This method is called when the activity is called. It also gets the move information from an intent. Moreover, it sets the content view so the user can see the visuals.
     * It also adds the highscores to the stack.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_level);
        Moves moves = (Moves)getIntent().getSerializableExtra("Moves");
        updateText(moves);
        int levelNumber = (Integer)getIntent().getSerializableExtra("LevelNumber"); //uh oh

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        double score = Math.max(0, 5-(double)(moves.getMoves() - moves.getBestMoves())/Math.min(moves.getBestMoves(), 3));
        Highscores.toAdd.push(new HighscoresItem(moves.getMoves(), levelNumber, dateFormat.format(date), score, Game.playerName));
    }

    /**
     * This method updates the text with the number of moves.
     * @param moves These are the number of moves, stored in a move object.
     */
    private void updateText(Moves moves){
        TextView movesMessage = (TextView)findViewById(R.id.congratulationsTitle);
        movesMessage.setText("You finished in " + moves.getMoves() + " moves. Best solution is " + moves.getBestMoves() + " moves.");
    }

    /**
     * This opens up the level selection screen on the click of a button.
     * @param view View This is the view for the onClick.
     */
    public void levelSelection(View view){
        Intent menu = new Intent(this, LevelSelection.class);
        startActivity(menu);
    }

    /**
     * This method is used to allow the user to retry the previous level.
     * @param v View This is used for the onClick event.
     */
    public void retry(View v){
        onBackPressed();
    }

    /**
     * This method calls the super class to go back.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
