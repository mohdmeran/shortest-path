/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/*
STORE SHORTEST PATH USING LINKEDLIST
*/
//package braingame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Graph {
    
    ArrayList<ArrayList<Node>> graph;
    ArrayList<LinkedList<Integer>> shortestPath;
    
    PriorityQueue<Node> pq;
    int cost[][];
    HashSet<Integer> settled;
    HashSet<Integer> foundNode;
    
    
    public Graph(){
        
        graph = new ArrayList<>();
        shortestPath = new ArrayList<>();
    }
    
    public void addNode(){
        shortestPath.add(new LinkedList<>());
        graph.add(new ArrayList<>());
    }
    
    public void connectNode(int nodeID, int neightbourID, int timeTaken, int distance){
        
        graph.get(nodeID).add(new Node(neightbourID, timeTaken, distance));    
    }
    
    public shortestPath findShortestPath(int sourceID, int targetID){
        
        //clear shortestPath
        for(int i = 0; i < shortestPath.size(); i++){
            
            shortestPath.get(i).clear();
        }
        
        //initialize all variable
        int totalNode = graph.size();
        pq = new PriorityQueue<>(totalNode, new Node());
        cost = new int[totalNode][2]; //[timeTaken,distance]
        settled = new HashSet<>();
        foundNode = new HashSet<>();
        
        //set all distance from source to infinity
        for(int i = 0; i < totalNode; i++){
            
            cost[i][0] = Integer.MAX_VALUE;
            cost[i][1] = Integer.MAX_VALUE;
        }
        
        
        //set distance of source to source = 0 and add the node to pq
        cost[sourceID][0] = 0;
        cost[sourceID][1] = 0;
        
        pq.add(new Node(sourceID, 0, 0));
        
        //add the path of the source
        shortestPath.get(sourceID).add(sourceID);
        
        //add source to founded node
        foundNode.add(sourceID);
        
        while(settled.size() != foundNode.size()){
                   
            int u = pq.remove().ID;
            
            settled.add(u);
            
            if(u == targetID){
                
                break;
            }
            
            //calculate neighbouring distance
            int edgeDistance;
            int newDistance;
            
            int edgeTimeTaken;
            int newTimeTaken;
            
            for(int i = 0; i < graph.get(u).size(); i++){
                
                Node currentNode = graph.get(u).get(i);
                foundNode.add(currentNode.ID);
                
                if(!settled.contains(currentNode.ID)){
                    
                    
                    edgeDistance = currentNode.distance;
                    newDistance = cost[u][1] + edgeDistance;
                    
                    edgeTimeTaken = currentNode.timeTaken;
                    newTimeTaken = cost[u][0] + edgeTimeTaken;
                    
                    if(newTimeTaken < cost[currentNode.ID][0]){
                        
                        changeCost(newTimeTaken, newDistance, currentNode.ID);
                        newPath(currentNode.ID, u);
                    }
                    else if(newTimeTaken == cost[currentNode.ID][0]){
                        
                        if(newDistance < cost[currentNode.ID][1]){
                            
                               changeCost(newTimeTaken, newDistance, currentNode.ID);
                               newPath(currentNode.ID, u);                     
                        }
                    }
                            
                    pq.add(currentNode);
                }
            }
            
        }
        
        
        return new shortestPath(shortestPath.get(targetID), cost[targetID][1], cost[targetID][0]);
        /*
        if(shortestPath.get(targetID).isEmpty()){
            System.out.println("Node " + targetID + " is not connected to node " + sourceID);
            System.out.println("");
        }
        else{
            LinkedList thisNode = shortestPath.get(targetID);
                
                System.out.print("Shortest Path From Node " + sourceID + " to " + targetID + ": ");                
                //System.out.println(thisNode.size());
                for(int i = 0; i < thisNode.size() - 1; i++){
                                       
                    System.out.print(thisNode.get(i) + " > ");
                }
                System.out.println(thisNode.getLast());
                
                System.out.println("Time taken: " + cost[targetID][0]);
                System.out.println("Distance: " + cost[targetID][1]);
                System.out.println("");
        }
        */
        
        
    }
    
    public void changeCost(int timeTaken, int distance, int ID){
        
        cost[ID][0] = timeTaken;
        cost[ID][1] = distance;
    }
    
    public void newPath(int targetNodeID, int newNodeID){
        
        shortestPath.get(targetNodeID).clear();
        for(int j = 0; j < shortestPath.get(newNodeID).size(); j++){

            shortestPath.get(targetNodeID).addLast(shortestPath.get(newNodeID).get(j));
        }
        shortestPath.get(targetNodeID).addLast(targetNodeID);
    }
    /*
    
    //display all distance from source node
    public void displayShortestPath(int sourceID){
        
        for(int i = 0; i < distance.length; i++){
            System.out.println("distance from " + sourceID + " to " + i + " is " + distance[i]);
        }
    }
    */
    
    
}
