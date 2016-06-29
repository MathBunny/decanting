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
        playGame.putExtra("Scenario", scenario);
        startActivity(playGame);
    }

    public void levelTwo(View view){
        Intent playGame = new Intent(this, Game.class);
        Scenario scenario = new Scenario(2, 4); //lol?
        scenario.jugs[0] = new Jug(0, 3);
        scenario.jugs[1] = new Jug(1, 5);
        playGame.putExtra("Scenario", scenario);
        startActivity(playGame);
    }

    public void levelThree(View view){
        Intent playGame = new Intent(this, Game.class);
        Scenario scenario = new Scenario(2, 4); //lol?
        scenario.jugs[0] = new Jug(0, 8);
        scenario.jugs[1] = new Jug(1, 5);
        playGame.putExtra("Scenario", scenario);
        startActivity(playGame);
    }

    public void levelFour(View view){
        Intent playGame = new Intent(this, Game.class);
        Scenario scenario = new Scenario(2, 5); //lol?
        scenario.jugs[0] = new Jug(0, 11);
        scenario.jugs[1] = new Jug(1, 7);
        playGame.putExtra("Scenario", scenario);
        startActivity(playGame);
    }

    public void levelFive(View view){
        Intent playGame = new Intent(this, Game.class);
        Scenario scenario = new Scenario(2, 8); //lol?
        scenario.jugs[0] = new Jug(0, 11);
        scenario.jugs[1] = new Jug(1, 9);
        playGame.putExtra("Scenario", scenario);
        startActivity(playGame);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}
