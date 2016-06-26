package ca.horatiu.decanting;
import java.util.*;

/**
 * Created by Horatiu on 26/06/2016.
 */
public class SolutionSolverTest { //implement JUnit so it's legit? :-)
  Jug [] arr;
  int target;
  HashSet <String> dp = new HashSet<String>(); //store if this was already considered in the Q
  
  public String generateCache(Jug [] array){
    StringBuilder cache = new StringBuilder(); //only noobs do += with String :-) 
    for(int x = 0; x < array.length; x++)
      cache.append(array[x].getVolume() + "|");
    return cache.toString();
  }
  
  public int solve(){
    //ok now time for magic :-)
    dp.add(generateCache(arr));
    ArrayDeque<State> Q = new ArrayDeque<State>();
    Q.addLast(new State(arr));
    
    int moves = 0;
    bfs:{
      while(!Q.isEmpty()){
        int size = Q.size();
        for(int q = 0; q < size; q++){
          State temp = Q.removeFirst();
          System.out.println("RUNNING!" + temp.toString());
          //now do each possibility in the tree...
          
          //verify to see if any are right
          for(int x = 0; x < temp.arr.length; x++){
            if (temp.arr[x].getVolume() == target){
              System.out.println("Solved! Moves: " + moves);
              break bfs;
            }
          }
          
          //fill one of each
          for(int x = 0; x < temp.arr.length; x++){
            int tempCapacity = temp.arr[x].getVolume();
            temp.arr[x].setVolume(temp.arr[x].getMaxCapacity());
            if (!dp.contains(generateCache(temp.arr))){
              Q.addLast(new State(temp.arr));
              System.out.println("ADDED: " + temp.toString());
              dp.add(generateCache(temp.arr));
            }
            temp.arr[x].setVolume(tempCapacity);
          }
          
          //empty each one
          for(int x = 0; x < temp.arr.length; x++){
            int tempCapacity = temp.arr[x].getVolume();
            temp.arr[x].setVolume(0);
            if (!dp.contains(generateCache(temp.arr))){
              Q.addLast(new State(temp.arr));
              dp.add(generateCache(temp.arr));
            }
            temp.arr[x].setVolume(tempCapacity);
          }
          
          //now swap, O(n^2 time)?
          for(int a = 0; a < temp.arr.length; a++){
            for(int b = 0; b < temp.arr.length; b++){
              if (a != b){
                //pour a in b!
                //you have to get the intricate details...
                int volumeA = temp.arr[a].getVolume();
                int volumeB = temp.arr[b].getVolume();
                temp.arr[a].pour(temp.arr[b]);
                if (!dp.contains(generateCache(temp.arr))){
                  Q.addLast(new State(temp.arr));
                  dp.add(generateCache(temp.arr));
                }
                temp.arr[a].setVolume(volumeA);
                temp.arr[b].setVolume(volumeB);
              }
            }
          }
          
        }
        moves++;
      }
    }
    return -1;
  }
  
  static class State{
    Jug [] arr;
    
    public State(Jug [] arr){
      this.arr = new Jug[arr.length];
      for(int x = 0; x < arr.length; x++){
        this.arr[x] = new Jug(arr[x].getCol(), arr[x].getMaxCapacity());
        this.arr[x].setVolume(arr[x].getVolume());
      }
    }
    
    public String toString(){
      String ans = "";
      for(int x = 0; x < arr.length; x++){
        ans += arr[x].getVolume() + " "; //don't use += 
      }
      return ans;
    }
  }
  
  
  public SolutionSolverTest(){
    arr = new Jug[2];
    arr[0] = new Jug(0, 5); //the 0 & 1 don't actually matter!
    arr[1] = new Jug(1, 3);
    target = 4;
    System.out.println("Minimum number of moves: " + solve());
  }
  
  public static void main (String [] args){
    new SolutionSolverTest();
  }
}
