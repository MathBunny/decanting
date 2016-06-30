package ca.horatiu.decanting;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by Horatiu on 27/06/2016.
 */
public class GameRenderer extends View {
    private int width;
    private int height;
    private int numJugs;
    private Paint drawPaint;
    Canvas canvas;
    final int GAP = 20;
    private Handler h;
    private final int FRAME_RATE = 2 << 20;
    private final int SPEED = 1;
    Scenario scenario;
    int maxCapacity;
    int [] capacity;
    int [] capacityDrawn;
    boolean [] highlighted;
    boolean hasHighlighted = false;
    private int moves = 0;
    private int target;
    private Game game;

    int radius = 0;

    public GameRenderer(Context context, int numJugs, Scenario scenario) { //numJugs is useless!
        super(context);
        this.game = (Game)context;
        drawPaint = new Paint();
        drawPaint.setColor(Color.parseColor("#f0f0f0"));
        drawPaint.setAntiAlias(true);
        setOnMeasureCallback();
        this.numJugs = scenario.jugs.length;
        h = new Handler();
        this.scenario = scenario;
        this.target = scenario.getTargetCapacity();

        capacity = new int[numJugs];
        highlighted = new boolean[numJugs];
        capacityDrawn = new int[numJugs];

        for(int x = 0; x  < scenario.jugs.length; x++){
            if (scenario.jugs[x].getMaxCapacity() > maxCapacity)
                maxCapacity = scenario.jugs[x].getMaxCapacity();
        }
    }

    public void tapped(int x){
        int jug = (x/(getWidth()/(numJugs+2)))-1; //(jugs+2)
        Log.d("Tap", "Tapped! Jug: " + jug + " ");
        if (hasHighlighted){
            if (jug >= 0 && jug < numJugs){
                if (!highlighted[jug]){
                    //swap these two :-)
                    for(int y = 0; y < scenario.jugs.length; y++){
                        if (jug != y && highlighted[y]){
                            //swap this one...
                            scenario.jugs[y].pour(scenario.jugs[jug]); //is this ok?
                            Log.d("Tap", "Pour from " + x + " to: " + y);
                            highlighted[y] = false;
                            moves++;
                        }
                    }
                }
                hasHighlighted = false;
                highlighted[jug] = false;
            }
        }
        else{
            if (jug >= 0 && jug < numJugs){
                hasHighlighted = true;
                highlighted[jug] = true; //highlight now!
            }
        }
        invalidate();
    }

    public void drawText(){
        drawPaint.setColor(Color.parseColor("#2196f3"));
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        //you have the pixels now you need to figure out the size...
        drawPaint.setTextSize(pixels); //1000? lol
        canvas.drawText("Goal: " + scenario.getTargetCapacity() + ", Moves: " + moves, 0, 30, drawPaint);

        /*
        for(int x = 0; x < scenario.jugs.length; x++) //I dislike these constants :(
            canvas.drawText(scenario.jugs[x].getVolume() + "", (width/(numJugs+2)) * (x+1), getHeight()-20, drawPaint); //u should put the current capacity :)

        for(int x = 0; x < scenario.jugs.length; x++)
            canvas.drawText(scenario.jugs[x].getMaxCapacity() + "", (width/(numJugs+2)) * (x+1), getHeight()/2, drawPaint);
        */
        for(int x = 0; x < scenario.jugs.length; x++) //I dislike these constants :(
            canvas.drawText("Max: " + scenario.jugs[x].getMaxCapacity() + ", Current: " + scenario.jugs[x].getVolume(), (width/(numJugs+2)) * (x+1), getHeight()/2, drawPaint); //u should put the current capacity :)

    }

    public void drawHighlights(){
        int heightStep = height/4;
        int widthStep = (width/(numJugs+2));
        drawPaint.setColor(Color.YELLOW);

        drawPaint.setStrokeWidth(10);
        int count = 0;
        for(int x = 1; x <= (numJugs+1)*2-1; x++) {
            int jug = (x)/2 - 1;
            Log.d("Debug", jug + "");
            if (jug < numJugs && jug >= 0 &&  highlighted[jug]) {
                //canvas.drawLine(widthStep * (int) ((x + 1) / 2) + ((x % 4 == 0 && x != 2 || x == 7) ? (-GAP) : (0)), heightStep * 4, widthStep * (int) ((x + 1) / 2) + ((x % 4 == 0 && x != 2 || x == 7) ? (-GAP) : (0)), heightStep * 2, drawPaint);
                canvas.drawLine(widthStep * (int) ((x + 1) / 2) + ((count==0) ? (0) : (-GAP)), heightStep * 4, widthStep * (int) ((x + 1) / 2) + ((count==0) ? (0) : (-GAP)), heightStep * 2, drawPaint);
                count++;
                if (count == 2)
                    count = 0;
                Log.d("Debug", "YES!!" + jug);
            }
        }
    }

