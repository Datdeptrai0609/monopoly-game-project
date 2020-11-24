package monopoly;

import org.ini4j.Wini;
import org.ini4j.Profile;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

class Board {
  private final int size; // number of blocks in a board
  private final Block[] board; // representation of board
  private int busPos;
  private int jailPos;
  private Wini ini;

  // constructor for a new board of squares
  public Board() throws IOException {
    // Starting to read config.ini file
    ini = new Wini(new File("..\\config.ini"));
    size = castNum(ini.get("monopoly", "size"));
    busPos = castNum(ini.get("bus", "pos"));
    jailPos = castNum(ini.get("jail", "pos"));

    board = new Block[size];
    makeBoard();
  }

  public int size() {
    return size;
  }

  public Block block(final int pos) {
    return board[pos];
  }

  // return an array of the squares on the board
  public Block[] getBoard() {
    return board;
  }

  private void makeBoard() throws IOException {
    // create a list of section in config.ini
    Collection<Profile.Section> list = ini.values();
    int pos, buy, build, oneH, twoH, threeH, fourH, hotel, rent;
    Block bl;
    // loop through the list to create object of block
    for (Profile.Section section : list) {
      String sectionName = section.getName();
      // monopoly and player are not blocks but place in top of file
      if (sectionName.equals("monopoly") || sectionName.equals("player")) {
        continue;
      } else if (sectionName.equals("go")) {
        pos = castNum(section.get("pos"));
        bl = new GoBlock(pos);
      } else if (sectionName.equals("jail")) {
        pos = castNum(section.get("pos"));
        bl = new JailBlock(pos);
      } else if (sectionName.equals("tax")) {
        pos = castNum(section.get("pos"));
        bl = new TaxBlock(pos);
      } else if (sectionName.equals("festival")) {
        pos = castNum(section.get("pos"));
        bl = new FestivalBlock(pos);
      } else if (sectionName.equals("bus")) {
        pos = castNum(section.get("pos"));
        bl = new BusBlock(pos);
      } else if (sectionName.equals("chance")) {
        for (int i = 0; i < section.size(); i++) {
          pos = castNum(section.get(String.format("pos%d", i + 1)));
          bl = new ChanceBlock(pos);
          board[pos] = bl;
        }
        continue;
      } else if (sectionName.equals("travel")) {
        buy = castNum(section.get("buy"));
        int size = castNum(section.get("size"));
        for (int i = 0; i < size; i++) {
          String name = (String) section.get(String.format("name%d", i + 1));
          pos = castNum(section.get(String.format("pos%d", i + 1)));
          bl = new TravelBlock(name, pos);
          board[pos] = bl;
        }
        continue;
      } else if (sectionName.equals("cards")) {
        // This will go to the section of cards. The block is done.
        break;
      } else {
        pos = castNum(section.get("pos"));
        buy = castNum(section.get("buy"));
        build = castNum(section.get("build"));
        rent = castNum(section.get("rent"));
        oneH = castNum(section.get("1H"));
        twoH = castNum(section.get("2H"));
        threeH = castNum(section.get("3H"));
        hotel = castNum(section.get("HT"));
        bl = new PropertyBlock(sectionName, pos, rent, oneH, twoH, threeH, hotel, buy, build);
      }
      // Assign block to array board
      board[pos] = bl;
    }
  }

  public int busPos() {
    return busPos;
  }

  public int jailPos() {
    return jailPos;
  }

  static int castNum(final Object o) {
    return Integer.parseInt(o.toString());
  }
}
