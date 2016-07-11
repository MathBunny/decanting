package ca.horatiu.decanting;

import java.io.Serializable;

/**
 * This method encapsulates the best moves and total moves.
 * @author Horatiu Lazu
 */
public class Moves implements Serializable{
    /** moves int This is the number of moves. */
    private int moves;
    /** bestMoves int This is the best possible solution. */
    private int bestMoves;

    /**
     * This is the class constructor.
     * @param moves int This is the number of moves the player did.
     * @param bestMoves int This is the best number of moves possible.
     */
    public Moves(int moves, int bestMoves){
        this.moves = moves;
        this.bestMoves = bestMoves;
    }

    /** This method returns the number of moves.
     * @return int This is the number of moves.
     */
    public int getMoves(){
        return moves;
    }

    /** This method returns the best move count.
     * @return int This is the best possible amount of moves.
     */
    public int getBestMoves(){
        return bestMoves;
    }
}
