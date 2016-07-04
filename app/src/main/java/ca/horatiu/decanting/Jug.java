package ca.horatiu.decanting;

import java.io.Serializable;

/**
 * This class abstracts a Jug and makes it serializable to be used in intents.
 * @author Horatiu Lazu
 * @version 1.0
 */
public class Jug implements Serializable{
    /** col int This is the column number of the jug */
    private int col;
    /** maxCapacity int This is the max capacity of the jug */
    private int maxCapacity;
    /* volume int This is the volume of the jug */
    private int volume;

    /** This is the class constructor of a Jug
     *
     * @param col This is the column number.
     * @param maxCapacity This is the max capacity of the jug.
     */
    public Jug(int col, int maxCapacity){
        this.col = col;
        this.maxCapacity = maxCapacity;
    }

    /** This method sets the volume of a jug.
     *
     * @param volume This is the new volume.
     */
    public void setVolume(int volume){
        this.volume = volume;
    }

    /**
     * This method returns the column number.
     * @return int This is the column number.
     */
    public int getCol(){
        return col;
    }

    /**
     * This method returns the maximum capacity.
     * @return int This is the maximum capacity of the jug.
     */
    public int getMaxCapacity(){
        return maxCapacity;
    }

    /**
     * This method empties a jug.
     */
    public void empty(){
        volume = 0;
    }

    /* This method fills a jug. */
    public void fill(){
        volume = maxCapacity;
    }

    /**
     * This method pours water from one jug to another. It makes use of Java's pass by value of the reference.
     * @param other This is the other jug, being poured.
     */
    public void pour(Jug other){
        /* This case if for an overflow.
            Ex: 5ml poured into a 2ml bottle. Decrease the pouring one by the difference.
         */
        if (other.getMaxCapacity() <  other.getVolume() + volume){
            volume -= other.getMaxCapacity() - other.getVolume();
            other.setVolume(other.getMaxCapacity());
        }
        else{
            other.setVolume(other.getVolume() + volume); //you pour from yours to the other one!
            volume = 0;
        }
    }

    /** This method returns the volume of the jug.
     * @return int This is the volume of the jug.
     */
    public int getVolume(){
        return volume;
    }

    /** This method generates a String describing the object
     *
     * @return String The description String.
     */
    public String toString(){
      return volume + "";
    }
}
