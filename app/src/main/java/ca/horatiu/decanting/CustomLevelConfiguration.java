package ca.horatiu.decanting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Arrays;

/**
 * This class generates custom levels and verifies if they are valid.
 * @author Horatiu Lazu
 */
public class CustomLevelConfiguration extends AppCompatActivity {

    /** This method is called when the activity is called.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_level_configuration);
    }

    /** This method is called on the click of a button.
     * It reads values from the textboxes and sorts them, then verifies if the level is valid or not.
     * If it is not valid, then a toast appears.
     * @param view
     */
    public void generate(View view){
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
        Scenario scenario = new Scenario(numJugs, target); //go backwards to retain order!
        for(int x = 0; x < scenario.jugs.length; x++){
            scenario.jugs[x] = new Jug(x, arr[arr.length-x-1]); //build up everything
        }
        SolutionSolver verify = new SolutionSolver(scenario.jugs, target);
        if (verify.getMinSteps() <= SolutionSolver.MAX_MOVES_PERMITTED && verify.getMinSteps() != -1){
            Intent playGame = new Intent(this, Game.class);
            playGame.putExtra("Scenario", scenario);
            playGame.putExtra("LowestMoveCount", verify.getMinSteps());
            playGame.putExtra("LevelNumber", -1);
            startActivity(playGame);
        }
        else{
            Toast.makeText(getApplicationContext(), "This problem is impossible to solve. Please change your parameters and try again.", Toast.LENGTH_LONG).show();
        }
    }

    /** This method generates a random level. It uses Math.random() and the solver class.
     * @param view View This is the view.
     */
    public void generateRandom(View view){
        while(true){
            int a = (int)(Math.random()*12)+1;
            int b = (int)(Math.random()*12)+1;
            int c = (int)(Math.random()*12)+1;
            int d = (int)(Math.random()*12)+1;
            boolean [][][][][] dp = new boolean[13][13][13][13][13]; //efficiency!

            int target = (int)(Math.random()*Math.max(Math.max(Math.max(a, b), c), d))+1; //limit range. Impossible to solve otherwise!
            if (a ==b || a == c || a == d || a == target || b == c || b == d || b == target || c == d || c == target || d == target || dp[a][b][c][d][target]){ //alternatively use a HashMap
                continue;
            }
            dp[a][b][c][d][target] = true;
            int shouldBeOne = (int)(Math.random()*10)+1; //10% chance of it being a 1
            if (shouldBeOne != 1)
                continue;
            Scenario scenario = new Scenario(4, target);
            scenario.jugs[0] = new Jug(0, a);
            scenario.jugs[1] = new Jug(1, b);
            scenario.jugs[2] = new Jug(2, c);
            scenario.jugs[3] = new Jug(3, d);
            SolutionSolver verify = new SolutionSolver(scenario.jugs, target);
            if (verify.getMinSteps() <= SolutionSolver.MAX_MOVES_PERMITTED && verify.getMinSteps() != -1 && verify.getMinSteps() >= SolutionSolver.MIN_MOVES_REQUIRED){
                Intent playGame = new Intent(this, Game.class);
                playGame.putExtra("Scenario", scenario);
                playGame.putExtra("LowestMoveCount", verify.getMinSteps());
                playGame.putExtra("LevelNumber", -1);
                startActivity(playGame);
                return;
            }
        }
    }
}
