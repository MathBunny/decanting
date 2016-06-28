package ca.horatiu.decanting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.Serializable;

public class Game extends AppCompatActivity {
    int numJugs = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Scenario scenario = (Scenario)getIntent().getSerializableExtra("Scenario");
        setContentView(new GameRenderer(this, numJugs, scenario));
    }
}
