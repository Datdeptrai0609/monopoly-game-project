package com.monopoly.game.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class CreateClient {

  private final String BROKER = "ws://68.183.181.67:8080";

  // Creating a client object
  public MqttClient client = new MqttClient(BROKER, // URI
      MqttClient.generateClientId(), // ClientId
      new MemoryPersistence()); // Persistence
  public MqttConnectOptions options = new MqttConnectOptions();

  public CreateClient() throws MqttException {
  }

  public void CreateConnect() throws MqttException {
    options.setKeepAliveInterval(300);
    options.setConnectionTimeout(3000);

    client.connect(options);
  }
}
