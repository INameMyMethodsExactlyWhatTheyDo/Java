import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class GoFishGame {
	
	Player playerA;		//The first input will be A second will be B
	Player playerB;		
	Deck deck;			
	boolean playerANext;	//true for playerA next, false for playerB next
	File file = new File ("GoFish_results.txt");	//The output File
	PrintWriter writer ;			
	/**
	 * Constructor for a GoFishGame
	 * This will create two players and one deck
	 * @param a	the name of the PlayerA
	 * @param b	the name of the PlayerB
	 * @throws FileNotFoundException
	 */
	public GoFishGame(String a, String b) throws FileNotFoundException {
		playerA = new Player(a);
		playerB = new Player(b);
		writer = new PrintWriter ("GoFish_results.txt");
	}
	/**
	 * This will initialize the game by shuffling the deck 
	 * and dealing each player 7 cards
	 * @throws IOException
	 */
	public void init() throws IOException {
		deck = new Deck();
		deck.shuffle();
		deal();
		playerANext = true;
		for(int i = 0 ; i < 3; i ++) {
			if(playerA.checkHandForBook()) {
				print("book", playerA.getName(), playerB.getName(), 
						playerA.book.get(playerA.getBookSize() - 1));
			}
			if(playerB.checkHandForBook()) {
				print("book", playerB.getName(), playerA.getName(), 
						playerB.book.get(playerB.getBookSize() - 1));
			}
		}
	}
	/**
	 * This will process the flow of the game 
	 * alternating turns between two players
	 * @throws IOException
	 */
	public void runGame() throws IOException {
		init();
		while(!isOver()) {
			if(playerANext) {
				makeTurn(playerA, playerB);
				playerANext = !playerANext;
			}else {
				makeTurn(playerB, playerA);
				playerANext = !playerANext;
			}	
			print(" ", null, null, null);
		}
		endGame();
		deck.printDeck();
	}
	/**
	 * This will determine the winner of the game
	 * and print of the scores
	 */
	public void endGame() {
		try {
			print("end", playerA.getName(), playerB.getName(), null);
		} catch (IOException e) {
		}
		writer.close();
	}	
	/**
	 * This method will deal each player 7 cards
	 */
	private void deal() {
		for(int i = 0; i < 7; i++) {
			playerA.addCardToHand(deck.dealCard());
			playerB.addCardToHand(deck.dealCard());

		}
	}
	/**
	 * Checks to see if the game is over	
	 * @return	true if game is over, else false
	 */
	private boolean isOver() {
		if(playerA.getBookSize() + playerB.getBookSize() == 26) {
			return true;
		}
		return false;
	}
	/**
	 * This method will run one turn with one player asking another player
	 * for a card, the other player will give the card if he has one else
	 * the asking player will have to draw
	 * @param a	the asking player
	 * @param b	the reactive player
	 * @throws IOException
	 */
	private void makeTurn(Player a, Player b) throws IOException {
		Card c;
		Card c1;
		if(a.handNotEmpty()) {
			c = a.chooseCardFromHand();
			print("ask", a.getName(), b.getName(), c);
			if(b.rankInHand(c)){
				a.addCardToHand(b.removeRankFromHand(c));
				playerANext = !playerANext;
				print("yes", a.getName(), b.getName(), c);
			}else {
				print("go", a.getName(), b.getName(), c);
				if(deck.numCardsLeft() != 0) {
					c1 = deck.dealCard();
					a.addCardToHand(c1);
					print("draw", a.getName(), b.getName(), c1);
				} else {
					//print empty
				}
			}
			if(a.checkHandForBook()) {
				c = a.book.get(a.getBookSize() - 1);
				print("book", a.getName(), b.getName(), c);
			}
		} else {
			c = deck.dealCard();
			a.addCardToHand(c);
			print("draw", a.getName(), b.getName(), c);
		} 
	}
	/**
	 * Checks if the game was a draw
	 * @return	true if the game was a draw, else false
	 */
	private boolean draw() {
		if(playerA.getBookSize() == playerB.getBookSize()) {
			return true;
		}
		return false;
	}
	/**
	 * Checks to see if playerA is the winner
	 * @return true if playerA won, else false
	 */
	private boolean didAWin() {
		if(playerA.getBookSize() > playerB.getBookSize()) {
			return true;
		}
		return false;
	}
	/**
	 * This method will print all dialog of the game to the console
	 * The printed line will be determined by the sel field and the 
	 * arguments passed in
	 * @param sel	the switch case controller
	 * @param p1	the asking player 
	 * @param p2	the reacting player
	 * @param c		the Card of the context
	 * @throws IOException
	 */
	private void print(String sel, String p1, String p2, Card c) throws IOException{
		switch (sel) {
		case "ask":
			writer.println(p1 + " askes - Do you have a " + c.getRank() + "?");
			break;
		case "go":
			writer.println(p2 + " says - Go Fish!");
			break;
		case "yes":
			writer.println(p2 + " says - Yes, I have a " + c.getRank() + ".");
			break;
		case "draw":
			writer.println(p1 + " draws " + c + ".");
			break;
		case "book":
			writer.println(p1 + " books a pair of " + c.getRank());
			break;
		case " ":
			writer.println("");
			break;
		case "end":
			if(didAWin()) {
				writer.println(playerA.getName() + " Wins with " + playerA.getBookSize() + " pairs.");
				writer.println(playerA.bookToString());
				writer.println(playerB.getName() + " has " + playerB.getBookSize() + " pairs.");
				writer.println(playerB.bookToString());
				return;
			}		
			if(draw()) {
				writer.println("Its a draw, both players have 13 pairs.");
				writer.println(playerA.getName() + " has:");
				writer.println(playerA.bookToString());
				writer.println(playerB.getName() + " has:");
				writer.println(playerB.bookToString());
				return;
			}else {
				writer.println(playerB.getName() + " Wins with " + playerB.getBookSize() + " pairs.");
				writer.println(playerB.bookToString());
				writer.println(playerA.getName() + " has " + playerA.getBookSize() + " pairs.");
				writer.println(playerA.bookToString());
				return;
			}
		}
	}

}
