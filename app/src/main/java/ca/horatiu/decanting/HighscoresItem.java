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
    /** player String This is the player name. */
    String player;
    /** date String This is the date. */
    String date;
    /** double performanceRating This is the performance rating of the attempt. */
    double performanceRating;

    /**
     * This is the class constructor that populates the field variables.
     * @param moves int This is the number of moves.
     * @param level int This is the level number.
     * @param date String This is the current date.
     * @param performanceRating double This is the performance.
     * @param player String This is the player information.
     */
    public HighscoresItem(int moves, int level, String date, double performanceRating, String player){
        this.moves =  moves;
        this.level = level;
        this.date = date;
        this.performanceRating = performanceRating;
        this.player = player;
    }

    /** This is a default class constructor. */
    public HighscoresItem(){}

    /** This method sets the performance rating.
     * @param performanceRating double This is a new performance rating.
     */
    public void setPerformanceRating(double performanceRating){
        this.performanceRating = performanceRating;
    }

    /** This method returns the performance rating.
     * @return double This is the performance rating.
     */
    public double getPerformanceRating(){
        return performanceRating;
    }

    /**
     * This method sets the date.
     * @param date String This is the new date.
     */
    public void setDate(String date){
        this.date = date;
    }

    /** This method returns the date.
     * @return String This is the date.
     */
    public String getDate(){
        return date;
    }

    /** This method returns the player name.
     * @return String This is the playername.
     */
    public String getPlayer(){
        return player;
    }

    /** This method sets the player name.
     * @param player String This is the player name.
     */
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
