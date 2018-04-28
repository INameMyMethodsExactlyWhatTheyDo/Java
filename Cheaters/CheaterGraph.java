import java.util.*;


public class CheaterGraph{
    /**
     * each kay would be "filename1 + filename2" or visevera 
     * each CheaterEdge would contain the number of hits between them
     */
    HashMap<String, CheaterEdge> graph = new HashMap<String, CheaterEdge>();
    int thresh; //this is the threshhold at which we will consider someone to be cheating
    
    public CheaterGraph(int thresh){
        this.thresh = thresh;
    }

    /**
     * The two nodes that make an edge are combined together to make a single key for the hashmap
     * it cheecks both ways and only one should have the edge object
     */
    public void processPair(String file1, String file2){
        String key1 = file1 + file2;
        String key2 = file2 + file1;
        if(graph.containsKey(key1)){
            graph.get(key1).increment();
        }else if(graph.containsKey(key2)){
            graph.get(key2).increment();
        }else{
            graph.put(key1, new CheaterGraph.CheaterEdge(file1, file2));
            graph.get(key1).increment();
        }
    }

    public int getEdgeWeight(String file1, String file2){
        String key1 = file1 + file2;
        String key2 = file2 + file1;
        if(graph.containsKey(key1)){
            return graph.get(key1).getWeight();
        }else if(graph.containsKey(key2)){
            return graph.get(key2).getWeight();
        }else{
            return 0;
        }
    }
    /**
     * Frist it finds all edges that have over n hits
     * then it sorts it with some comparator 
     * and then it returns the list of edges, sorted, that have over n hits
     */
    public ArrayList<CheaterEdge> sortEdges(){
        //get all pairs that have 200 + hits
        ArrayList<CheaterEdge> validEdges = new ArrayList<CheaterEdge>();
        Set<String> keySet = graph.keySet();
        for(String key : keySet){
            if(graph.get(key).getWeight() >= this.thresh){
                validEdges.add(graph.get(key));
            }
        }

        Collections.sort(validEdges, new compareEdge());
        return validEdges;
    }

    /**
     * this will print the whole graph 
     */
    public void printGraph(){
        Set<String> keySet = graph.keySet();
        CheaterEdge e;
        for(String key : keySet){
            e = graph.get(key);
            System.out.println(e.weight + ": " + e.node1 + " " + e.node2);
        }
    }

    /**
     * this is the output we want
     */
    public void printCheaterGraph(){
        for(CheaterEdge e: this.sortEdges()){
            System.out.println(e.weight + ": " + e.node1 + " " + e.node2);
        }
    }

    /**
     * there will be at max 1 edge for each pair of files 
     * the weight indicates the number of 6 word combinations 
     * they both had in common
     */
    class CheaterEdge{
        String node1;
        String node2;
        int weight;
        public CheaterEdge(String node1, String node2){
            this.node1 = node1;
            this.node2 = node2;
            weight = 0;
        }
        public void increment(){
            weight++;
        }
        public int getWeight(){
            return weight;
        }
        public String getNode1(){
            return node1;
        }
        public String getNode2(){
            return node2;
        }

    }
    /**
     * this thing is a compareator we use you know pro 
     */

    class compareEdge implements Comparator<CheaterEdge>{
        public int compare(CheaterEdge e1, CheaterEdge e2){
            return e2.getWeight() - e1.getWeight();
        }
    }
}