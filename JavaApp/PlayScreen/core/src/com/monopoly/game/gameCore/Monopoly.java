package com.monopoly.game.gameCore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.monopoly.game.gameGUI.WaitingRoom;
import com.monopoly.game.mqtt.Publish;

import org.eclipse.paho.client.mqttv3.MqttException;

public class Monopoly {
  private final Dice dice;
  private State state;
  private boolean lost = false;
  private String[] names = {"girl", "ninja", "cat", "santa", "boy", "dinasour"};

  public Monopoly(int[] playerId) throws IOException {
    // Create board and initialize players as well as some neccessary state
    state = new State();
    state.players = new LinkedList<>();
    state.current = null;
    dice = new Dice();
    state.board = new Board();
    initialize(playerId);
  }

  // Handle each turn of each player
  public void turn(String topic) {
    System.out.println("It's " + state.current.name() + "'s turn");
    System.out.println("Are you ready to dice?");
    if (state.current.inputBool(topic)) {
      // Start dice
      dice.roll();
      if (dice.getDouble()) {
        System.out.println("DOUBLE!");
      }
      // Handle dice when in Jail
      if (state.current.inJail()) {
        if (dice.getDouble()) {
          state.current.leaveJail();
          dice.setDouble();
        } else {
          System.out.println("You did not roll a double dice");
          return;
        }
      }


      System.out.println("You roll a " + dice.getVal());
      state.current.move(dice.getVal(), state.board);
    }
  }

  // Handle action at destination
  public void handleBlock(Player player, Block block, Select propsSelect) {
    System.out.println("You land on " + state.board.getBoard()[player.position()].name());
    boolean owned = block.isOwned();
    boolean ownable = block.isOwnable();

    if (!owned && ownable) {
      buyBlock(player, block);
    } else if (ownable) {
      rentBlock(player, block, propsSelect);
    } 
    else if (block instanceof TaxBlock) {
      payTax(player, (TaxBlock) block, propsSelect);
    } else if (block instanceof JailBlock) {
      state.current.toJail(state.board);
    } else if (block instanceof FestivalBlock) {
      organizeFestival(player, propsSelect);
    } else {
      return;
    }
  }

  public void organizeFestival(Player player, Select propsSelect) {
    // Check can organize festival or not
    boolean canOrganize = false;
    // Player can organize festival if player has any property don't organize yet
    for (Block prop : player.properties()) {
      if (!prop.getFestival()) {
        canOrganize = true;
      }
    }
    if (canOrganize) {
      Block bl;
      // Can only choose property which are not Festival
      while (true) {
        //bl = propsSelect(player);
        bl = propsSelect.select(player);
        if (bl.getFestival()) {
          System.out.println("This property was already organized Festival");
        } else {
          bl.setFestival(true);
          break;
        }
      }
    } else {
      System.out.println("You cannot organize any Festival now!");
    }
  }

  public void payTax(Player player, TaxBlock block, Select propsSelect) {
    int cost = block.tax(player.getAssets());
    System.out.println("You must pay $" + cost);
    if (player.getMoney() < cost) {
      while (true) {
        cost = additionMoney(player, null, cost, propsSelect);
        if (cost == Integer.MIN_VALUE) {
          return;
        } else if (cost <= 0) {
          player.excMoney(cost * -1);
          break;
        }
      }
    } else
      player.excMoney(cost * -1);
  }

  public void buyBlock(Player player, Block block) {
    // Publish buy house to mqtt
    try {
      new Publish().pub(WaitingRoom.PIN + "/gameplayP/" + player.getId() + "/buy", "0");
    } catch (MqttException e) {}
    // Receive and handle
    int cost = block.cost();
    System.out.println("Would you like to purchase " + block.name() + " for " + cost + " (Yes/No)?");
    if (player.inputBool(WaitingRoom.PIN + "/gameplayM/" + player.getId() + "/buy")) {
      if (player.getMoney() >= cost) {
        player.excMoney(cost * -1);
        purchase(player, block);
      } else {
        System.out.println("You don't have enough money!");
      }
    }
  }

