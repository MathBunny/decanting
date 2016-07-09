package ca.horatiu.decanting;

import android.content.Intent;
import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class Game extends AppCompatActivity implements
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {
    /**  String DEBUG_TAG This is the debug tag for all gesture methods */
    private static final String DEBUG_TAG = "Gestures";
    /** mDetector GestureDetectorCompat This is used for detecting gestures */
    private GestureDetectorCompat mDetector;
    /** renderer GameRenderer This reference is made to the GameRenderer object, generating the view */
    private GameRenderer renderer;
    /** int leastMoves This is the least amount of moves that happened */
    private int leastMoves;
    /** int numJugs This is the default count of jugs */
    int numJugs = 4;
    /** Scenario levelConfiguration This is the level in case the user chooses to play again. */
    private Scenario levelConfiguration;
    /** int levelNumber This is the level number of the level.*/
    int levelNumber;

    /**
     * This method sets up the  UI for the activity, and draws the background. It also sets up the gesture detectors and initializes the least move count and renderer.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Scenario scenario = (Scenario)getIntent().getSerializableExtra("Scenario");
        for(int x = 0; x < scenario.getJugCount(); x++){
            scenario.jugs[x].setVolume(0);
        }
        levelConfiguration = (Scenario)deepClone(scenario);
        leastMoves = (int)getIntent().getSerializableExtra("LowestMoveCount");
        levelNumber = (int)getIntent().getSerializableExtra("LevelNumber");
        renderer = new GameRenderer(this, numJugs, scenario);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            renderer.setBackground(getResources().getDrawable(R.drawable.background_grey));
        }

        setContentView(renderer);

        mDetector = new GestureDetectorCompat(this,this);
        mDetector.setOnDoubleTapListener(this);
    }

    /**
     * This method makes a "deep clone" of any object it is given.
     * @param object Object This is an object reference.
     */
    public static Object deepClone(Object object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void onResume(){
        super.onResume();
        Log.d("RERAN", "RERAN 3");
    }

    public void onRestart(){
        super.onRestart();
        Log.d("RERAN", "RERAN 4");
    }

    /**
     * This method resets the fields and the volume, in case the user goes back to play again.
     */
    public void onStart(){
        super.onStart();
        Log.d("RERAN", "RERAN 2"); //reset
        for(int x = 0; x < levelConfiguration.getJugCount(); x++){
            levelConfiguration.jugs[x].setVolume(0);
        }
        renderer = new GameRenderer(this, numJugs, levelConfiguration);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            renderer.setBackground(getResources().getDrawable(R.drawable.background_grey));
        }
        setContentView(renderer);
        mDetector = new GestureDetectorCompat(this,this);
        mDetector.setOnDoubleTapListener(this);
    }

    /**
     * This method returns the least amount of moves.
     * @return int This is the least amount of moves.
     */
    public int getLeastMoves(){
        return leastMoves;
    }

    /**
     * This method is called whenever this is an onTouchEvent.
     * @param event This is the event reference variable.
     * @return boolean Indicates if there was a touch event.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    /**
     * This method is called whenever a finger was touched down.
     * @param event This variable is a reference variable.
     * @return boolean Indicates if there was a touch event.
     */
    @Override
    public boolean onDown(MotionEvent event) {
        Log.d(DEBUG_TAG,"onDown: " + event.toString());
        return true;
    }
    /**
     * This method is called whenever a fling happened.
     * @param event1 This variable is a reference variable for the first starting point.
     * @param event2 This variable is a reference variable for the second end point of the fling.
     * @return boolean Indicates if there was a fling event.
     */

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());
        return true;
    }
    /**
     * This method is called whenever a finger was touched down.
     * @param event This variable is a reference variable.
     */
    @Override
    public void onLongPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
    }
    /**
     * This method is called whenever a something was scrolled. It is used to fill jugs.
     * @param e1 This variable is a reference variable for the first starting point.
     * @param e2 This variable is a reference variable for the second end point of the fling.
     * @param distanceX This is the distance that changes x wise.
     * @param distanceY This is the distance that changes y wise.
     * @return boolean Indicates if there was a fling event.
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        renderer.gesture((int)e1.getX(), (int)e1.getY(), (int)distanceX, (int)distanceY);
        Log.d(DEBUG_TAG, "onScroll: " + e1.toString()+e2.toString());
        return true;
    }

    /**
     * This method is called whenever a show press happened.
     * @param event This is the event reference variable.
     */
    @Override
    public void onShowPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
    }

    /**
     * This method is called whenever a single tap up happened.
     * @param event This is the event reference variable.
     * @return boolean This indicates if the method was ever invoked.
     */
    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
        return true;
    }

    /**
     * This method is called whenever an object was double tapped.
     * @param event This is the event reference variable.
     * @return boolean This indicates if the method was ever invoked.
     */
    @Override
    public boolean onDoubleTap(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDoubleTap: " + event.toString());
        return true;
    }

    /**
     * This method is called whenever something is double tapped.
     * @param event This is the event reference variable.
     * @return boolean This indicates if the method was invoked.
     */
    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDoubleTapEvent: " + event.toString());
        return true;
    }

    /**
     * This method is called whenever a single tapped occurs. It verifies to see if a toast should appear.
     * @param event This is the event reference variable, which can provide details such as x and y location.
     * @return boolean This indicates if the method was invoked.
     */
    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapConfirmed: " + event.toString());
        int jug = ((int)event.getX()/(renderer.getWidth()/(numJugs+2)))-1;

        if (jug < 0 && (int)event.getY() < renderer.getHeight()/2){
            if (levelNumber == 1)
                Toast.makeText(getApplicationContext(), "Swipe up and down on the jugs to fill and empty, tap the source jug and destination to pour into another. Try to fill one jug to the top capacity.", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), "OK!", Toast.LENGTH_LONG).show();
        }
        else if (jug < 0 && (int)(event.getY()) > renderer.getHeight()/2 && (int)(event.getY()) < renderer.getHeight()){
            Toast.makeText(getApplicationContext(), "OK!", Toast.LENGTH_LONG).show();
        }
        renderer.tapped((int)event.getX());

        return true;
    }

    /**
     * This method is invoked when the game is finished. It sends the user to the winner screen.
     * @param moves This is the number of moves that occured in the game.
     * @param minimumMoves This is the minimum number of moves neccessary.
     */
    public void finished(int moves, int minimumMoves){
        Intent playGame = new Intent(this, CompletedLevel.class);
        Moves movesIntent = new Moves(moves, minimumMoves);
        playGame.putExtra("Moves", movesIntent);
        startActivity(playGame);
    }

}
