package ca.horatiu.decanting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.Serializable;

public class LevelSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selection);
    }

    public void levelOne(View view){
        Intent playGame = new Intent(this, Game.class);
        Scenario scenario = new Scenario(3, 2); //lol?
        scenario.jugs[0] = new Jug(0, 3);
        scenario.jugs[1] = new Jug(1, 5);
        scenario.jugs[2] =  new Jug(2, 7);
        SolutionSolver s = new SolutionSolver(scenario.jugs, 2);

        playGame.putExtra("Scenario", scenario);
        playGame.putExtra("LowestMoveCount", s.getMinSteps());

        startActivity(playGame);
    }

    public void levelTwo(View view){
        Intent playGame = new Intent(this, Game.class);
        Scenario scenario = new Scenario(2, 4); //lol?
        scenario.jugs[0] = new Jug(0, 3);
        scenario.jugs[1] = new Jug(1, 5);
        SolutionSolver s = new SolutionSolver(scenario.jugs, 4);

        playGame.putExtra("Scenario", scenario);
        playGame.putExtra("LowestMoveCount", s.getMinSteps());

        startActivity(playGame);
    }

    public void levelThree(View view){
        Intent playGame = new Intent(this, Game.class);
        Scenario scenario = new Scenario(2, 4); //lol?
        scenario.jugs[0] = new Jug(0, 8);
        scenario.jugs[1] = new Jug(1, 5);
        SolutionSolver s = new SolutionSolver(scenario.jugs, 4);

        playGame.putExtra("Scenario", scenario);
        playGame.putExtra("LowestMoveCount", s.getMinSteps());
        startActivity(playGame);
    }

    public void levelFour(View view){
        Intent playGame = new Intent(this, Game.class);
        Scenario scenario = new Scenario(2, 5); //lol?
        scenario.jugs[0] = new Jug(0, 11);
        scenario.jugs[1] = new Jug(1, 7);
        SolutionSolver s = new SolutionSolver(scenario.jugs, 5);

        playGame.putExtra("Scenario", scenario);
        playGame.putExtra("LowestMoveCount", s.getMinSteps());

        startActivity(playGame);
    }

    public void levelFive(View view){
        Intent playGame = new Intent(this, Game.class);
        Scenario scenario = new Scenario(2, 8); //lol?
        scenario.jugs[0] = new Jug(0, 11);
        scenario.jugs[1] = new Jug(1, 9);
        SolutionSolver s = new SolutionSolver(scenario.jugs, 8);

        playGame.putExtra("Scenario", scenario);
        playGame.putExtra("LowestMoveCount", s.getMinSteps());
        startActivity(playGame);
    }

    public void levelSix(View view){
        Intent playGame = new Intent(this, Game.class);
        Scenario scenario = new Scenario(3, 11); //lol?
        scenario.jugs[0] = new Jug(0, 8);
        scenario.jugs[1] = new Jug(1, 14);
        scenario.jugs[2] = new Jug(2, 7);
        SolutionSolver s = new SolutionSolver(scenario.jugs, 11);

        playGame.putExtra("Scenario", scenario);
        playGame.putExtra("LowestMoveCount", s.getMinSteps());
        startActivity(playGame);
    }

    public void levelSeven(View view){
        Intent playGame = new Intent(this, Game.class);
        Scenario scenario = new Scenario(2, 4); //lol?
        scenario.jugs[0] = new Jug(0, 8);
        scenario.jugs[1] = new Jug(1, 13);
        SolutionSolver s = new SolutionSolver(scenario.jugs, 4);

        playGame.putExtra("Scenario", scenario);
        playGame.putExtra("LowestMoveCount", s.getMinSteps());
        startActivity(playGame);
    }

    public void levelEight(View view){
        Intent playGame = new Intent(this, Game.class);
        Scenario scenario = new Scenario(3, 11); //lol?
        scenario.jugs[0] = new Jug(0, 12);
        scenario.jugs[1] = new Jug(1, 5);
        scenario.jugs[2] = new Jug(2, 5);
        SolutionSolver s = new SolutionSolver(scenario.jugs, 11);

        playGame.putExtra("Scenario", scenario);
        playGame.putExtra("LowestMoveCount", s.getMinSteps());
        startActivity(playGame);
    }

    public void levelNine(View view){
        Intent playGame = new Intent(this, Game.class);
        Scenario scenario = new Scenario(4, 19); //lol?
        scenario.jugs[0] = new Jug(0, 12);
        scenario.jugs[1] = new Jug(1, 5);
        scenario.jugs[2] = new Jug(2, 22);
        scenario.jugs[3] = new Jug(3, 17);
        SolutionSolver s = new SolutionSolver(scenario.jugs, 19);

        playGame.putExtra("Scenario", scenario);
        playGame.putExtra("LowestMoveCount", s.getMinSteps());
        startActivity(playGame);
    }

    public void levelTen(View view){
        Intent playGame = new Intent(this, Game.class);
        Scenario scenario = new Scenario(4, 21); //lol?
        scenario.jugs[0] = new Jug(0, 12);
        scenario.jugs[1] = new Jug(1, 19);
        scenario.jugs[2] = new Jug(2, 30);
        scenario.jugs[3] = new Jug(3, 23);
        SolutionSolver s = new SolutionSolver(scenario.jugs, 21);

        playGame.putExtra("Scenario", scenario);
        playGame.putExtra("LowestMoveCount", s.getMinSteps());
        startActivity(playGame);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void customLevel(View view){
        Intent customLevelConfigurator = new Intent(this, CustomLevelConfiguration.class);
        startActivity(customLevelConfigurator);
    }

}
