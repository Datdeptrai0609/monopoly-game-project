package monopoly;

public interface Block {
	int position();

	String name();

	boolean isOwnable();

	boolean isOwned();

	int cost();

	void purchase(Player player);

	int rent();

  void setFestival(boolean stt);

  boolean getFestival();

	Player owner();

  void reset();

	String toString();
}
