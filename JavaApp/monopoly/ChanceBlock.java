package monopoly;

public class ChanceBlock implements Block {
	private final int DECK_SIZE = 16; //16 cards in either type of deck
	private final Deck deck; //store deck of cards
	private String name = "chance";
	private int pos;

	//construct square of type cards
	public ChanceBlock(int pos) {
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

	//create deck of chance cards
	private void chance() {
		Card[] cards = new Card[DECK_SIZE];

		for (int i = 0; i < DECK_SIZE; i++)
			cards[i] = new Card(i);

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
}
