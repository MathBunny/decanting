package ca.horatiu.decanting;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
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

    public GameRenderer(Context context, int numJugs) {
        super(context);
        drawPaint = new Paint();
        drawPaint.setColor(Color.parseColor("#f0f0f0"));
        drawPaint.setAntiAlias(true);
        setOnMeasureCallback();
        this.numJugs = numJugs;
    }

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
        for(int x = 1; x <= 8; x++) {
            canvas.drawLine(widthStep * (int) ((x + 1) / 2) + ((x % 2 == 0 && x != 2 && x != 8) ? (-GAP) : (0)), heightStep * 4, widthStep * (int)((x + 1) / 2) + ((x % 2 == 0 && x != 2 && x != 8) ? (-GAP) : (0)), heightStep * 2, drawPaint);
            Log.d("Ok", widthStep * (int) ((x + 1) / 2) + ((x % 2 == 0) ? (-5) : (0)) + "");
        }

        Log.d("Jugs", numJugs + "");
        //draw the + and minus lol
        for(int x = 0; x < numJugs; x++){
            //compute the x coordinate
            drawPaint.setColor(Color.BLACK);
            int xCordMinus = widthStep * (x+1)+(widthStep/3);
            int yCordMinus = heightStep * 3;
            //drawPaint.setTypeface(Typeface.create("Arial",Typeface.ITALIC));

            canvas.drawText("djfdlksjflsdkj", xCordMinus, yCordMinus, drawPaint);
            Log.d("loc", xCordMinus + " " + yCordMinus);
        }

        //draw the base value
        //height = ???
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
