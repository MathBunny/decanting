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
        Scenario scenario = new Scenario(3);
        scenario.jugs[0] = new Jug(0, 3);
        scenario.jugs[0].setVolume(0);
        scenario.jugs[1] = new Jug(0, 5);
        scenario.jugs[1].setVolume(1);
        scenario.jugs[2] =  new Jug(0, 7);
        scenario.jugs[2].setVolume(7);

        playGame.putExtra("Scenario", scenario);
        startActivity(playGame);
    }
}
