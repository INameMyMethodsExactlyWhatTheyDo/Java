
import java.util.ArrayList;
import java.util.*;
public class Deck {
	private ArrayList<Card> deck = new ArrayList<Card> ();	//Contains all cards in deck
	final int NUM_CARDS = 52;  								//for this kind of deck
	private Random ran = new Random(System.nanoTime());	
	/**
	 * Default constructor for Deck
	 * Creates a sorted deck of cards 
	 */
	public Deck() {
		for(int i = 0; i < 4; i++) {
			for(int k = 1; k <= 13; k++){
				switch(i) {
				case 0:
					deck.add(new Card(k,'s'));
					break;
				case 1:
					deck.add(new Card(k,'h'));
					break;
				case 2:
					deck.add(new Card(k,'c'));
					break;
				case 3:
					deck.add(new Card(k,'d'));
					break;
				}
			}
		}
	}
	/**
	 * This method will shuffle the deck of cards randomly 
	 */
	public void shuffle() {
		Collections.shuffle(deck,ran);
	}
	/**
	 * Print all contents of deck to console 
	 */
	public void printDeck() {
		for(int i = 0; i < deck.size(); i++){
			System.out.println("" + deck.get(i));
		}
	}
	/**
	 * Will remove top card from deck and return it 
	 * @return	the top card from deck 
	 */
	public Card dealCard() {
		int index = deck.size() -1;
		int cardRank = deck.get(index).getRank();
		Suits cardSuit = deck.get(index).getSuit();
		Card card = new Card(cardRank, cardSuit);
		deck.remove(index);
		return card;
	}
	/**
	 * This will return and int with the number of cards left in deck
	 * @return	number of cards left in deck
	 */
	public int numCardsLeft(){
		return deck.size();
	}
}

