package ca.horatiu.decanting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void highscores(View v){

    }

    public void about(View v){

    }

    public void play(View v){
        Intent levelSelection = new Intent(this, LevelSelection.class);
        startActivity(levelSelection); //open level selection
    }
}
