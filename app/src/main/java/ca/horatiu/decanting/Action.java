package ca.horatiu.decanting;

/**
 * Created by Horatiu on 09/07/2016.
 */
public class Action {
    private int firstIndex = -1;
    private int firstCapacity = -1;
    private int secondIndex = -1; //in case you pour 2 places
    private int secondCapacity = -1;

    public Action(int firstIndex, int firstCapacity){
        this.firstIndex = firstIndex;
        this.firstCapacity = firstCapacity;
    }

    public Action(int firstIndex, int firstCapacity, int secondIndex, int secondCapacity){
        this.firstIndex = firstIndex;
        this.firstCapacity = firstCapacity;
        this.secondIndex = secondIndex;
        this.secondCapacity = secondCapacity;
    }

    public int getFirstIndex(){
        return firstIndex;
    }

    public int getFirstCapacity(){
        return firstCapacity;
    }

    public int getSecondIndex(){
        return secondIndex;
    }

    public int getSecondCapacity(){
        return secondCapacity;
    }
}
