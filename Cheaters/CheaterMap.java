import java.io.StringBufferInputStream;
import java.util.*;

public class CheaterMap{
    Reader in;
    String dir;
    HashMap<String, Set<String>> map = new HashMap<String, Set<String>>();

    public CheaterMap(String dir){
        this.dir = dir;
        in = new Reader(dir);
    }

    public void biuldMap(){
        String key; 
        String fileName;
        Set<String> value;
        System.out.println("Biulding CheaterMap From input " 
                            + dir + "... \n");
        while(in.hasNext()){
            fileName = in.getCurrentFileName();
            key = in.getNext();
            //key doesnt exisit
            if(!map.containsKey(key)){
                value = new HashSet<String>();
                value.add(fileName);
                map.put(key, value);
            //key exisit 
            }else{
                map.get(key).add(fileName);
            }
        }
    }

    public void printMap(){
        Set<String> keySet = map.keySet();
        for(String key : keySet){
            if(map.get(key).size() > 2){                
                System.out.print(key + "\n\t");
                for(String fileName: map.get(key)){
                    System.out.print(fileName + " ");
                }
                System.out.println("\n");
            }
        }
    }
    public void buildGraph(){

    }
}