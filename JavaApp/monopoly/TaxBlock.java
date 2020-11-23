/**
 * This is a monopoly game for OOP Project
 * Author: Vo Anh Viet
*/
package monopoly;

public class TaxBlock implements Block {
	private final String name = "Tax";
	private final int pos;

	public TaxBlock(int pos) {
		this.pos = pos;
	}

	public boolean isOwned() {
		return false;
	}

	public int position() {
		return pos;
	}

	public String name() {
		return name;
	}

	public boolean isOwnable() {
		return false;
	}

  // tax is 10% of all assets of player
	public int tax(int value) {
		return (int) (value * 10 / 100);
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

  public void reset() {}

  public String toString() {
    return name;
  }
}
