/**
 * Kyle Zhou
 * Kz3528
 * BoxOffice
 */
package assignment6;

import java.lang.Thread;
import java.util.*;
public class BookingClient {
  /*
   * @param office maps box office id to number of customers in line
   * @param theater the theater where the show is playing
   */
  int count = 1;    //starts client at number 1
  Theater theater;
  Map<String, Integer> office;
  Map<String, ArrayList<Integer>> clientId; // queue for each boxoffice
  boolean done = false;
  public BookingClient(Map<String, Integer> office, Theater theater) {
    this.office = office;
    this.theater = theater;
    this.clientId = new HashMap<String, ArrayList<Integer>>();

    //initilize all boxoffice queues. Clients have unique id's.
    for(String key:  office.keySet()){
      clientId.put(key, new ArrayList<Integer>());
      for(int i = 0; i < office.get(key); i++){
        clientId.get(key).add(count);
        count++;
      }
    }
  }

  /*
   * Starts the box office simulation by creating (and starting) threads
   * for each box office to sell tickets for the given theater
   *
   * @return list of threads used in the simulation,
   *         should have as many threads as there are box offices
   */
	public List<Thread> simulate() {
    Set<String> keyset = office.keySet();
    List<Thread> threads = new ArrayList<Thread>();
    //Creates thread for each box
    for(String officeId: keyset){
      threads.add(new Thread(new Runnable(){
        @Override
        public void run() {
          while(clientId.get(officeId).size() != 0 && !done){
            //only remove client from queue if he has gotten a ticket
            if(theater.printTicket(officeId, theater.bestAvailableSeat(), clientId.get(officeId).get(0)) != null){
              clientId.get(officeId).remove(0);
            }
            if(theater.getSoldOut()){
              done = true;
            }   
          }
        }
      }));
    }

    for(Thread t: threads){
      t.start();
    }
    return threads;
  }

  public static void main(String[] args){
    //sample output
    
    
    Map<String, Integer> box = new HashMap<String, Integer>();
    Theater the = new Theater(3, 5, "Sunny get 4.0");
    box.put("BX1", 3);
    box.put("BX2", 4);
    box.put("BX3", 3);
    box.put("BX4", 3);
    box.put("BX5", 3);
    
    // Theater the = new Theater(100, 1, "Ouija");
    // box.put("BX1", 50);
    // box.put("BX2", 100);

    // Theater the = new Theater(500, 2, "Ouija");
    // box.put("BX1", 400);
    // box.put("BX2", 600);
    
    

    BookingClient book = new BookingClient(box, the);
    book.simulate();


  }
}
