package com.monopoly.gameCore;

import org.ini4j.Profile;

import java.io.IOException;
import java.util.Collection;

public class Board {
  private final int size; // number of blocks in a board
  private final Block[] board; // representation of board
  private int busPos;
  private int jailPos;
  private ReadIni ini;

  // constructor for a new board of squares
  public Board() throws IOException {
    // Starting to read config.ini file
    ini = new ReadIni();
    size = ini.getNumValue("monopoly", "size");
    busPos = ini.getNumValue("bus", "pos");
    jailPos = ini.getNumValue("jail", "pos");

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
    Collection<Profile.Section> listSections = ini.listOfSection();
    int pos, buy, build, oneH, twoH, threeH, hotel, rent;
    Block bl;
    // loop through the list to create object of block
    for (Profile.Section section : listSections) {
      String sectionName = section.getName();
      // monopoly and player are not blocks but place in top of file
      if (sectionName.equals("monopoly") || sectionName.equals("player")) {
        continue;
      } else if (sectionName.equals("go")) {
        pos = ini.getNumValue(sectionName, "pos");
        bl = new GoBlock(pos);
      } else if (sectionName.equals("jail")) {
        pos = ini.getNumValue(sectionName, "pos");
        bl = new JailBlock(pos);
      } else if (sectionName.equals("tax")) {
        pos = ini.getNumValue(sectionName, "pos");
        bl = new TaxBlock(pos);
      } else if (sectionName.equals("festival")) {
        pos = ini.getNumValue(sectionName, "pos");
        bl = new FestivalBlock(pos);
      } else if (sectionName.equals("bus")) {
        pos = ini.getNumValue(sectionName, "pos");
        bl = new BusBlock(pos);
      } else if (sectionName.equals("chance")) {
        for (int i = 0; i < section.size(); i++) {
          pos = ini.getNumValue(sectionName, String.format("pos%d", i + 1));
          bl = new ChanceBlock(pos);
          board[pos] = bl;
        }
        continue;
      } else if (sectionName.equals("travel")) {
        buy = ini.getNumValue(sectionName, "buy");
        int size = ini.getNumValue(sectionName, "size");
        for (int i = 0; i < size; i++) {
          String name = ini.getStrValue(sectionName, String.format("name%d", i + 1));
          pos = ini.getNumValue(sectionName, String.format("pos%d", i + 1));
          bl = new TravelBlock(name, pos);
          board[pos] = bl;
        }
        continue;
      } else if (sectionName.equals("cards")) {
        // This will go to the section of cards. The block is done.
        break;
      } else {
        pos = ini.getNumValue(sectionName, "pos");
        buy = ini.getNumValue(sectionName, "buy");
        build = ini.getNumValue(sectionName, "build");
        rent = ini.getNumValue(sectionName, "rent");
        oneH = ini.getNumValue(sectionName, "1H");
        twoH = ini.getNumValue(sectionName, "2H");
        threeH = ini.getNumValue(sectionName, "3H");
        hotel = ini.getNumValue(sectionName, "HT");
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
}
