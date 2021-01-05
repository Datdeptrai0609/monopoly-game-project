package com.monopoly.game.gameCore;

import com.monopoly.game.mqtt.*;

import org.eclipse.paho.client.mqttv3.MqttException;

public class Input {
  //private final Scanner scanner;
  private Subscribe sub;

  public Input(String topic) {
    //scanner = new Scanner(System.in);
    try {
      sub = new Subscribe();
      sub.start(topic);
    } catch (MqttException e) {}
  }

  //public void startSub(String topic) {
    //try {
      //sub = new Subscribe();
      //sub.start(topic);
    //} catch (MqttException e) {}
  //}

  // Read string input
  public String inputString() {
    while (!sub.receivedMsg()) {
      try {
        Thread.sleep(0);
      } catch (Exception e) {}
    }
    return sub.getMqttMsg();
  }

  // Check input is yes or no return boolean
  public boolean inputBool() {
    return inputDecision(new int[] {1, 0}) == 0;
  } // Check input is integer or not then parse to int
  public int inputInt() {
    while (true) {
      int val;
      try {
        val = Integer.parseInt(inputString());
      } catch (NumberFormatException e) {
        System.out.println("Please enter a valid integer.");
        continue;
      }
      return val;
    }
  }

  // Check yes/no for inputBool()
  public int inputDecision(final int[] choices) {
    while (true) {
      int input = inputInt();
      for (int i = 0; i < choices.length; i++) {
        if (input == choices[i]) {
          return i;
        }
      }
      System.out.println("Please enter a valid decision.");
    }
  }

  public Subscribe getSubscribe() {
    return sub;
  }
}
