package com.monopoly.game;

public class PropertyBlock implements Block {
  // costs of rent for all possible property states
  private final int rent;
  private final int oneHouseRent;
  private final int twoHouseRent;
  private final int threeHouseRent;
  private final int hotelRent;

  private final int buyPrice; // cost to purchase property
  private final int buildPrice; // cost to purchase one house on property
  private final int pos;
  private final String name;
  private int buildings; // building status
  private boolean owned; // is property owned?
  private boolean festivalStatus;
  private Player owner;

  // construct property, given its rents
  public PropertyBlock(String name, int pos, int rent, int oneHouseRent, int twoHouseRent, int threeHouseRent,
      int hotelRent, int buyPrice, int buildPrice) {
    this.rent = rent;
    this.oneHouseRent = oneHouseRent;
    this.twoHouseRent = twoHouseRent;
    this.threeHouseRent = threeHouseRent;
    this.hotelRent = hotelRent;
    this.buyPrice = buyPrice;
    this.buildPrice = buildPrice;
    buildings = 0;
    owned = false;
    festivalStatus = false;

    this.pos = pos;
    this.name = name;
  }

  public int position() {
    return pos;
  }

  public String name() {
    return name;
  }

  public boolean isOwnable() {
    return true;
  }

  // update status of property to owned
  public void purchase(Player player) {
    owned = true;
    owner = player;
  }

  public boolean isOwned() {
    return owned;
  }

  // update building status by integer input
  public void build(int a) {
    buildings += a;
    if (buildings > 4) {
      throw new IllegalArgumentException("Cannot build past hotel!");
    } else if (buildings < 0) {
      throw new IllegalArgumentException("Cannot build negative buildings!");
    } 
  }

  // cost to purchase property
  public int cost() {
    return buyPrice;
  }

  // return number of buildings owned
  public int numHouses() {
    return buildings;
  }

  // change festival status for property block
  public void setFestival(boolean stt) {
    festivalStatus = stt;
  }

  // get the festival status
  public boolean getFestival() {
    return festivalStatus;
  }

  // return cost to purchase one house
  public int houseCost() {
    return buildPrice;
  }

  // return rent price
  public int rent() {
    int price;
    if (!owned) {
      return 0;
    }
    switch (buildings) {
      case 0:
        price = rent;
        break;
      case 1:
        price = oneHouseRent;
        break;
      case 2:
        price = twoHouseRent;
        break;
      case 3:
        price = threeHouseRent;
        break;
      case 4:
        price = hotelRent;
        break;
      default:
        return 0;
    }
    if (festivalStatus) {
      return price * 2;
    }
    return price;
  }

  public Player owner() {
    return owner;
  }

  public void reset() {
    owned = false;
    buildings = 0;
    festivalStatus = false;
    owner = null;
  }

  public String toString() {
    if (numHouses() == 4 && festivalStatus) {
      return name + " - Hotel" + " - Festival";
    } else if (numHouses() == 4) {
      return name + " - Hotel";
    } else if (numHouses() > 0 && festivalStatus) {
      return name + " - " + numHouses() + " Houses" + " - Festival";
    } else if (numHouses() > 0) {
      return name + " - " + numHouses() + " Houses";
    } else if (festivalStatus) {
      return name + " - Festival";
    } else {
      return name;
    }
  }

  public String priceInfo() {
    return String.format("$%d\n$%d\n$%d\n$%d\n$%d\n$%d\n$%d", rent, oneHouseRent, twoHouseRent, threeHouseRent, hotelRent, buyPrice, buildPrice);
  }
}