  public void rentBlock(Player player, Block block, Select propsSelect) {
    int cost = block.rent();
    Player owner = block.owner();
    // Land on your property
    if (player.name().equals(owner.name())) {
      // Handle build House here
      if (block instanceof PropertyBlock) {
        PropertyBlock propsBlock = (PropertyBlock) block;
        System.out.println("Your " + propsBlock.name() + " block is having " + propsBlock.numHouses() + " house(s)");
        if (player.getMoney() < propsBlock.houseCost()) {
          System.out.println("You don't have enough money to build house in this property!");
          return;
        }
        // Pulish to mqtt that player can build house here
        try {
          new Publish().pub(WaitingRoom.PIN + "/gameplayP/" + player.getId() + "/buy", "1"); 
        } catch (MqttException e) {}
        // Receive and handle build house
        System.out.println("Would you like to build houses here?");
        if (player.inputBool(WaitingRoom.PIN + "/gameplayM/" + player.getId() + "/buy")) {
          if (player.getMoney() < propsBlock.houseCost()) {
            System.out.println("You don't have enough money!");
          } else {
            System.out.println("You can only build a house per building time!");
            propsBlock.build(1);
            player.excMoney(propsBlock.houseCost() * -1);
          }
          // when first build can build 2 houses, then only 1 house
          //switch (propsBlock.numHouses()) {
            //case 0:
            //case 1:
              //System.out.println("You can only build up to 2 houses now. How many houses would you like to build?");
              //while (true) {
                //int houseInput = player.inputInt();
                //if (propsBlock.numHouses() + houseInput >= 0 && propsBlock.numHouses() + houseInput <= 2) {
                  //if (player.getMoney() < propsBlock.houseCost() * houseInput) {
                    //System.out.println("You don't have enough money!");
                    //continue;
                  //}
                  //propsBlock.build(houseInput);
                  //player.excMoney(propsBlock.houseCost() * houseInput * -1);
                  //break;
                //}
              //}
              //break;
            //case 2:
            //case 3:
              //if (player.getMoney() < propsBlock.houseCost()) {
                //System.out.println("You don't have enough money!");
              //} else {
                //System.out.println("You can only build a house per building time!");
                //propsBlock.build(1);
                //player.excMoney(propsBlock.houseCost() * -1);
              //}
              //break;
            //default:
              //System.out.println("You cannot build houses in this property anymore!");
          //}
        }
      }
      return;
    }

    // Land on different player's property
    System.out.println("You have landed on " + block.name() + " and must pay " + cost + " in rent.");
    if (player.getMoney() >= cost) {
      // send to mqtt that enough money to pay for rent
      try {
        new Publish().pub(WaitingRoom.PIN + "/gameplayP/" + player.getId() + "/buy", "2");
      } catch (MqttException e) {}
      // receive and handle
      if (player.inputBool(WaitingRoom.PIN + "/gameplayM/" + player.getId() + "/buy")) {
        player.excMoney(cost * -1);
        owner.excMoney(cost);
      }
    } else {
      // send to mqtt that don't have enough money to pay for rent
      try {
        new Publish().pub(WaitingRoom.PIN + "/gameplayP/" + player.getId() + "/buy", "3");
      } catch (MqttException e) {}
      // receive and handle
      int remainCost = cost;
      while (true) {
        remainCost = additionMoney(player, owner, remainCost, propsSelect);
        // player lose
        if (remainCost == Integer.MIN_VALUE) {
          return;
        } else if (remainCost <= 0) {
          // player pay all money but have redundant money
          player.excMoney(remainCost * -1);
          owner.excMoney(cost);
          break;
        }
      }
    }

    // Set the festival price to normal price when have any player go in
    if (block.getFestival()) {
      block.setFestival(false);
    }
  }

  // Handle drawCard in ChanceBlock
  public void drawCard(Player player, Card card, Select propsSelect) {
    switch (card.action()) {
      case BANK_MONEY:
        if (card.value() >= 0) {
          player.excMoney(card.value());
        } else {
          int cost = -1 * card.value();
          if (player.getMoney() < cost) {
            while (true) {
              cost = additionMoney(player, null, cost, propsSelect);
              if (cost == Integer.MIN_VALUE) {
                break;
              } else if (cost <= 0) {
                player.excMoney(cost * -1);
                break;
              }
            }
          } else {
            player.excMoney(cost * -1);
          }
        }
        break;
      case MOVE_TO:
        player.moveTo(card.travelTo(), state.board);
        break;
      default:
        break;
    }
  }

  // Handle sell property for more money
  public int additionMoney(Player player, Player owner, int cost, Select propsSelect) {
    Queue<Block> props = availableAssets(player);
    int availableAssets = sellVal(props) + player.getMoney();

    if (availableAssets < cost) {
      lose(player, owner);
      return Integer.MIN_VALUE;
    } else if (cost <= player.getMoney()) {
      player.excMoney(cost * -1);
      return 0;
    } else {
      // Chose sell house to continue the game
      System.out.println("You need additional funds!");

      System.out.println("Which property would you like to sell?");
      System.out.println("Please enter number.");
      //Block bl = propsSelect(player);
      Block bl = propsSelect.select(player);

      cost -= bl.cost();
      if (bl instanceof PropertyBlock) {
        PropertyBlock prop = (PropertyBlock) bl;
        cost -= prop.numHouses() * prop.houseCost() / 2;
      }
      player.sellProp(bl);
      return cost;
    }
  }

