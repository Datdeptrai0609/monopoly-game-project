/**
 * This is a monopoly game for OOP Project
 * Author: Vo Anh Viet
*/
package monopoly;

import org.ini4j.*;

import java.io.File;
import java.io.IOException;

public class TravelBlock implements Block {
  private final int cost;
  private final String name;
  private final int pos;
  private Player owner;
  private boolean festivalStatus;
  private boolean owned; // is property owned?

  // constructor
  public TravelBlock(String name, int pos) throws IOException {
    // Read config.ini file
    Wini ini = new Wini(new File("..\\config.ini"));
    cost = Integer.parseInt(ini.get("travel", "buy").toString());
    owned = false;
    festivalStatus = false;
    this.name = name;
    this.pos = pos;
  }

  public int position() {
    return pos;
  }

  public String name() {
    return name;
  }

  // update status of property to owned
  public void purchase(Player player) {
    owned = true;
    owner = player;
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

  // return rent price = cost of 1 TravelBlock * number TravelBlock owner has
  public int rent() {
    int price = cost * owner.travelSize();
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
    return cost;
  }

  public void reset() {
    owned = false;
    owner = null;
    festivalStatus = false;
  }

  public String toString() {
    if (festivalStatus) {
      return name + " - Festival";
    }
    return name;
  }
}
