package ca.horatiu.decanting;

/**
 * This class acts as an encapsulation of a high scores item.
 * Created by Horatiu on 06/07/2016.
 */
public class HighscoresItem  {
    /** moves int This is the number of moves. */
    int moves;
    /** level int This is the level number. */
    int level;
    /** id long This is the ID # of the item. */
    long id;

    String player;

    String date;

    double performanceRating;

    //MainActivity.highscores.getDatasource().addScore(moves.getMoves() + "", levelNumber, dateFormat.format(date), score + "");

    public HighscoresItem(int moves, int level, String date, double performanceRating, String player){
        this.moves =  moves;
        this.level = level;
        this.date = date;
        this.performanceRating = performanceRating;
        this.player = player;
    }

    public HighscoresItem(){

    }

    public void setPerformanceRating(double performanceRating){
        this.performanceRating = performanceRating;
    }

    public double getPerformanceRating(){
        return performanceRating;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getDate(){
        return date;
    }

    public String getPlayer(){
        return player;
    }

    public void setPlayer(String player){
        this.player = player;
    }

    /**
     * This method sets the moves.
     * @param moves int The new move number.
     */
    public void setMoves(int moves){
        this.moves = moves;
    }

    /** This method sets the ID.
     * @param id long This is the new ID.
     */
    public void setId(long id){
        this.id = id;
    }

    /**
     * This method sets the level.
     * @param level int This is the level.
     */
    public void setLevel(int level){
        this.level = level;
    }

    /**
     * This method returns the level.
     * @return int This is the level.
     */
    public int getLevel(){
        return level;
    }

    /**
     * This method returns the number of moves.
     * @return int The number of moves.
     */
    public int getMoves(){
        return moves;
    }

    /**
     * This method returns the ID.
     * @return long ID
     */
    public long getId(){
        return id;
    }
}
