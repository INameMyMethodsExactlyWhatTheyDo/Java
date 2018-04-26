/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * <Student1 Kyle Zhou>
 * <Student1 KZ3528>
 * Fall 2017
 */

package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	
	// static variables and constants only here.
	static Set<String> dict;		//this will contain all the 5 letter words
	static ArrayList<String> ends;	//this holds the start word and end word
	static boolean run;				//if /quit is called then run will terminite program
	public static void main(String[] args) throws Exception {
		Scanner kb;	// input Scanner for commands
		PrintStream ps;	// output file, for student testing and grading only
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default input from Stdin
			ps = System.out;			// default output to Stdout
		}
		initialize();
		while(run){
			ends = parse(kb);
			if(run == false){
				continue;
			}
			printLadder(getWordLadderBFS(ends.get(0), ends.get(1)));
			printLadder(getWordLadderDFS(ends.get(0), ends.get(1)));
		}
	}
	
	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests.  So call it 
		// only once at the start of main.
		run = true;
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		ArrayList<String> ends = new ArrayList<String>();
		ends.add(keyboard.next());
		if(ends.get(0).equals("/quit")){
			run = false;
			return null;
		}
		ends.add(keyboard.next());
		return ends;
	}

	/**
	 * DFS implementation. It recurssivily calls the helper method and is limited to 300 levels
	 * if it failed to find a word ladder then increase the threshhold
	 * down to prevent stack over flow.
	 * Uses a priority queue to search closer to the end word
	 * Outputs to councol if ladder was found or not
	 */
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		//System.out.println("DFS begin");
		// Returned list should be ordered start to end.  Include start and end.
		// If ladder is empty, return list with just start and end.
		if(run == false){
			return null;
		}
		Set<String> dict = makeDictionary();
		UndirectedGraph graph = new UndirectedGraph(dict);
		ArrayList<String> ladder;
		start = start.toUpperCase();
		end = end.toUpperCase();
		recursiveSearch(graph, start, end, 300);
		//ladder found
		if(graph.getMap().get(end).isVisit()){
			System.out.println("a " + countLadder(graph,start, end) 
								+ "-rung word ladder exists between " 
								+ start.toLowerCase() + " and " + end.toLowerCase() + ".");
			ladder = makeLadder(graph, start, end);
		}else{
			System.out.println("no word ladder can be found between " 
								+ start.toLowerCase() + " and " + end.toLowerCase() + ".");
			ladder = new ArrayList<String>();
			ladder.add(start.toLowerCase());
			ladder.add(end.toLowerCase());
		}
		return ladder; // replace this line later with real return
	}
	
	/**
	 * BFS search for end word. Always finds shortest path
	 * returns an arraylist with the complete word ladder, or just the first and last words
	 * During the run, it will output to councole if it was sucsessful or not in finding the word ladder
	 */
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		if(run == false){
			return null;
		}
		Set<String> dict = makeDictionary();
		UndirectedGraph graph = new UndirectedGraph(dict);
		Queue<String> q = new LinkedList<String>();
		ArrayList<String> ladder;
		ArrayList<String> edges;
		String head;
		String key;
		start = start.toUpperCase();
		end = end.toUpperCase();
		q.add(start);
		graph.getMap().get(start);
		while(!q.isEmpty()){
			//remove head of q and add it's edges to q'
			head = q.poll();
			edges = graph.getMap().get(head).getEdge();
			for(int i = 0 ; i < edges.size(); i++){
				key = edges.get(i);
				if(!graph.getMap().get(key).isVisit()){
					graph.getMap().get(key).visit();
					graph.getMap().get(key).setParent(head);
					//if we have found the end
					if(key.equals(end)){
						System.out.println("a " + countLadder(graph,start, end) 
							+ "-rung word ladder exists between " 
							+ start.toLowerCase() + " and " + end.toLowerCase() + ".");
						ladder = makeLadder(graph, start, end);
						return ladder;
					}
					q.add(key);
				}
			}	
		}
		System.out.println("no word ladder can be found between " 
		+ start.toLowerCase() + " and " + end.toLowerCase() + ".");
		ladder = new ArrayList<String>();
		ladder.add(start.toLowerCase());
		ladder.add(end.toLowerCase());
		return ladder; 
	}
	
	/**
	 * Output to councol the word ladder if it exist.
	 * if not then it just outputs the frist and last words
	 */
	public static void printLadder(ArrayList<String> ladder) {
		if(run == false){
			return;
		}
		if(ladder != null){
			for(String word: ladder){
				System.out.println(word);
			}
		}
	}

	// Other private static methods here
	/**
	 * This Method is recusivly called in DFS.
	 * It priorities edges that are closer to the end word by using a priority queue
	 * disregard return statement as it is used for the recursive implementaion
	 * Limited depth search to 300 levels to prevent SOF
	 */
	private static boolean recursiveSearch(UndirectedGraph graph, String key, String end, int n){
		ArrayList<String> edges = graph.getMap().get(key).getEdge();
		
		Queue<String> pri = new PriorityQueue<String>(new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				return (diff(s1.toLowerCase(), ends.get(1)) - diff(s2.toLowerCase(), ends.get(1)));
			}
		});
		for(String x : edges){
			pri.offer(x);
		}
		if(n == 0){
			return false;
		}
		String word;
		for(int i = 0; i < edges.size(); i++){
			word = pri.poll();
			if(!graph.getMap().get(word).isVisit()){
				graph.getMap().get(word).visit();
				graph.getMap().get(word).setParent(key);
				//this is the base case;
				if(word.equals(end)){
					graph.getMap().get(end).visit();
					graph.getMap().get(end).setParent(key);
					return true;
				//this is the recursive call
				} else if(recursiveSearch(graph, word, end, n - 1)){
					return true;
				} 
			}
		}
		return false;
	}

	/**
	 * This methods returns by how many letter each charactor differs by
	 * @return String
	 */
	private static int diff(String s1, String s2){
		int diff = 0;
		for(int i = 0; i < s1.length(); i++){
			if(s1.charAt(i) != s2.charAt(i)){
				diff++;
			}
		}
		return diff;
	}

	/**
	 * This method helps contruct the Ladder from the graph
	 * @return ArrayList<String>
	 */
	private static ArrayList<String> makeLadder(UndirectedGraph graph, String start, String end){
		ArrayList<String> ladder = new ArrayList<String>();
		String parent = graph.getMap().get(end).getParent();
		ladder.add(end.toLowerCase());
		while(!parent.equals(start)){
			ladder.add(parent.toLowerCase());
			parent = graph.getMap().get(parent).getParent();
		}
		ladder.add(start.toLowerCase());
		Collections.reverse(ladder);
		return ladder;
	}
	
	/**
	 * This method counts the number of ladders in the graph 
	 * @return int
	 */
	private static int countLadder(UndirectedGraph graph, String start, String end){
		String parent = graph.getMap().get(end).getParent();
		int count = 0;
		while(!parent.equals(start)){
			parent = graph.getMap().get(parent).getParent();
			count++;
		}
		return count;
	}

	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
}
