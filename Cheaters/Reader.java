import java.util.*;
import java.io.*;

public class Reader{
    File dir;
    File[] list;
    int indexOfCurrentFile = 0;
    int n;
    Scanner currentFile;
    ArrayList<String> buffer = new ArrayList<String>();
    /**
     * Dir is the directory that the files live in
     * n is the buffer size
     */
    public Reader(String dir, int n){
        this.dir = new File(dir);
        this.list = this.dir.listFiles();
        this.n = n;
        initBuffer();
    }

    /**
     * called when initilizing a new file
     * fills buffer with first n words
     */
    public void initBuffer(){
        buffer.clear();
        try{
            currentFile = new Scanner(new FileReader(list[indexOfCurrentFile]));
        }catch(FileNotFoundException e){
            System.out.println("File not found");
        }
        for(int i = 0 ; i < n; i++){
            buffer.add(currentFile.next());
        }
    }
    /**
     * Returns the next 6 set from the input
     * will walk accoss all files 
     * at the end of each file it will re init the 
     * buffer. and increment the file counter
     */
    public String getNext(){
        StringBuilder string = new StringBuilder();
        for(int i = 0 ; i < n; i++){
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
    /**
     * Get next can be called if this is ture;
     */
    public boolean hasNext(){
        if(indexOfCurrentFile >= list.length){
            return false;
        }
        return true;
    }
    /**
     * Returns the current file we are reading
     * note this should be called before getnext 
     * because getnext will push the file pointer to
     * the next file.
     */
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