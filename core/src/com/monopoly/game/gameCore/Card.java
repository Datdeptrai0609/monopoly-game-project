package com.monopoly.game.gameCore;

import org.ini4j.Profile;

import java.io.IOException;

public class Card {
  private CardAction action;
  private int value;
  private int travelTo = Integer.MAX_VALUE;
  private String text;

  public Card(int i) throws IOException {
    ReadIni ini = new ReadIni();
    // Create Profile.Section of BANK_MONEY
    Profile.Section section = ini.getSection("BANK_MONEY");
    // get size of section BANK_MONEY b/c have value and content so / 2
    int bankMoneySize = section.size() / 2;
    // If the number of cards bigger than BANK_MONEY size, section will change
    // to MOVE_TO
    if (i <= bankMoneySize) {
      value = ini.getNumValue(section.getName(), String.format("value%d", i));
    } else {
      section = ini.getSection("MOVE_TO");
      // Now i is bigger than BANK_MONEY size so we must minus to it
      i -= bankMoneySize;
      travelTo = ini.fetchNumVal(section.getName(), String.format("travelTo%d", i));
    }
    text = ini.getStrValue(section.getName(), String.format("content%d", i));
    action = CardAction.valueOf(section.getName());
  }

  public int value() {
    return value;
  }

  public int travelTo() {
    return travelTo;
  }

  public String text() {
    return text;
  }

  public CardAction action() {
    return action;
  }

  public enum CardAction {
    BANK_MONEY, MOVE_TO
  }
}
