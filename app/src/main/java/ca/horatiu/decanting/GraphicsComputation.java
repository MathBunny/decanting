package ca.horatiu.decanting;

/**
 * Created by Horatiu on 27/06/2016.
 */
public class GraphicsComputation {
    static final int HEIGHT_DIVISION = 4;
    /* This method basically draws all of the graphics ... */
    public static void computeCoordinates(int width, int height, int numJugs){
        int stepH = width/(numJugs+2);
        int stepV = height/HEIGHT_DIVISION; //constant
        //draw taller lines on the edges
    }

}
