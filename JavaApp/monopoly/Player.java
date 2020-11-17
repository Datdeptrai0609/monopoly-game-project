package monopoly;

import java.util.LinkedList;
import java.util.Queue;

public class Player {
	private final Input input;
	private final int JAIL_BLOCK = 8;
	private final Queue<Block> properties;
	private final String playerName;
	private int money;
	private int position;
	private boolean inJail;

	public Player(String playerName) {
		input = new Input();
		money = 200;
		properties = new LinkedList<>();
		position = 0;
		this.playerName = playerName;
		inJail = false;
	}

	public void addProperty(Block block) {
		if (!block.isOwnable())
			throw new IllegalArgumentException("This property cannot be purchased!");
		properties.add(block);
		block.purchase(this);
	}

	public void move(int numSpaces) {
		position += numSpaces;
		int BOARD_SIZE = 31;
		if (position > BOARD_SIZE) {
			position -= (BOARD_SIZE + 1);
			excMoney(200);
		}

		if (position == JAIL_BLOCK) {
      inJail = true;
		}
	}

	public void moveTo(int pos) {
		if (pos < position && !inJail)
			excMoney(200);
		position = pos;

    if (position == JAIL_BLOCK) {
      inJail = true;
    }
	}

	public int position() {
		return position;
	}

	public Queue<Block> properties() {
    return properties;
	}

	public String name() {
		return playerName;
	}

	public int getMoney() {
		return money;
	}

	public void excMoney(int money) {
		this.money += money;
	}

	public void toJail() {
    moveTo(JAIL_BLOCK);
	}

	public void sellProp(Block bl) {
		properties.remove(bl);
	}

	public void leaveJail() {
		inJail = false;
	}

	public boolean inJail() {
		return inJail;
	}

	public int getAssets() {
		int assets = this.money;
		for (Block s : properties) {
			assets += s.cost();
			if (s instanceof PropertyBlock)
				assets += getHouseVal((PropertyBlock) s);
		}
		return assets;
	}

	private int getHouseVal(PropertyBlock prop) {
		int numHouses = prop.numHouses();
		int houseCost = prop.houseCost();

		return numHouses * houseCost;
	}

  public boolean inputBool() {
    return input.inputBool();
  }

  public int inputInt(Monopoly.State state) {
    return input.inputInt();
  }
}
