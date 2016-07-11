package ca.horatiu.decanting;

/**
 * This class encapsulates an action, so that the undo function works.
 * Created by Horatiu on 09/07/2016.
 */
public class Action {
    /** firstIndex int This is the first index of the first bucket that is changed. */
    private int firstIndex = -1;
    /** firstCapacity int This is what the capacity was for the bucket at the first index previously. */
    private int firstCapacity = -1;
    /** secondIndex int This is the index of the second action.*/
    private int secondIndex = -1; //in case you pour 2 places
    /** secondCapacity int This is the previous capacity of the jug at the second index. */
    private int secondCapacity = -1;

    /**
     * This is a class constructor where only one jug was involved.
     * @param firstIndex int This is the first index.
     * @param firstCapacity int This is the previous capacity of the first index.
     */
    public Action(int firstIndex, int firstCapacity){
        this.firstIndex = firstIndex;
        this.firstCapacity = firstCapacity;
    }

    /**
     * This is a class constructor where two jugs were involved.
     * @param firstIndex int This is the index of the first bucket.
     * @param firstCapacity int This is the previous capacity of the first index.
     * @param secondIndex int This is the second index.
     * @param secondCapacity int This is the second index's previous capacity.
     */
    public Action(int firstIndex, int firstCapacity, int secondIndex, int secondCapacity){
        this.firstIndex = firstIndex;
        this.firstCapacity = firstCapacity;
        this.secondIndex = secondIndex;
        this.secondCapacity = secondCapacity;
    }

    /**
     * This method returns the first index.
     * @return int This is the first index.
     */
    public int getFirstIndex(){
        return firstIndex;
    }

    /**
     * This method returns the first capacity.
     * @return int This is the first capacity.
     */
    public int getFirstCapacity(){
        return firstCapacity;
    }

    /**
     * This method returns the second index.
     * @return int This is the second index.
     */
    public int getSecondIndex(){
        return secondIndex;
    }

    /**
     * This method returns the second capacity.
     * @return int This is the second capacity.
     */
    public int getSecondCapacity(){
        return secondCapacity;
    }
}
