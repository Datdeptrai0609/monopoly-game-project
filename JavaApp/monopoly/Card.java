package monopoly;

import org.ini4j.*;

import java.io.File;
import java.io.IOException;

public class Card {
  private CardAction action;
  private int value;
  private int travelTo = Integer.MAX_VALUE;
  private String text;

  public Card(int i) throws IOException {
    Wini ini = new Wini(new File("..\\config.ini"));
    // Create Profile.Section of BANK_MONEY
    Profile.Section section = (Profile.Section) ini.get("BANK_MONEY");
    // get size of section BANK_MONEY b/c have value and content so / 2
    int bankMoneySize = section.size()/2;
    // If the number of cards bigger than BANK_MONEY size, section will change
    // to MOVE_TO
    if (i <= bankMoneySize) {
      value = Integer.parseInt(section.get(String.format("value%d", i)).toString());
    } else {
      section = (Profile.Section) ini.get("MOVE_TO"); 
      // Now i is bigger than BANK_MONEY size so we must minus to it
      i -= bankMoneySize;
      travelTo = Integer.parseInt(section.fetch(String.format("travelTo%d", i)).toString());
    }
    text = section.get(String.format("content%d", i)).toString();
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
