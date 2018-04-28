import java.io.StringBufferInputStream;
import java.util.*;

public class CheaterMap{
    Reader in;
    String dir;
    int threshHold;
    HashMap<String, Set<String>> map = new HashMap<String, Set<String>>();
    CheaterGraph graph;
    public CheaterMap(String dir, int bufferSize, int threshHold){
        this.dir = dir;
        this.threshHold = threshHold;
        in = new Reader(dir, bufferSize);
        graph = new CheaterGraph(threshHold);
    }

    /**
     * takes in every single n word combinaion and then makes a hashmap 
     * of 6word combo mapped to a set of all file names that it apeared in
     * when a file is inputed into an exisiting set it will incement it's edge
     * between itself and all those preexisiting nodes
     */
    public void biuldMap(){
        String key; 
        String fileName;
        Set<String> value;
        System.out.println("Biulding CheaterMap From input " 
                            + dir + "... \n");
        while(in.hasNext()){
            fileName = in.getCurrentFileName();
            key = in.getNext();                     //key is n letter word combo
            if(!map.containsKey(key)){              //key does not exist
                value = new HashSet<String>();
                value.add(fileName);
                map.put(key, value);
            }else{                                  //key does exisit, implying there exisit at least one element in set
                if(!map.get(key).contains(fileName)){
                    for(String fileName1: map.get(key)){        //loop though every preexisiting file in set
                        graph.processPair(fileName, fileName1); // add pair to graoh
                    }
                    map.get(key).add(fileName);
                }
            }
        }
    }

    public void printMap(int n){
        Set<String> keySet = map.keySet();
        for(String key : keySet){
            if(map.get(key).size() >= n ){                
                System.out.print(key + "\n\t");
                for(String fileName: map.get(key)){
                    System.out.print(fileName + " ");
                }
                System.out.println("\n");
            }
        }
    }

    public void printGraph(){
        graph.printGraph();
    }
    //solution
    public void printCheatingGraph(){
        graph.printCheaterGraph();
    }
}