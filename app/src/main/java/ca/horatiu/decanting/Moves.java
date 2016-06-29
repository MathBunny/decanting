package ca.horatiu.decanting;

import java.io.Serializable;

/**
 * Created by Horatiu on 29/06/2016.
 */
public class Moves implements Serializable{
    private int moves;
    private int bestMoves;

    public Moves(int moves, int bestMoves){
        this.moves = moves;
        this.bestMoves = bestMoves;
    }

    public int getMoves(){
        return moves;
    }

    public int getBestMoves(){
        return bestMoves;
    }
}
