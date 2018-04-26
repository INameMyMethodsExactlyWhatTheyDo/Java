public class Card {
	static int TOP_RANK = 13; //King
	static int LOW_RANK = 1; //Ace
	private int rank;  		//Rank of Card
	private Suits suit;		//Suit of Card
	
	/**
	 * The default constructor, Ace of Spades
	 */
	public Card() {
		rank = 1;
		suit = Suits.spade;
	}
	/**
	 * Overload Constructor that will create a card 
	 * with rank r and char s
	 * @param r	int value of target rank
	 * @param s	first char of each suit, ie. s = spades
	 */
	public Card(int r, char s) {
		rank = r;
		suit = toSuit(s);
	}
	/**
	 * Overload Constructor that will create a card 
	 * with rank r and suit s
	 * @param r int value of target rank
	 * @param s	target suit 
	 */
	public Card(int r, Suits s) {
		rank = r;
		suit = s;
	}
	/**
	 * Converts char to suit
	 * @param c	the first char of a suit
	 * @return	the target suit value
	 */
	private Suits toSuit(char c) {
		switch(c) {
		case 's':
			return Suits.spade;
		case 'h':
			return Suits.heart;
		case 'c':
			return Suits.club;			
		case 'd':
			return Suits.diamond;
		}
		return Suits.spade;
	}
	/**
	 * Converts suit value to a string  
	 * @param 	target suit
	 * @return	returns a string that describes the suit
	 */
	private String suitToString(Suits s)
	{
		switch(s) {
		case spade:
			return "s";
		case heart:
			return "h";
		case club:
			return "c";			
		case diamond:
			return "d";
		}
		return "s"; 
	}
	/**
	 * Converts rank to String 
	 * @param r the target rank
	 * @return	a string that describes the rank
	 */
	private String rankToString(int r)
	{
		switch (r) {
		case 1: 
			return "A";
		case 11:
			return "J";
		case 12:
			return "Q";
		case 13:
			return "k";
		}
		return "" + r; //dummy
	}
	/**
	 * Getter for rank value
	 * @return the rank of the card
	 */
	public int getRank() {
		return rank;
	}
	/**
	 * Getter for suit value
	 * @return	the suit of the card
	 */
	public Suits getSuit() {
		return suit;
	}
	/**
	 * Return a string that describes the card
	 */
	public String toString() {
		String s = "";
		
		s = s + rankToString(getRank()) + suitToString(getSuit());
		
		return s;
	}
}


enum Suits {club, diamond, heart, spade};