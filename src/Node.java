/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package braingame;

import java.util.Comparator;

/**
 *
 * @author Miron
 */
public class Node implements Comparator<Node>{
    
    int ID;
    int distance;
    int timeTaken;
    
    public Node(){
        
    }
    
    public Node(int ID, int timeTaken, int distance){
        
        this.ID = ID;
        this.distance = distance;
        this.timeTaken = timeTaken;
    }

    @Override
    public int compare(Node node1, Node node2) {
        if(node1.distance < node2.distance){
            return -1;
        }
        else{
            return 1;
        }
    }
    
    
}
