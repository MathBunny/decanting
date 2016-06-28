package ca.horatiu.decanting;

import java.io.Serializable;

/**
 * Created by Horatiu on 26/06/2016.
 */
public class Scenario implements Serializable {
    private int jugCount;
    Jug [] jugs;

    public Scenario(int jugCount){
        this.jugCount = jugCount;
        this.jugs = new Jug[jugCount];
    }

    public Jug [] getJugs(){
        return jugs;
    }

    public int getJugCount(){
        return jugCount;
    }
}
