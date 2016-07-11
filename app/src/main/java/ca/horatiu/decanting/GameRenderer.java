package ca.horatiu.decanting;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import java.util.*;

/**
 * This class handles all graphic related tasks within the main game, drawn in a canvas.
 * Created by Horatiu on 27/06/2016.
 */
public class GameRenderer extends View {
    /** width int This is the width of the view. */
    private int width;
    /** height int This is the height of the view. */
    private int height;
    /** numJugs int This is the number of jugs. */
    private int numJugs;
    /** drawPaint Paint This is the paint used for draw commands. */
    private Paint drawPaint;
    /** canvas Canvas This is the canvas reference */
    private Canvas canvas;
    /** GAP int This is the gap between two jugs. */
    final int GAP = 20;
    /** TOP_PADDING int This is the padding from the top of the screen. */
    final int TOP_PADDING = 150;
    /** h Handler This is used for threading (animations). */
    private Handler h;
    /** FRAME_RATE int This is the frame rate. */
    private final int FRAME_RATE = 60;
    /** SPEED int This is the speed of the jugs filling/emptying. */
    private final int SPEED = 5;
    /** scenario Scenario This is the scenario / level. */
    private  Scenario scenario;
    /** maxCapacity int This is the largest capacity of one of the jugs. */
    private int maxCapacity;
    /** capacity int [] These are the capacities of the jugs. */
    private int [] capacity;
    /** capacityDrawn int [] These are the drawn capacities (so far) of the jugs. */
    private int [] capacityDrawn;
    /** highlighted boolean [] These are the jugs which are highlighted. */
    private boolean [] highlighted;
    /** hasHighlighted boolean This indicates if anything is highlighted. */
    private boolean hasHighlighted = false;
    /** moves int This is the number of moves. */
    private int moves = 0;
    /** target int This is the target capacity. */
    private int target;
    /** game Game This is the game reference. */
    private Game game;
    /** hasWon boolean This indicates if a player has won already. */
    private boolean hasWon = false;
    /** undo Stack<> This holds the game moves so moves can be easily removed. */
    Stack<Action> undo = new Stack<>();


    /**
     * This is the class constructor for the game renderer.
     * @param context Context This is a context.
     * @param numJugs int This is the number of jugs present.
     * @param scenario Scenario This is the scenario with the jug preferences.
     */
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

