package monopoly;

public class TravelBlock implements Block {
	private final int COST = 200;
	private final String name;
	private final int pos;
  private final TravelBlock[] others = new TravelBlock[4];
	private int numOwned;  //number of travelBlock owned by a player
	private Player owner;
  private boolean festivalStatus;
	private boolean owned;  //is property owned?

	//constructor
	public TravelBlock(String name, int pos) {
		numOwned = 1;
    festivalStatus = false;
		this.name = name;
		this.pos = pos;
	}

  public void createGroup(TravelBlock a, TravelBlock b, TravelBlock c, TravelBlock d) {
    others[0] = a;
    others[1] = b;
    others[2] = c;
    others[3] = d;
  }

	private void updateOwners() {
		numOwned = 1;
		for (TravelBlock r : others){
			if (r.isOwned() && r.owner().equals(owner))
				numOwned++;
		}
	}

	public int position() {
		return pos;
	}

	public String name() {
		return name;
	}

	//update status of property to owned
	public void purchase(Player player) {
		owned = true;
		owner = player;

		updateOwners();
	}

	public boolean isOwnable() {
		return true;
	}

  public void setFestival(boolean stt) {
    festivalStatus = stt;
  }

  public boolean getFestival() {
    return festivalStatus;
  }

	//return rent owed
	public int rent() {
    int price;
		updateOwners();

		switch (numOwned) {
			case 1:
        price = 25;
        break;
			case 2:
        price = 50;
        break;
			case 3:
        price = 100;
        break;
			case 4:
        price = 100;
        break;
      case 5:
        price = 100;
        break;
			default:
				return 0;
		}
    if (festivalStatus) 
      return price * 2;
    return price;
	}

	public boolean isOwned() {
		return owned;
	}

	public Player owner() {
		return owner;
	}

	public int cost() {
		return COST;
	}

	public String toString() {
    if (festivalStatus) 
      return name + " - Festival";
		return name;
	}
}
