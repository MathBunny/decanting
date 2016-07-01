package ca.horatiu.decanting;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import java.io.Serializable;

public class Game extends AppCompatActivity implements
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;
    private GameRenderer renderer;
    private int leastMoves;

    int numJugs = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Scenario scenario = (Scenario)getIntent().getSerializableExtra("Scenario");
        leastMoves = (int)getIntent().getSerializableExtra("LowestMoveCount");
        renderer = new GameRenderer(this, numJugs, scenario);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            renderer.setBackground(getResources().getDrawable(R.drawable.background_grey));
        }

        setContentView(renderer);

        mDetector = new GestureDetectorCompat(this,this);
        mDetector.setOnDoubleTapListener(this);
    }

    public int getLeastMoves(){
        return leastMoves;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        Log.d(DEBUG_TAG,"onDown: " + event.toString());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        renderer.gesture((int)e1.getX(), (int)e1.getY(), (int)distanceX, (int)distanceY);
        Log.d(DEBUG_TAG, "onScroll: " + e1.toString()+e2.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDoubleTap: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDoubleTapEvent: " + event.toString());
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapConfirmed: " + event.toString());
        //single tap here...
        int jug = ((int)event.getX()/(renderer.getWidth()/(numJugs+2)))-1;

        if (jug < 0 && (int)event.getY() < renderer.getHeight()/2){
            Toast.makeText(getApplicationContext(), "Swipe up and down on the jugs to fill and empty, tap the source jug and destination to pour into another. Try to fill one jug to the top capacity.", Toast.LENGTH_LONG).show();
        }
        renderer.tapped((int)event.getX());

        return true;
    }

    public void finished(int moves, int minimumMoves){
        Intent playGame = new Intent(this, CompletedLevel.class);
        Moves movesIntent = new Moves(moves, minimumMoves);
        playGame.putExtra("Moves", movesIntent);
        startActivity(playGame);
    }

}
