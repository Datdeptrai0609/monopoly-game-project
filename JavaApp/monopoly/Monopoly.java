/**
 * This is a monopoly game for OOP Project
 * Author: Vo Anh Viet
*/
package monopoly;

import java.io.IOException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Random;

class Monopoly {
  private final Dice dice;
  private State state;
  private boolean lost = false;

  private Monopoly() throws IOException {
    // Create board and initialize players as well as some neccessary state
    state = new State();
    state.players = new LinkedList<>();
    state.current = null;
    Input input = new Input();
    dice = new Dice();
    state.board = new Board();
    initialize(input);
  }

  public static void main(String[] args) throws IOException {
    // create Monopoly and run it
    Monopoly monopoly = new Monopoly();
    monopoly.run();
  }

  private void run() {
    // while loop until game has only 1 player remain
    while (state.players.size() > 1) {
      try {
        state.current = state.players.remove(); // Current is player who is playing
        turn();
        if (!lost)
          state.players.add(state.current); // End turn. Add current to the end of queue
        lost = false;
      } catch (NoSuchElementException e) {
        System.out.println("ERROR!");
        return;
      } finally {
        printState();
      }
    }
    // Print player who win
    Player winner = state.players.remove();
    System.out.println("Winner is: " + winner.name());
  }

  // Handle each turn of each player
  private void turn() {
    System.out.println("It's " + state.current.name() + "'s turn");
    int double_count = 0;
    Block[] board = state.board.getBoard();
    while (true) {
      // Check player's previous turn is in BusBlock or not
      if (state.current.position() == state.board.busPos()) {
        int busNum = busSelect(state.current);
        state.current.moveTo(busNum, state.board);
        handleBlock(state.current, board[state.current.position()]);
        break;
      }
      // Check player is in JailBlock or not
      else if (state.current.inJail()) {
        System.out.println("Would you like to get out of jail using cash?");
        if (state.current.inputBool()) {
          state.current.excMoney(-50);
          state.current.leaveJail();
        }
      }
      System.out.println("Are you ready to dice?");
      if (state.current.inputBool()) {
        // Start dice
        dice.roll();
        if (dice.getDouble())
          double_count++;

        // Handle dice when in Jail
        if (state.current.inJail()) {
          if (dice.getDouble()) {
            state.current.leaveJail();
            dice.setDouble();
          } else {
            System.out.println("You did not roll a double dice");
            break;
          }
        }

        // If dice 3 double continuity -> go to Jail
        if (double_count == 3) {
          state.current.toJail(state.board);
          break;
        }

        System.out.println("You roll a " + dice.getVal());
        state.current.move(dice.getVal(), state.board);
        handleBlock(state.current, board[state.current.position()]);

        // If not roll double or player in jail -> END TURN
        if (!dice.getDouble() || state.current.inJail() || lost) {
          break;
        } else {
          System.out.println("Your dice is double. So, You can go in the next turn");
        }
      }
    }
  }

  // Handle action at destination
  private void handleBlock(Player player, Block block) {
    System.out.println("You land on " + state.board.getBoard()[player.position()].name());
    boolean owned = block.isOwned();
    boolean ownable = block.isOwnable();

    if (!owned && ownable)
      buyBlock(player, block);
    else if (ownable)
      rentBlock(player, block);
    else if (block instanceof ChanceBlock)
      drawCard(player, (ChanceBlock) block);
    else if (block instanceof TaxBlock)
      payTax(player, (TaxBlock) block);
    else if (block instanceof JailBlock)
      state.current.toJail(state.board);
    else if (block instanceof FestivalBlock)
      organizeFestival(player);
    else
      return;
  }

