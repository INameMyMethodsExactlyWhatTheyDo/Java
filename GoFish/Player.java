import java.util.ArrayList;
import java.util.Random;

public class Player {
	
	ArrayList<Card> hand = new ArrayList<Card>();		//Holds current card in hand
	ArrayList<Card> book = new ArrayList<Card>();		//Holds card 1 of booked pair
	ArrayList<Card> bookCopy = new ArrayList<Card>();	//Holds card 2 of booked pair
	private Random ran = new Random(System.nanoTime());	
	private String name;			
	/**
	 * Default constructor for class player
	 */
	public Player() {

		name = "kyle" + ran.nextInt((int)(System.nanoTime()%100));
	}
	/**
	 * Overloaded constructor with a name
	 * @param name will be the name of the player
	 */
	public Player(String name) {
		this.name = name;
	}
	/**
	 * Will add a given card to the players hand
	 * @param c a Card to be added to the players hand
	 */
	public void addCardToHand(Card c) {
		hand.add(c);
	}
	/**
	 * Given a card the method will search hand and 
	 * remove a card with the same rank and suit
	 * @param c this is the card that contains the attributes of the to be removed card
	 * @return	the card that was removed form hand
	 */
	public Card removeCardFromHand(Card c) {
		Card card = new Card(c.getRank(), c.getSuit());
		for(int i = 0; i < hand.size(); ++i){
			if(hand.get(i).toString().equals(c.toString())){
				hand.remove(i);
				return card;
			}
		}
		return card;
	}
	/**
	 * Given a card the method will search hand and 
	 * remove a card with same rank
	 * @param c the card with the target rank to be removed
	 * @return	the card that was removed from hand
	 */
	public Card removeRankFromHand(Card c) {
		Card card = null;
		for(int i = 0; i < hand.size(); ++i){
			if(hand.get(i).getRank() == c.getRank()){
				card = new Card(hand.get(i).getRank(), hand.get(i).getSuit());
				hand.remove(i);
				return card;
			}
		}
		return card;
	}
	/**
	 * Getter for the name of player
	 * @return	a string that contains the name of player
	 */
	public String getName() {
		return name;
	}
	/**
	 * Will generate a string that describes all cards 
	 * in hand
	 * @return a toString of all cards in hand
	 */
	public String handToString() {
		String s = new String();
		for(int i = 0; i < hand.size(); i++) {
			s = s + hand.get(i) + "\n";
		}
		return s;
	}
	/**
	 * Will generate a string that describes all cards that are booked
	 * @return a toString of all cards booked
	 */
	public String bookToString() {
		String s = new String();
		for(int i = 0; i < book.size(); i++) {
			s = s + book.get(i) + " " + bookCopy.get(i) + ", \n";
		}
		return s;
	}
	/**
	 * Getter for the number of cards in hand
	 * @return an int with the number of cards in hand
	 */
	public int getHandSize() {
		return hand.size();
	}
	/**
	 * Getter for the number of cards in book
	 * @return an int with the number of cards in book
	 */
	public int getBookSize() {
		return book.size();
	}
	/**
	 * This method will search the hand for a pair, if pair
	 * was found then method will remove both cards from hand
	 * and add them to book and book copy
	 * @return	true if pair was booked, else false
	 */
    public boolean checkHandForBook() {
    	int[] handRank = fillHandRank();
    	for(int i = 0; i < 13; i++) {
    		//found 4 of a kind
    		if(handRank[i] >= 2) {
    			for(int k = 0; k < hand.size(); k++) {
    				if(isRank(i + 1, hand.get(k))) {
    					book.add(removeCardFromHand(hand.get(k)));
    					break;
    				}
    			}
    			for(int k = 0; k < hand.size(); k++) {
    				if(isRank(i + 1, hand.get(k))) {
    					bookCopy.add(removeCardFromHand(hand.get(k)));
    					break;
    				}
    			}
    			return true;
    		}
    	}
    	return false; 
    }
    /**
     * Helper method for checkHandForBook
     * Checks if card a is rank r
     * @param r	target rank
     * @param a	target card
     * @return
     */
    private boolean isRank(int r, Card a) {
    	return a.getRank() == r;
    }
    /**
     * Helper method for checkHandForBook
     * Will generate an int[] that contains 
     * the number of cards a hand has of each rank
     * @return	int[] with 13 entries containing the number of each rank
     */
    private int[] fillHandRank() {
    	int[] handRank = new int[13];
    	for(int i = 0; i < hand.size(); i ++) {
    		handRank[hand.get(i).getRank() - 1] += 1;
    	}
    	return handRank;
    }
    /**
     * Checks if rank is in hand
     * @param c target card
     * @return	true if card is found in hand, else false
     */
    public boolean rankInHand(Card c) {
    	for(int i = 0; i < hand.size(); i ++) {
    		if(isRank(c.getRank(), hand.get(i))){
    			return true;
    		}
    	}
    	return false;
    }
    /**
     * Randomly selects one card in hand
     * @return	the selected card from hand
     */
    public Card chooseCardFromHand() {
    	Card c;
    	if(hand.size() == 0) {
    		return null;
    	}
    	int index = ran.nextInt(hand.size());
    	c = new Card(hand.get(index).getRank(), hand.get(index).getSuit());
    	return c;
    }
    /**
     * Checks to see if a hand contains a certain card
     * @param c target card
     * @return	true if card is found in hand, else false
     */
    public boolean cardInHand(Card c) {
    	for(int i = 0; i < hand.size(); i++) {
    		if(hand.get(i).toString().equals(c.toString())) {
    			return true;
    		}
    	}
    	return false;
    }
    /**
     * Checks to see if there a hand contains a rank
     * @param c Card that contains the target rank
     * @return	true if hand contains such rank, else false
     */
    public boolean sameRankInHand(Card c) {
    	for(int i = 0 ; i < hand.size(); i++) {
    		if(hand.get(i).getRank() == c.getRank()) {
    			return true;
    		}
    	} 
    	return false; 
    }
    /**
     * Checks to see if hand is empty
     * @return	true is hand is not empty, else false
     */
    public boolean handNotEmpty() {
    	if(this.getHandSize() == 0) {
    		return false;
    	}else {
    		return true;
    	}
    }
    /**
     * Reinitialize all fields of player 
     */
    public void reset() {
    	hand = new ArrayList<Card>();
    	book = new ArrayList<Card>();
    	bookCopy = new ArrayList<Card>();
    }
}
