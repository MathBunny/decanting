package ca.horatiu.decanting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CompletedLevel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_level);
        Moves moves = (Moves)getIntent().getSerializableExtra("Moves");
        updateText(moves);
    }

    private void updateText(Moves moves){
        TextView movesMessage = (TextView)findViewById(R.id.textView);
        movesMessage.setText("You finished in " + moves.getMoves() + " moves. Best solution is " + moves.getBestMoves() + " moves.");
    }

    public void levelSelection(View view){
        Intent menu = new Intent(this, LevelSelection.class);
        startActivity(menu);
    }
}
