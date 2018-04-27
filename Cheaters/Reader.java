import java.util.*;
import java.io.*;

public class Reader{
    File dir;
    File[] list;
    int indexOfCurrentFile = 0;
    Scanner currentFile;
    ArrayList<String> buffer = new ArrayList<String>();
    public Reader(String dir){
        this.dir = new File(dir);
        this.list = this.dir.listFiles();
        initBuffer();
    }

    public void initBuffer(){
        buffer.clear();
        try{
            currentFile = new Scanner(new FileReader(list[indexOfCurrentFile]));
        }catch(FileNotFoundException e){
            System.out.println("File not found");
        }
        for(int i = 0 ; i < 6; i++){
            buffer.add(currentFile.next());
        }
    }
    //must get file before getNext, getnext will move the cursor
    public String getNext(){
        StringBuilder string = new StringBuilder();
        for(int i = 0 ; i < 6; i++){
            string.append(buffer.get(i));
        }

        if(currentFile.hasNext()){
            buffer.remove(0);
            buffer.add(currentFile.next() + " ");
        }else{
            indexOfCurrentFile++;
            if(this.hasNext()){
                initBuffer();
            }
        }

        return string.toString();
    }

    public boolean hasNext(){
        if(indexOfCurrentFile >= list.length){
            return false;
        }
        return true;
    }

    public String getCurrentFileName(){
        return list[indexOfCurrentFile].getName();
    }

    //------------------------------debuging stuff--------------//
    public void printNames(){
        for(File file: list){
            System.out.println(file.getName());
        }
    }
    public String test(){
        File f = list[0];       
        File file = new File("sm_doc_set/abf0704.txt");
        try{
            currentFile = new Scanner(new FileReader(file));
        }catch(FileNotFoundException e){    
            System.out.println("bad");
        }
        return "" + currentFile.hasNext();
    }
}