/**
 * Kyle Zhou
 * Kz3528
 * BoxOffice
 */
package assignment6;

import java.util.*;
public class Theater {

	ArrayList<Seat> seats = new ArrayList<Seat>();
	ArrayList<Ticket> tickets = new ArrayList<Ticket>();
	String show;
	boolean soldout = false;
	/*
	 * Represents a seat in the theater
	 * A1, A2, A3, ... B1, B2, B3 ...
	 */
	static class Seat {
		private int rowNum;
		private int seatNum;
		private boolean sold;

		public Seat(int rowNum, int seatNum) {
			this.rowNum = rowNum;
			this.seatNum = seatNum;
			this.sold = false;
		}

		public int getSeatNum() {
			return seatNum;
		}

		public int getRowNum() {
			return rowNum;
		}

		public boolean isSold(){
			return sold;
		}
		/**
		 * mark a seat as sold so it wont be sold again
		 */
		public boolean sell(){
			if(this.sold){
				return false;
			}
			this.sold = true;
			return true;
		}
		/**
		 * converting number to base 26 and then displaying as a letter.
		 */
		@Override
		public String toString() {
			String name = new String();
			String ret = new String();
			int row = rowNum;
			int buffer = 0;
			int a = 65;
			while(row >= 0){
				buffer = row % 26 + a;
				name = name + (char)buffer;
				row /= 26; 
				row -= 1;
			}
			for(int i = 0; i < name.length(); i++){
				ret = ret + name.charAt(name.length() - i - 1);
			}
			seatNum++;
			ret = ret+ seatNum;
			return ret;
		}
	}

  	/*
	 * Represents a ticket purchased by a client
	 */
	static class Ticket {
		private String show;
		private String boxOfficeId;
		private Seat seat;
	  	private int client;

		public Ticket(String show, String boxOfficeId, Seat seat, int client) {
			this.show = show;
			this.boxOfficeId = boxOfficeId;
			this.seat = seat;
			this.client = client;
		}

		public Seat getSeat() {
			return seat;
		}

		public String getShow() {
			return show;
		}

		public String getBoxOfficeId() {
			return boxOfficeId;
		}

		public int getClient() {
			return client;
		}

		@Override
		public String toString() {
			// TODO: Implement this method to return a string that resembles a ticket
			StringBuilder string = new StringBuilder();
			string.append("-------------------------------\n");
			string.append("| Show: " + show);
			for(int i = 0; i < 30 - show.length() - 8; i++){
				string.append(" ");
			}
			string.append("|\n");

			string.append("| Box Office ID: " + boxOfficeId);
			for(int i = 0; i < 30 - boxOfficeId.length() - 17; i++){
				string.append(" ");
			}
			string.append("|\n");

			string.append("| Seat: " + seat);
			for(int i = 0; i < 30 - seat.toString().length() - 8; i++){
				string.append(" ");
			}
			string.append(" |\n");

			string.append("| Client: " + client);
			for(int i = 0; i < 30 - ("" + client).length() - 10; i++){
				string.append(" ");
			}
			string.append("|\n");
			string.append("-------------------------------\n");
			return string.toString();
		}
	}

	/**
	 * Contructor for a theater. each theater has one movie
	 */
	public Theater(int numRows, int seatsPerRow, String show) {
		this.show = show;
		for(int i = 0; i < numRows; ++i){
			for(int j = 0; j < seatsPerRow; ++j){
				seats.add(new Seat(i, j));
			}
		}
	}

	/*
	 * Calculates the best seat not yet reserved
	 *
 	 * @return the best seat or null if theater is full
   */
	public synchronized Seat bestAvailableSeat() {
		for(Seat x : seats){
			if(!x.isSold()){
				return x;
			}
		}
		if(!soldout){
			soldOut();
		}
		return null;
	}

	/**
	 * This is a helper method that blocks all ticket printing.
	 * Effectivly ends execution.
	 */
	private void soldOut(){
		System.out.println("Sorry, we are sold out!");
		soldout = true;
	}

	public boolean getSoldOut(){
		return soldout;
	}

	/*
	 * Prints a ticket for the client after they reserve a seat
   * Also prints the ticket to the console
	 *
   * @param seat a particular seat in the theater
   * @return a ticket or null if a box office failed to reserve the seat
   */
	public synchronized Ticket printTicket(String boxOfficeId, Seat seat, int client) {
		if(soldout ||seat.isSold()){
			return null;
		}
		Ticket ticket = new Ticket(show, boxOfficeId, seat, client);
		tickets.add(ticket);
		seat.sell();
		System.out.print(ticket);
		
		try{
			Thread.sleep(1000);
		}catch(InterruptedException e){}
		
		return ticket;
	}

	/*
	 * Lists all tickets sold for this theater in order of purchase
	 *
   * @return list of tickets sold
   */
	public List<Ticket> getTransactionLog() {
		return tickets;
	}
}
