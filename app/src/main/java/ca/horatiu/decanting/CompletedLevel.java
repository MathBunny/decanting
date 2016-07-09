package ca.horatiu.decanting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * This activity encapsulates a completed level, and shows the winner screen.
 * @author Horatiu Lazu
 */
public class CompletedLevel extends AppCompatActivity {
    boolean once = false;
    /**
     * This method is called when the activity is called. It also gets the move information from an intent. Moreover, it sets the content view so the user can see the visuals.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_level);
        Moves moves = (Moves)getIntent().getSerializableExtra("Moves");
        updateText(moves);
    }

    /**
     * This method updates the text with the number of moves.
     * @param moves These are the number of moves, stored in a move object.
     */
    private void updateText(Moves moves){
        TextView movesMessage = (TextView)findViewById(R.id.textView);
        movesMessage.setText("You finished in " + moves.getMoves() + " moves. Best solution is " + moves.getBestMoves() + " moves.");
    }

    /**
     * This opens up the level selection screen on the click of a button.
     * @param view
     */
    public void levelSelection(View view){
        Intent menu = new Intent(this, LevelSelection.class);
        startActivity(menu);
    }

    public void retry(View v){
        onBackPressed();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!once) {
            once = true;
            onBackPressed();

        }
        Log.d("RERAN", "Going...");


        //onBackPressed();
    }
}