  public Queue<Block> availableAssets(Player player) {
    Iterable<Block> props = player.properties();
    Queue<Block> avail = new LinkedList<>();
    for (Block bl : props)
      avail.add(bl);
    return avail;
  }

  // Calculate the price of sell all properties player has
  public int sellVal(Queue<Block> props) {
    int totalMoney = 0;
    for (Block bl : props) {
      totalMoney += bl.cost();
      if (bl instanceof PropertyBlock) {
        PropertyBlock prop = (PropertyBlock) bl;
        totalMoney += prop.numHouses() * prop.houseCost() / 2;
      }
    }
    return totalMoney;
  }

  // When a player lose game, change their money and property to owner if not
  // reset all property
  public void lose(Player player, Player owner) {
    // Send lose to mqtt
    try {
      new Publish().pub(WaitingRoom.PIN + "/gameplayP/" + player.getId() + "/lose", "1");
    } catch (MqttException e) {}
    if (owner != null) {
      owner.excMoney(player.getMoney());
      player.excMoney(-1 * player.getMoney());
      for (Block bl : player.properties()) {
        owner.addProperty(bl);
        bl.purchase(owner);
      }
    } else {
      for (Block bl : player.properties()) {
        bl.reset();
      }
    }
    lost = true;
    System.out.println(player.name() + " has lost!");
  }

  // Return a Block which player select in their asset
  //public Block propsSelect(Player player) {
    //System.out.println("You own the following properties:");
    //Iterable<Block> props = player.properties();

    //int counter = 1;
    //for (Block bl : props)
      //System.out.println(counter++ + ") " + bl.name());

    //while (true) {
      //int propNum = player.inputInt();
      //int propState = 1;

      //for (Block bl : props) {
        //if (propState++ == propNum) {
          //return bl;
        //}
      //}

      //System.out.println("Please select a valid property.");
    //}
  //}

  // Select the destination when in BusBlock
  public int busSelect(Player player) {
    int busNum;
    try {
      new Publish().pub(WaitingRoom.PIN + "/gameplayP/" + player.getId() + "/bus", "1");
    } catch (MqttException e) {}
    System.out.println("You can choose one block in this list:");
    for (Block bl : state.board.getBoard()) {
      if (bl instanceof BusBlock) {
        continue;
      }
      System.out.println(bl.position() + ") " + bl.name());
    }

    while (true) {
      busNum = player.inputInt(WaitingRoom.PIN + "/gameplayM/" + player.getId() + "/bus");
      if (busNum >= 0 && busNum < state.board.size() && busNum != state.board.busPos()) {
        break;
      } else {
        System.out.println("Please select valid block");
      }
    }
    return busNum;
  }

  // purchase a property
  public void purchase(Player player, Block block) {
    player.addProperty(block);
    block.purchase(player);
  }

  // initialize player and random at the beginning of the game
  public void initialize(int[] playerId) throws IOException {
    // Convert array playerId to ArrayList to shuffle
    //ArrayList<Integer> playerIdList = new ArrayList<Integer>();
    //for (int id : playerId) {
      //playerIdList.add(id);
    //}
    //// Shuffle the list of playerId
    //java.util.Collections.shuffle(playerIdList);
    //int[] playerIdSwapped = new int[4];
    //int index = 0;
    //for (int id : playerIdList) {
      //playerIdSwapped[index] = id;
      //++index;
    //}
    for (int i = 0; i < playerId.length; ++i) {
      state.players.add(new Player(names[playerId[i] - 1], playerId[i]));
    }

    printState();
  }

  // Method to print information each turn
  public void printState() {
    int counter = 1;
    for (Player player : state.players) {
      System.out.println("--------------------------------------------------");
      System.out.println("Player " + counter++);
      System.out.printf("%-10s%40s%n", "Name", player.name());
      System.out.printf("%-10s%40s%n", "Money", player.getMoney());
      System.out.printf("%-10s%40s%n", "Position", player.position());
      System.out.printf("%-10s", "Properties");
      Iterable<Block> owned = player.properties();

      boolean first = true;
      for (Block s : owned) {
        if (first) {
          System.out.printf("%40s%n", s);
        } else {
          System.out.printf("%50s%n", s);
        }
        first = false;
      }

      if (first) {
        System.out.printf("%40s%n", "none");
      }

      if (player.inJail()) {
        System.out.println("In jail");
      } else if (player.position() == state.board.busPos()) {
        System.out.println("In Bus Block");
      }
      System.out.println("--------------------------------------------------");
    }
  }

  public boolean getDouble() {
    return dice.getDouble();
  }

  public class State {
    public Queue<Player> players;
    public Board board; // game board
    public Player current;
  }

  public State getState() {
    return state;
  }

  public boolean getLost() {
    return lost;
  }

  public void setLost() {
    lost = false;
  }

  public Dice getDice() {
    return dice;
  }
}
