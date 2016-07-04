package ca.horatiu.decanting;

import java.io.Serializable;

/** This is the scenario class, used to encapsulate the game levels. It also implements Serializable to be used in intents.
 * Created by Horatiu on 26/06/2016.
 */
public class Scenario implements Serializable {
    /** jugCount int This is the jugCount. */
    private int jugCount;
    /** jugs Jugs [] These are the jugs */
    Jug [] jugs;
    /** targetCapacity int This is the target capacity of the level. */
    private int targetCapacity;

    /** This is the class constructor
     *
     * @param jugCount This is the number of jugs in the level.
     * @param targetCapacity
     */
    public Scenario(int jugCount, int targetCapacity){
        this.jugCount = jugCount;
        this.jugs = new Jug[jugCount];
        this.targetCapacity = targetCapacity;
    }

    /** This method returns the jugs.
     * @return Jug [] This is the private jugs variable.
     */
    public Jug [] getJugs(){
        return jugs;
    }

    /** This method returns the number of jugs.
     *
     * @return int The number of jugs.
     */
    public int getJugCount(){
        return jugCount;
    }

    /** This method returns the target capacity of the level.
     * @return int This is te target capacity.
     */
    public int getTargetCapacity(){
        return targetCapacity;
    }
}
