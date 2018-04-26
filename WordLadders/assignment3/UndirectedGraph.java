package assignment3;
import java.util.*;
import java.io.*;

class UndirectedGraph{
    
    private Map<String, Node> graph;            //The graph adjacency list to string
    private Set<String> dict;                   //Temporarily holds the dictionary, will be put into graph
    
    /**
     * Takes in a Dictionary of words and creates a graph out of it 
     * with edges such that each node is different by one letter
     */
    public UndirectedGraph(Set<String> dict){
        this.dict = dict;
        graph = new HashMap<String, Node>();
        makeGraph();
    }

    /**
     * This puts every word in the graph and connects it to every other word that 
     * is different by one letter
     */
    public void makeGraph(){
        Node newWord;
        for(String x: dict){
            newWord = new Node(x);
            for(String y: dict){
                if(newWord.isEdge(y)){
                    newWord.addEdge(y);
                }
            }      
            graph.put(x, newWord);
        }
    }
    
    /**
     * Getter for the graph
     * @return Map<String, Node>
     */
    public Map<String, Node> getMap(){
        return this.graph;
    }

    /**
     * Prints out the word and all it's adjacenies, for debugging.
     */
    public void printGraph(){
        for(String x : dict){
            System.out.println(x + " " + graph.get(x).getEdge());
        }
    }
}

/**
 * A node is a class that stores one string and an adjaceny list for that string
 */
class Node {
    private ArrayList<String> adj;
    private String word;
    private boolean visited, connected = false;
    private String last;
    
    /**
     * Constructor
     * Creates an unconnected node with string value
     * @param word type string
     */
    public Node (String word){
        this.word = word;
        this.visited = false;
        adj = new ArrayList<String>();
    }

    /**
     * Getter for the adjaceney list.
     * @return ArrayList<String>
     */
    public ArrayList<String> getEdge(){
        return this.adj;
    }

    /**
     * Adds a node to the adjacency list by ID
     */
    public void addEdge(String word){
        connected = true;
        adj.add(word);
    }

    /**
     * Methods records the parent node key
     */
    public void setParent(String parent){
        this.last = parent;
        connected = true;
    }

    /**
     * Getter for the parent String
     */
    public String getParent(){
        return this.last;
    }

    /**
     * Getter to see if node has been visited
     * @return true or false
     */
    public boolean isVisit(){
        return this.visited;
    }

    /**
     * Marks a node visited
     */
    public void visit(){
        this.visited = true;
    }
    
    /**
     * Make a node unvisited
     */
    public void unVisit(){
        this.visited = false;
    }

    /**
     * Getter for the a String of the word
     * @return a string of the word
     */
    public String getWord(){
        return this.word;
    }
    /**
     * Getter for the a String of the parent word
     * @return a string of the parent
     */
    public String getLast(){
        return this.last;
    }

    /**
     * Checks to see if a node has edges
     * @return true/false
     */
    public boolean isConnected(){
        return this.connected;
    }
    /**
     * This methods checks if a word is different by only one letter
     * @return boolean 
     */
    public boolean isEdge(String word){
        int diff = 0;
        String word1 = this.getWord();
        for(int i = 0; i < word.length(); i++){
            if(word1.charAt(i) != word.charAt(i)){
                diff++;
            }
            if(diff == 2){
                return false;
            }
        }
        if(diff == 1){
            return true;
        }else{
            return false;
        }
    }
}


