import java.util.*;
import java.io.*;

public class Main{
    public static void main(String[] args){
        CheaterMap map = new CheaterMap("sm_doc_set", 7, 200);
        //must call to read data
        map.biuldMap();
        
        /**
         * this will print our the Hashmap 6 word Combo 
         * and corsponding files that have it
         * Prints only the ones with n or more files that contain it 
         * n shoud be at least 1
        */
        //map.printMap(2);
        
        /**
         * this will print all edges of the graph un ordered
         */
        //map.printGraph();
        
        /**
         *  //this is will print all edges above the threshhold 
         * from greatest to least. 
         * Or in other words our final out put
         */
         map.printCheatingGraph();
        
    }
}