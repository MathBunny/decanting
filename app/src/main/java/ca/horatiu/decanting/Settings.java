package ca.horatiu.decanting;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ((EditText)findViewById(R.id.generatorDifficulty)).setText(SolutionSolver.MIN_MOVES_REQUIRED + "");
        ((EditText)findViewById(R.id.playerName)).setText(Game.playerName);
    }

    public void adjustPlayerName(View v){
        Game.playerName = ((EditText)findViewById(R.id.playerName)).getText().toString();
        Toast.makeText(getApplicationContext(), "Username changed to " + Game.playerName, Toast.LENGTH_LONG).show();

        SharedPreferences settings = getSharedPreferences("playerName", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("difficulty", Game.playerName);
        editor.commit();
    }


    public void adjustDifficulty(View v){
        try{
            int difficulty = Integer.parseInt(((EditText)findViewById(R.id.generatorDifficulty)).getText().toString());
            if (difficulty > 15){
                Toast.makeText(getApplicationContext(), "This is too difficult!", Toast.LENGTH_LONG).show();
            }
            else if (difficulty < 3){
                Toast.makeText(getApplicationContext(), "This is too easy!", Toast.LENGTH_LONG).show();
            }
            else{
                SolutionSolver.MIN_MOVES_REQUIRED = difficulty;
                Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_LONG).show();

                SharedPreferences settings = getSharedPreferences("difficulty", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("difficulty", difficulty);
                editor.commit();
            }
        }
        catch(NumberFormatException e){
            Toast.makeText(getApplicationContext(), "Error: Please enter a valid number!", Toast.LENGTH_LONG).show();
        }
    }
}
