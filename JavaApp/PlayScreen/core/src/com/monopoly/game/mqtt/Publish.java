package com.monopoly.game.mqtt;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Publish extends CreateClient {
  CreateClient onConnect;
  public Publish() throws MqttException {
    onConnect = new CreateClient();
  }

  public void pub(String topic, String content) throws MqttException {
    MqttMessage message = new MqttMessage(content.getBytes());
    onConnect.CreateConnect();
    onConnect.client.publish(topic, message);
  }
}
