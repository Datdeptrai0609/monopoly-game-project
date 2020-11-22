package monopoly;

import org.ini4j.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Player {
  private final Input input;
  private final Queue<Block> properties;
  private final String playerName;
  private int money;
  private int position;
  private boolean inJail;
  private ArrayList<TravelBlock> travelList;

  public Player(String playerName) throws IOException {
    input = new Input();
    Wini ini = new Wini(new File("..\\config.ini"));
    money = Integer.parseInt(ini.get("player", "money").toString());
    properties = new LinkedList<>();
    travelList = new ArrayList<TravelBlock>();
    position = 0;
    this.playerName = playerName;
    inJail = false;
  }

  public void addProperty(Block block) {
    if (!block.isOwnable())
      throw new IllegalArgumentException("This property cannot be purchased!");
    properties.add(block);
    if (block instanceof TravelBlock) 
      travelList.add((TravelBlock) block);
    block.purchase(this);
  }

  public void move(int numSpaces, Board board) {
    position += numSpaces;
    int boardSize = board.size();
    if (position >= boardSize) {
      position -= boardSize;
      excMoney(200);
    }

    if (position == board.jailPos()) {
      inJail = true;
    }
  }

  public void moveTo(int pos, Board board) {
    if (pos < position && !inJail)
      excMoney(200);
    position = pos;

    if (position == board.jailPos()) {
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

  public void toJail(Board board) {
    moveTo(board.jailPos(), board);
  }

  public void sellProp(Block bl) {
    properties.remove(bl);
    bl.reset();
    if (bl instanceof TravelBlock) 
      travelList.remove((TravelBlock) bl);
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

  public int travelSize() {
    return travelList.size();
  }

  public boolean inputBool() {
    return input.inputBool();
  }

  public int inputInt() {
    return input.inputInt();
  }
}