    /** This method is called when something is tapped.
     * @param x int This is the x coordinate.
     */
    public void tapped(int x){
        int jug = (x/(getWidth()/(numJugs+2)))-1; //(jugs+2)

        if (hasHighlighted){
            if (jug >= 0 && jug < numJugs){
                if (!highlighted[jug]){ //swap these two :-)
                    for(int y = 0; y < scenario.jugs.length; y++){
                        if (jug != y && highlighted[y]){
                            undo.push(new Action(y, scenario.jugs[y].getVolume(), jug, scenario.jugs[jug].getVolume()));
                            scenario.jugs[y].pour(scenario.jugs[jug]);
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

    /** This method draws the text on the screen (undo button, help and the level requirements */
    public void drawText(){
        drawPaint.setColor(Color.parseColor("#2196f3"));
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, getResources().getDisplayMetrics());
        drawPaint.setTextAlign(Paint.Align.RIGHT);
        drawPaint.setTextSize(pixels); //1000? lol

        canvas.drawText(moves + ((moves==1) ? " move" : " moves"), getWidth()-50, TOP_PADDING, drawPaint);

        drawPaint.setTextAlign(Paint.Align.LEFT);
        if (game.levelNumber == 1)
            canvas.drawText(" ?", 50, TOP_PADDING, drawPaint);
        canvas.drawText("⟲", 50, (game.levelNumber==1) ? (TOP_PADDING*2 + TOP_PADDING/2) : (TOP_PADDING), drawPaint); //*2


        drawPaint.setTextAlign(Paint.Align.CENTER);
        for(int x =1; x <= numJugs; x++)
            canvas.drawText(scenario.jugs[x - 1].getMaxCapacity() + "L", x * (width / (numJugs + 2)) + (width / (numJugs + 2)) / 2, height / 2, drawPaint);

        int xPos = (canvas.getWidth() / 2);
        int yPos = TOP_PADDING;
        pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources().getDisplayMetrics());
        drawPaint.setTextSize(pixels);
        drawPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(scenario.getTargetCapacity() + "L", xPos, yPos, drawPaint);

        drawPaint.setColor(Color.GRAY);
        drawPaint.setTextSize(pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics()));
        for(int x = 1; x <= numJugs; x++){
            canvas.drawText(scenario.jugs[x - 1].getVolume() + "L", x * (width / (numJugs + 2)) + (width / (numJugs + 2)) / 2, height-30, drawPaint);
        }
    }

    /** This method draws the highlights for selected buckets. */
    public void drawHighlights(){
        int heightStep = height/4;
        int widthStep = (width/(numJugs+2));
        drawPaint.setColor(Color.parseColor("#1976d2"));
        drawPaint.setStrokeWidth(10);

        int count = 0;
        for(int x = 1; x <= (numJugs+1)*2-1; x++) {
            int jug = (x)/2 - 1;
            if (jug < numJugs && jug >= 0 &&  highlighted[jug]) {
                canvas.drawLine(widthStep * (int) ((x + 1) / 2) + ((count==0) ? (0) : (-GAP)), heightStep * 4+5, widthStep * (int) ((x + 1) / 2) + ((count==0) ? (0) : (-GAP)), heightStep * 2, drawPaint); //+5 on heightStep*4
                count++;
                if (count == 2)
                    count = 0;
            }
        }
    }

    /** This method undos a move by popping the stack. */
    public void undo(){
        if (undo.isEmpty())
            return;
        moves--;
        Action temp = undo.pop();
        scenario.jugs[temp.getFirstIndex()].setVolume(temp.getFirstCapacity());
        if (temp.getSecondIndex() != -1){
            scenario.jugs[temp.getSecondIndex()].setVolume(temp.getSecondCapacity());
        }
    }

    /** This method determines if any player already won the game. */
    public void verifyIfWon(){
        for(int x = 0; x < scenario.jugs.length; x++){
            if (scenario.jugs[x].getVolume() == target){
                game.finished(moves, game.getLeastMoves()); //temporary!
                hasWon = true; //?
                return;
            }
        }
    }

    /** This method handles gestures such as swiping up or down on the jugs.
     * @param x int This is the x variable of the swipe.
     * @param y int This is the y variable of the swipe.
     * @param dx int This indicates the direction (x-wise) of the swipe.
     * @param dy int This indicates the direction (y-wise) of the swipe.
     */
    public void gesture(int x, int y, int dx, int dy){
        int jug = (x/(getWidth()/(numJugs+2)))-1;
        if (jug >= 0 && jug < numJugs && y > getHeight()/4){
            if (dy > 0){ //swipe up!
                if (scenario.jugs[jug].getVolume() != scenario.jugs[jug].getMaxCapacity()) {
                    undo.push(new Action(jug, scenario.jugs[jug].getVolume()));
                    scenario.jugs[jug].setVolume(scenario.jugs[jug].getMaxCapacity()); //MAX! update the jug too?
                    moves++;
                }
            }
            else{
                if (scenario.jugs[jug].getVolume() != 0) {
                    undo.push(new Action(jug, scenario.jugs[jug].getVolume()));
                    scenario.jugs[jug].setVolume(0);
                    moves++;
                }
            }
        }
        invalidate();
    }

    /** r Runnable This variable is used for the animations. */
    private Runnable r = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    /** This method draws to the canvas and calls multiple methods.
     * @param canvas Canvas This is the canvas reference.
     */
    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        if (hasWon)
            return;

        this.canvas = canvas;
        updateCapacities();
        drawBackground(canvas);
        this.height = getHeight();
        this.width = getWidth();
        drawBottom();
        drawHighlights();
        drawText();
        drawScales();
        drawCap();
        verifyIfWon();
    }

    /** This method updates the capacity of the jugs. */
    public void updateCapacities(){
        for(int x = 0; x < scenario.jugs.length; x++)
            capacity[x] = (int) (((double)scenario.jugs[x].getVolume()/maxCapacity)*((getHeight()*0.5)));
    }

    /** This method draws the bottom line of the jugs. */
    public void drawBottom(){
        int widthStep = (width/(numJugs+2));

        drawPaint.setColor(Color.parseColor("#9e9e9e"));
        canvas.drawLine(widthStep-5, height-5, widthStep*(numJugs+1)-GAP+5, height-5, drawPaint); //+-2
    }

    /** This method draws scales on the jugs */
    public void drawScales(){
        int height = getHeight()/2; //this is the height of the scale
        int widthStep = (width/(numJugs+2)); //sduplicate from method below!
        int xCord = 0;
        int skipValue = 1;
        drawPaint.setColor(Color.BLACK); //maxCapacity
        if (maxCapacity > 20){
            skipValue = maxCapacity/10;
        }
        int pixelJump = height/maxCapacity;
        final int LEFT_ALIGN = 20;
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        drawPaint.setTextSize(pixels); //1000? lol
        for(int x = 0; x < numJugs; x++){
            xCord += widthStep;
            for(int y = 0; y <= maxCapacity; y+= skipValue){
                if (y == 0) //may look odd on some displays?
                    continue;
                if (scenario.jugs[x].getMaxCapacity() == y || (scenario.jugs[x].getMaxCapacity()-y < skipValue && scenario.jugs[x].getMaxCapacity()-y > 0)){
                    drawPaint.setColor(Color.GRAY);
                    canvas.drawText(y + "", xCord + LEFT_ALIGN, getHeight() - (y * pixelJump) + 10, drawPaint);
                    drawPaint.setColor(Color.BLACK);
                }else {
                    canvas.drawText(y + "", xCord + LEFT_ALIGN, getHeight() - (y * pixelJump) + 10, drawPaint);
                }
            }
        }
    }

    /** This method draws a cap on the top of the jugs at the limit. */
    public void drawCap(){
        drawPaint.setStrokeWidth(1);
        drawPaint.setColor(Color.parseColor("#9e9e9e"));
        int height = getHeight()/2;
        int widthStep = (width/(numJugs+2));
        int xCord = 0;
        for(int x = 0; x < numJugs; x++){
            xCord += widthStep;
            canvas.drawLine(xCord, (int)(getHeight()-(((double)height/maxCapacity) * scenario.jugs[x].getMaxCapacity())), xCord+widthStep-GAP, (int)(getHeight()-(((double)height/maxCapacity) * scenario.jugs[x].getMaxCapacity())), drawPaint);
        }
        drawPaint.setStrokeWidth(10);
    }

    /** This method draws the background with all the Jugs.
     * @param canvas This is the canvas reference.
     */
    public void drawBackground(Canvas canvas){
        int widthStep = (width/(numJugs+2));
        int heightStep = height/4;
        drawPaint.setColor(Color.parseColor("#9e9e9e"));

        drawPaint.setStrokeWidth(10);
        for(int x = 1; x <= (numJugs+1)*2-1; x++)
            canvas.drawLine(widthStep * (int) ((x + 1) / 2) + ((x % 2 == 0 && x != 2 ||  x == ((numJugs==3) ? (7) : (numJugs==4) ? (9) : (5)) ? (-GAP) : (0))), heightStep * 4, widthStep * (int)((x + 1) / 2) + ((x % 2 == 0 && x != 2 || x == ((numJugs==3) ? (7) : (numJugs==4) ? (9) : (5))) ? (-GAP) : (0)), heightStep * 2, drawPaint);

        canvas.drawLine(widthStep, height, widthStep*(numJugs+1)-GAP, height, drawPaint);

        drawPaint.setColor(Color.parseColor("#03a9f4"));
        for(int x = 1; x <= capacityDrawn.length; x++){ //draw the rectangle
            int x1 = widthStep * (int) (x) + 5;
            int x2 = widthStep * (int) (x+1) -GAP - 5;
            int y1 = heightStep*4;
            int y2 = heightStep*4-capacityDrawn[x-1];
            canvas.drawRect(x1, y2, x2, y1, drawPaint);
        }

        for(int x = 0; x < capacity.length; x++){
            if (Math.abs(capacity[x]-capacityDrawn[x]) < 5){
                capacityDrawn[x] = capacity[x];
                continue;
            }
            if (capacity[x] < capacityDrawn[x]){
                capacityDrawn[x]-=SPEED;
            }
            else if (capacity[x] > capacityDrawn[x]){
                capacityDrawn[x]+=SPEED;
            }
        }

        drawHighlights();
        if (!hasWon) {
            h.postDelayed(r, FRAME_RATE);
            invalidate();
        }
        else{}

    }

    /**
     * This method gets the dimensions of the view before canvas object is built.
     */
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

    /**
     * This method removes the global listener.
     * @param listener
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void removeOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT < 16) {
            getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        } else {
            getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }
    }
}