  private void organizeFestival(Player player) {
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
        bl = propsSelect(player);
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

  private void payTax(Player player, TaxBlock block) {
    int cost = block.tax(player.getAssets());
    System.out.println("You must pay $" + cost);
    if (player.getMoney() < cost) {
      while (true) {
        cost = additionMoney(player, null, cost);
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

  private void buyBlock(Player player, Block block) {
    int cost = block.cost();
    System.out.println("Would you like to purchase " + block.name() + " for " + cost + " (Yes/No)?");
    if (player.inputBool()) {
      if (player.getMoney() >= cost) {
        player.excMoney(cost * -1);
        purchase(player, block);
      } else {
        System.out.println("You don't have enough money!");
      }
    }
  }

  private void rentBlock(Player player, Block block) {
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
        System.out.println("Would you like to build houses here?");
        if (player.inputBool()) {
          // when first build can build 2 houses, then only 1 house
          switch (propsBlock.numHouses()) {
            case 0:
            case 1:
              System.out.println("You can only build up to 2 houses now. How many houses would you like to build?");
              while (true) {
                int houseInput = player.inputInt();
                if (propsBlock.numHouses() + houseInput >= 0 && propsBlock.numHouses() + houseInput <= 2) {
                  if (player.getMoney() < propsBlock.houseCost() * houseInput) {
                    System.out.println("You don't have enough money!");
                    continue;
                  }
                  propsBlock.build(houseInput);
                  player.excMoney(propsBlock.houseCost() * houseInput * -1);
                  break;
                }
              }
              break;
            case 2:
            case 3:
              if (player.getMoney() < propsBlock.houseCost()) {
                System.out.println("You don't have enough money!");
              } else {
                System.out.println("You can only build a house per building time!");
                propsBlock.build(1);
                player.excMoney(propsBlock.houseCost() * -1);
              }
              break;
            default:
              System.out.println("You cannot build houses in this property anymore!");
          }
        }
      }
      return;
    }

    // Land on different player's property
    System.out.println("You have landed on " + block.name() + " and must pay " + cost + " in rent.");
    if (player.getMoney() >= cost) {
      player.excMoney(cost * -1);
      owner.excMoney(cost);
    } else {
      int remainCost = cost;
      while (true) {
        remainCost = additionMoney(player, owner, remainCost);
        // player lose
        if (remainCost == Integer.MIN_VALUE)
          return;
        // player pay all money but have redundant money
        else if (remainCost <= 0) {
          player.excMoney(remainCost * -1);
          owner.excMoney(cost);
          break;
        }
      }
    }

    // Set the festival price to normal price when have any player go in
    if (block.getFestival())
      block.setFestival(false);
  }

  // Handle drawCard in ChanceBlock
  private void drawCard(Player player, ChanceBlock cards) {
    Card card = cards.draw();
    System.out.println(card.text());

    int initialPos = player.position();

    switch (card.action()) {
      case BANK_MONEY:
        if (card.value() >= 0) {
          player.excMoney(card.value());
        } else {
          int cost = -1 * card.value();
          if (player.getMoney() < cost) {
            while (true) {
              cost = additionMoney(player, null, cost);
              if (cost == Integer.MIN_VALUE) {
                return;
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

    // Handle action at destination for MOVE_TO card action
    if (initialPos == player.position())
      return;

    Block bl = state.board.block(player.position());
    handleBlock(player, bl);
  }

  // Handle sell property for more money
  private int additionMoney(Player player, Player owner, int cost) {
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
      Block bl = propsSelect(player);

      cost -= bl.cost();
      if (bl instanceof PropertyBlock) {
        PropertyBlock prop = (PropertyBlock) bl;
        cost -= prop.numHouses() * prop.houseCost() / 2;
      }
      player.sellProp(bl);
      return cost;
    }
  }

  private Queue<Block> availableAssets(Player player) {
    Iterable<Block> props = player.properties();
    Queue<Block> avail = new LinkedList<>();
    for (Block bl : props)
      avail.add(bl);
    return avail;
  }

  // Calculate the price of sell all properties player has
  private int sellVal(Queue<Block> props) {
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
  private void lose(Player player, Player owner) {
    if (owner != null) {
      owner.excMoney(player.getMoney());
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
  private Block propsSelect(Player player) {
    System.out.println("You own the following properties:");
    Iterable<Block> props = player.properties();

    int counter = 1;
    for (Block bl : props)
      System.out.println(counter++ + ") " + bl.name());

    while (true) {
      int propNum = player.inputInt();
      int propState = 1;

      for (Block bl : props) {
        if (propState++ == propNum)
          return bl;
      }

      System.out.println("Please select a valid property.");
    }
  }

  // Select the destination when in BusBlock
  private int busSelect(Player player) {
    int busNum;
    System.out.println("You can choose one block in this list:");
    for (Block bl : state.board.getBoard()) {
      if (bl instanceof BusBlock) {
        continue;
      }
      System.out.println(bl.position() + ") " + bl.name());
    }

    while (true) {
      busNum = player.inputInt();
      if (busNum >= 0 && busNum < state.board.size() && busNum != state.board.busPos()) {
        break;
      } else {
        System.out.println("Please select valid block");
      }
    }
    return busNum;
  }

  // purchase a property
  private void purchase(Player player, Block block) {
    player.addProperty(block);
    block.purchase(player);
  }

  // initialize player and random at the beginning of the game
  private void initialize(Input input) throws IOException {
    for (int i = 0; i < 4; ++i) {
      System.out.println("Player " + (i + 1) + " name?");
      state.players.add(input.inputPlayer(state));
    }
    // Find the person who go first
    int first = new Random().nextInt(2) + 1;

    // Swap the first person to the first queue
    for (int i = 0; i < first; i++)
      state.players.add(state.players.remove());

    printState();
  }

  // Method to print information each turn
  private void printState() {
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
        if (first)
          System.out.printf("%40s%n", s);
        else
          System.out.printf("%50s%n", s);
        first = false;
      }

      if (first)
        System.out.printf("%40s%n", "none");

      if (player.inJail())
        System.out.println("In jail");
      else if (player.position() == state.board.busPos()) {
        System.out.println("In Bus Block");
      }
      System.out.println("--------------------------------------------------");
    }
  }

  public class State {
    public Queue<Player> players;
    public Board board; // game board
    public Player current;
  }
}
