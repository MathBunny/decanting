package ca.horatiu.decanting;

import java.util.ArrayDeque;
import java.util.HashSet;

/**
 * This class finds the solution to the puzzle.
 * @author Horatiu Lazu
 * Created by Horatiu on 26/06/2016.
 */
public class SolutionSolver {
    /** arr Jug [] These are the jugs. */
    private Jug [] arr;
    /** target int This is the target capacity. */
    private int target;
    /** dp HashSet This is used for caching states. */
    private HashSet<String> dp = new HashSet<String>(); //store if this was already considered in Q
    /** minSteps int This is the minimum number of steps. */
    private int minSteps = -1;
    /** steps String This is the number of steps. */
    private String steps;
    /** MAX_MOVES_PERMITTED This is the maximum number of moves permitted to be executed. */
    public static int MAX_MOVES_PERMITTED = 1000;
    /** MIN_MOVES_REQUIRED int This is the minimum moves required for the level to be generated. */
    public static int MIN_MOVES_REQUIRED = 5;

    /**
     * This method generates the cache.
     * @param array Jug [] This is the jug array inputted.
     * @return String This is the generated cache.
     */
    private String generateCache(Jug [] array){
        StringBuilder cache = new StringBuilder(); //only noobs do += with Strings :-)
        for(int x = 0; x < array.length; x++)
            cache.append(array[x].getVolume() + "|");
        return cache.toString();
    }

    /** This method solves the puzzle using a caching top-down BFS algorithm */
    private int solve(){
        dp.add(generateCache(arr));
        ArrayDeque<State> Q = new ArrayDeque<State>();
        Q.addLast(new State(arr, "Initial", "Initial"));

        int moves = 0;
        bfs:{
            while(!Q.isEmpty()){
                int size = Q.size();
                for(int q = 0; q < size; q++){
                    State temp = Q.removeFirst();
                    //verify to see if any are right
                    for(int x = 0; x < temp.arr.length; x++){
                        if (temp.arr[x].getVolume() == target){
                            //System.out.println("Solution: " + temp.actions);
                            steps = temp.actions;
                            return moves;
                            //break bfs;
                        }
                    }

                    //This is filling in the buckets
                    for(int x = 0; x < temp.arr.length; x++){
                        int tempCapacity = temp.arr[x].getVolume();
                        temp.arr[x].setVolume(temp.arr[x].getMaxCapacity());
                        if (!dp.contains(generateCache(temp.arr))){
                            Q.addLast(new State(temp.arr, temp.actions + "\n FILL " + x, temp.trace+"\nArr: = " +  temp.toString()));
                            dp.add(generateCache(temp.arr));
                        }
                        temp.arr[x].setVolume(tempCapacity);
                    }

                    //empty each one
                    for(int x = 0; x < temp.arr.length; x++){
                        int tempCapacity = temp.arr[x].getVolume();
                        temp.arr[x].setVolume(0);
                        if (!dp.contains(generateCache(temp.arr))){
                            Q.addLast(new State(temp.arr,  temp.actions + "\n EMPTY " + x, temp.trace+"\nArr: = " + temp.toString()));
                            dp.add(generateCache(temp.arr));
                        }
                        temp.arr[x].setVolume(tempCapacity);
                    }

                    //now swap, O(a*b time)?
                    for(int a = 0; a < temp.arr.length; a++){
                        for(int b = 0; b < temp.arr.length; b++){
                            if (a != b){//pour a in b!
                                int volumeA = temp.arr[a].getVolume();
                                int volumeB = temp.arr[b].getVolume();
                                temp.arr[a].pour(temp.arr[b]);
                                if (!dp.contains(generateCache(temp.arr))){
                                    Q.addLast(new State(temp.arr,  temp.actions + "\n POUR " + a + " INTO: " + b,  temp.trace+"\nArr: = " +  temp.toString()));
                                    dp.add(generateCache(temp.arr));
                                }
                                temp.arr[a].setVolume(volumeA);
                                temp.arr[b].setVolume(volumeB);
                            }
                        }
                    }
                }
                moves++;
                if (moves > MAX_MOVES_PERMITTED){
                    minSteps = -1;
                    return -1; //ok!
                }
            }
        }
        return -1;
    }

    /** This class abstracts a state. */
    static class State{
        /** arr Jug [] This is the jug array. */
        Jug [] arr;
        /** actions String This is the string with actions. */
        String actions;
        /** trace String This is the string storing all the events. */
        String trace;

        /**
         * This is a class constructor for the State
         * @param arr Jug [] This is the jug array.
         * @param actions String These are the actions.
         * @param trace String This is the trace of events.
         */
        public State(Jug [] arr, String actions, String trace){
            this.arr = new Jug[arr.length];
            this.actions = actions;
            this.trace = trace;
            for(int x = 0; x < arr.length; x++){
                this.arr[x] = new Jug(arr[x].getCol(), arr[x].getMaxCapacity());
                this.arr[x].setVolume(arr[x].getVolume());
            }
        }

        /**
         * This class is used for diagnostic purposes to output the state to a string.
         * @return String This is the state.
         */
        public String toString(){
            StringBuilder ans = new StringBuilder();
            for(int x = 0; x < arr.length; x++){
                ans.append(arr[x].getVolume() + " "); //don't use +=
            }
            return ans.toString();
        }
    }

    /**
     * This method returns the minimum number of steps.
     * @return int This is the minimum number of steps.
     */
    public int getMinSteps(){
        return minSteps;
    }

    /**
     * This is the number of steps.
     * @return int Number of steps.
     */
    public String getSteps(){
        return steps;
    }

    /**
     * This is the class constructor for the program.
     * @param arr Jug [] This is the array.
     * @param target int This is the target capacity.
     */
    public SolutionSolver(Jug [] arr, int target){
        this.arr = arr;
        this.target = target;
        this.minSteps = solve();
    }

}
