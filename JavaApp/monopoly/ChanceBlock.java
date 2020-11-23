/**
 * This is a monopoly game for OOP Project
 * Author: Vo Anh Viet
*/
package monopoly;

import org.ini4j.*;

import java.io.File;
import java.io.IOException;

public class ChanceBlock implements Block {
	private final Deck deck; //store deck of cards
	private String name = "chance";
	private int pos;

	//construct square of type cards
	public ChanceBlock(int pos) throws IOException{
		deck = new Deck();
    chance();
		this.pos = pos;
	}

	public boolean isOwnable() {
		return false;
	}

	public int position() {
		return pos;
	}

	public String name() {
		return name;
	}

	public boolean isOwned() {
		return false;
	}

  // Create cards assign to deck and shuffle them
	private void chance() throws IOException {
    // Open file config.ini
    Wini ini = new Wini(new File("..\\config.ini"));
    // Get number of all chance cards
    int cardSize = Integer.parseInt(ini.get("cards", "size").toString()); 
		Card[] cards = new Card[cardSize];

    // Create Card object and save to cards array
    for (int i = 0; i < cardSize; i++) {
			cards[i] = new Card(i + 1);
    }

		deck.initialize(cards);
	}

	//draw next card
	public Card draw() {
		return deck.drawCard();
	}

	public int cost() {
		return 0;
	}

	public void purchase(Player player) {
	}

	public int rent() {
		return 0;
	}

  public void setFestival(boolean stt) {}

  public boolean getFestival() {
    return false;
  }

	public Player owner() {
		return null;
	}

	public String toString() {
		return name;
	}

	public Iterable<Card> cards() {
		return deck.cards();
	}

  public void reset() {}
}
