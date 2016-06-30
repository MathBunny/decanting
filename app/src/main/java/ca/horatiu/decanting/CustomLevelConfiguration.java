package ca.horatiu.decanting;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;

public class CustomLevelConfiguration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_level_configuration);
    }

    public void generate(View view){
        //verify if it's possible...
        int [] arr =  new int[4];
        arr[0] = Integer.parseInt(((EditText)findViewById(R.id.textbox1)).getText().toString());
        arr[1] = Integer.parseInt(((EditText)findViewById(R.id.textbox2)).getText().toString());
        arr[2] = Integer.parseInt(((EditText)findViewById(R.id.textbox3)).getText().toString());
        arr[3] = Integer.parseInt(((EditText)findViewById(R.id.textbox4)).getText().toString());
        int arrSize = 0;
        Arrays.sort(arr);
        for(int x = arr.length-1; x >=0; x--)
            if (arr[x] != 0)
                arrSize++;
            else
                break;
        int numJugs = arrSize;
        int target = Integer.parseInt(((EditText)findViewById(R.id.targetCapacity)).getText().toString());
        Scenario scenario = new Scenario(numJugs, target);
        //go backwards to retain order!
        for(int x = 0; x < scenario.jugs.length; x++){
            scenario.jugs[x] = new Jug(x, arr[arr.length-x-1]); //build up everything.
        }
        SolutionSolver verify = new SolutionSolver(scenario.jugs, target);
        if (verify.getMinSteps() <= SolutionSolver.MAX_MOVES_PERMITTED && verify.getMinSteps() != -1){
            //works
            Log.d("Works", "Works" + verify.getMinSteps());
            Intent playGame = new Intent(this, Game.class);

            playGame.putExtra("Scenario", scenario);
            playGame.putExtra("LowestMoveCount", verify.getMinSteps());
            startActivity(playGame);
        }
        else{
            Log.d("Works", "Nope lol");
            Toast.makeText(getApplicationContext(), "This problem is impossible to solve. Please change your parameters and try again.", Toast.LENGTH_LONG).show();
        }
    }
}