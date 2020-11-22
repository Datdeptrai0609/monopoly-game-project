package monopoly;

public class Card {
  private CardAction action;
  private int value;
  private int travelTo = Integer.MAX_VALUE;
  private String text;

  public Card(int a) {
    chance(a);
  }

  private void chance(int a) {
    switch (a) {
      case 0:
        income();
        break;
      case 1:
        inherit();
        break;
      case 2:
        xmas();
        break;
      case 3:
        go();
        break;
      case 4:
        bank();
        break;
      case 5:
        hospital();
        break;
      case 6:
        services();
        break;
      case 7:
        jail();
        break;
      case 8:
        school();
        break;
      case 9:
        doctor();
        break;
      case 10:
        stock();
        break;
      case 11:
        life();
        break;
      case 12:
        beauty();
        break;
      case 13:
        poor();
        break;
      case 14:
        dividend();
      case 15:
        festival();
      default:
        break;
    }
  }

  private void beauty() {
    action = CardAction.BANK_MONEY;
    text = "You have won second prize in a beauty contest! Collect $10";
    value = 10;
  }

  private void life() {
    action = CardAction.BANK_MONEY;
    text = "Life insurance matures. Collect $100";
    value = 100;
  }

  private void stock() {
    action = CardAction.BANK_MONEY;
    text = "From sale of stock. You get $45";
    value = 45;
  }

  private void doctor() {
    action = CardAction.BANK_MONEY;
    text = "Doctors Fee. Pay $50";
    value = -50;
  }

  private void school() {
    action = CardAction.BANK_MONEY;
    text = "Pay School tax of $150";
    value = -150;
  }

  private void jail() {
    action = CardAction.MOVE_TO;
    text = "Go to Jail";
    travelTo = 8;
  }

  private void services() {
    action = CardAction.BANK_MONEY;
    text = "Receive for Services $25";
    value = 25;
  }

  private void hospital() {
    action = CardAction.BANK_MONEY;
    text = "Pay hospital $100";
    value = -100;
  }

  private void bank() {
    action = CardAction.BANK_MONEY;
    text = "Bank Error in your favor. Collect $200";
    value = 200;
  }

  private void go() {
    action = CardAction.MOVE_TO;
    text = "Advance to Go. Collect $200";
    travelTo = 0;
  }

  private void xmas() {
    action = CardAction.BANK_MONEY;
    text = "Xmas fund matures. Collect $100";
    value = 100;
  }

  private void inherit() {
    action = CardAction.BANK_MONEY;
    text = "You inherit $100!";
    value = 100;
  }

  private void income() {
    action = CardAction.BANK_MONEY;
    text = "Income Tax Refund. Collect $20";
    value = 20;
  }

  private void poor() {
    action = CardAction.BANK_MONEY;
    text = "Pay poor Tax of $15";
    value = -15;
  }

  private void dividend() {
    action = CardAction.BANK_MONEY;
    text = "Bank pays you dividend of $50";
    value = 50;
  }

  private void festival() {
    action = CardAction.MOVE_TO;
    text = "Go to festival block";
    travelTo = 16;
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
