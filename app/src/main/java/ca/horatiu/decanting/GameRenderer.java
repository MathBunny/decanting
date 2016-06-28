package ca.horatiu.decanting;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
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
    private final int FRAME_RATE = 30;
    Scenario scenario;
    int maxCapacity;
    int [] capacity;
    int [] capacityDrawn;

    int radius = 0;

    public GameRenderer(Context context, int numJugs) {
        super(context);
        drawPaint = new Paint();
        drawPaint.setColor(Color.parseColor("#f0f0f0"));
        drawPaint.setAntiAlias(true);
        setOnMeasureCallback();
        this.numJugs = numJugs;
        h = new Handler();
        scenario = new Scenario(numJugs);
        for(int x = 0; x < scenario.jugs.length; x++)
            scenario.jugs[x] = new Jug(x, 10); //10?

        for(int x = 0; x  < scenario.jugs.length; x++){
            if (scenario.jugs[x].getMaxCapacity() > maxCapacity)
                maxCapacity = scenario.jugs[x].getMaxCapacity();
        }
        capacity = new int[numJugs];
        capacityDrawn = new int[numJugs];
        capacity[0] = 200;
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
        drawBackground(canvas);
    }

    public void drawBackground(Canvas canvas){
        int widthStep = (width/(numJugs+2));
        int heightStep = height/4;
        drawPaint.setColor(Color.parseColor("#9e9e9e"));

        drawPaint.setStrokeWidth(10);
        for(int x = 1; x <= (numJugs+1)*2; x++) {
            canvas.drawLine(widthStep * (int) ((x + 1) / 2) + ((x % 2 == 0 && x != 2 && x != 8) ? (-GAP) : (0)), heightStep * 4, widthStep * (int)((x + 1) / 2) + ((x % 2 == 0 && x != 2 && x != 8) ? (-GAP) : (0)), heightStep * 2, drawPaint);
            Log.d("Ok", widthStep * (int) ((x + 1) / 2) + ((x % 2 == 0) ? (-5) : (0)) + "");
        }

        drawPaint.setTextSize(250);
        //draw the + and minus lol
        /*for(int x = 0; x < numJugs; x++){
            //compute the x coordinate
            drawPaint.setColor(Color.BLACK);
            int xCordMinus = widthStep * (x+1)+(widthStep/6);
            int yCordMinus = (int)((heightStep * 2.0)/1.4);

            canvas.drawLine(xCordMinus-widthStep/6, yCordMinus, xCordMinus+widthStep/6, yCordMinus, drawPaint);

            //drawPaint.setTypeface(Typeface.create("Arial",Typeface.ITALIC));

           // canvas.drawText("-", xCordMinus, yCordMinus, drawPaint);
            Log.d("loc", xCordMinus + " " + yCordMinus);
        }*/

        drawPaint.setColor(Color.parseColor("#03a9f4")); //green
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
                capacityDrawn[x]--;
            }
            else if (capacity[x] > capacityDrawn[x]){
                capacityDrawn[x]++;
            }
        }
        //height = ???
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