    public void verifyIfWon(){
        for(int x = 0; x < scenario.jugs.length; x++){
            if (scenario.jugs[x].getVolume() == target){
                game.finished(moves, game.getLeastMoves()); //temporary!
                return;
            }
        }
    }

    public void gesture(int x, int y, int dx, int dy){
        //calculate the real jug
        int jug = (x/(getWidth()/(numJugs+2)))-1; //(jugs+2)
        //Log.d("Jug", jug + " " + dy + " =change");
        if (jug >= 0 && jug < numJugs && y > getHeight()/4){
            if (dy > 0){ //swipe up!
                if (scenario.jugs[jug].getVolume() != scenario.jugs[jug].getMaxCapacity()) {
                    scenario.jugs[jug].setVolume(scenario.jugs[jug].getMaxCapacity()); //MAX! update the jug too?
                    moves++;
                }
            }
            else{
                if (scenario.jugs[jug].getVolume() != 0) {
                    scenario.jugs[jug].setVolume(0);
                    moves++;
                }
            }
        }
        //Log.d("Capacity:", capacity[jug] + " vs " + scenario.jugs[jug].getVolume());
        invalidate();
    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        updateCapacities();
        drawBackground(canvas);
        this.height = getHeight();
        this.width = getWidth();
        drawHighlights();
        drawText();
        verifyIfWon();
    }

    public void updateCapacities(){
        for(int x = 0; x < scenario.jugs.length; x++){
            capacity[x] = (int) (((double)scenario.jugs[x].getVolume()/maxCapacity)*((getHeight()*0.5)));
            Log.d("Updated", capacity[x] + "");
        }
        //Log.d("Height: ", getHeight() + "");
    }

    public void drawBackground(Canvas canvas){
        int widthStep = (width/(numJugs+2));
        int heightStep = height/4;
        drawPaint.setColor(Color.parseColor("#9e9e9e"));

        drawPaint.setStrokeWidth(10);
        for(int x = 1; x <= (numJugs+1)*2-1; x++) {
            //canvas.drawLine(widthStep * (int) ((x + 1) / 2) + ((x % 2 == 0 && x != 2 && x != 8) ? (-GAP) : (0)), heightStep * 4, widthStep * (int)((x + 1) / 2) + ((x % 2 == 0 && x != 2 && x != 8) ? (-GAP) : (0)), heightStep * 2, drawPaint);
            canvas.drawLine(widthStep * (int) ((x + 1) / 2) + ((x % 2 == 0 && x != 2 ||  x == ((numJugs==3) ? (7) : (numJugs==4) ? (9) : (5)) ? (-GAP) : (0))), heightStep * 4, widthStep * (int)((x + 1) / 2) + ((x % 2 == 0 && x != 2 || x == ((numJugs==3) ? (7) : (numJugs==4) ? (9) : (5))) ? (-GAP) : (0)), heightStep * 2, drawPaint);
        } //is it 5?

        drawPaint.setColor(Color.parseColor("#03a9f4")); //blue? lol
        //draw the base value
        for(int x = 1; x <= capacityDrawn.length; x++){
            //draw the rectangle
            int x1 = widthStep * (int) (x) + 5;
            int x2 = widthStep * (int) (x+1) -GAP - 5;
            int y1 = heightStep*4;
            int y2 = heightStep*4-capacityDrawn[x-1];
            canvas.drawRect(x1, y2, x2, y1, drawPaint);
        }
        for(int x = 0; x < capacity.length; x++){
            if (capacity[x] < capacityDrawn[x]){
                capacityDrawn[x]-=SPEED;
            }
            else if (capacity[x] > capacityDrawn[x]){
                capacityDrawn[x]+=SPEED;
            }
            //Log.d("Capacity drawn: ", "Capacity drawn" + capacityDrawn[x] + "");
        }
        //height = ???
        drawHighlights();
        h.postDelayed(r, FRAME_RATE);
        invalidate();

    }

    public void fillRect(int jugNumber){
        double shouldFillPercentage = (double)(scenario.jugs[jugNumber].getMaxCapacity()/maxCapacity); //ie, 0.5 .. now multiply height by 0.5 and only go up that much!


    }

    private void setOnMeasureCallback() {
        ViewTreeObserver vto = getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                removeOnGlobalLayoutListener(this);
                width = getMeasuredWidth();
                height = getMeasuredHeight();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void removeOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT < 16) {
            getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        } else {
            getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }
    }
}
