package ca.horatiu.decanting;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Highscores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);
    }

    public void updateScores(String dbName, int moves, int seconds){
        SQLiteDatabase mydatabase = openOrCreateDatabase("your database name",MODE_PRIVATE,null);
    }
}
