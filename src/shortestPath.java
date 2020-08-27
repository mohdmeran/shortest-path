
import java.util.ArrayList;
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author black
 */
public class shortestPath {
    
    LinkedList<Integer> ShortestPath;
    int distance, timeTaken;
    
    public shortestPath(LinkedList<Integer> ShortestPath, int distance, int timeTaken){
        
        this.ShortestPath = ShortestPath;
        this.distance = distance;
        this.timeTaken = timeTaken;
    }
}
