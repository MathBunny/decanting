package ca.horatiu.decanting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * This is the about screen.
 * @author Horatiu Lazu
 * @version 1.0
 */
public class About extends AppCompatActivity {

    /**
     * This method is called when the activity is created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
}
