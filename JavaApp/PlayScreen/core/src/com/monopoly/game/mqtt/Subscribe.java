package com.monopoly.game.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Subscribe extends CreateClient {
  private String stringMsg;
  private boolean statusMsg;

  public Subscribe() throws MqttException {
    statusMsg = false;
  }

  CreateClient onConnect = new CreateClient();
public void start(String topic) throws MqttException {
    //callBack.CallBack(onConnect.client);
    onConnect.client.setCallback(new MqttCallback(){

      @Override
      public void connectionLost(Throwable cause) { // Called when the client lost the connection to the broker
        System.out.println(cause);
      }

      @Override
      public void messageArrived(String topic, MqttMessage message) throws Exception {
        stringMsg = message.toString();
        statusMsg = true;
        System.out.println(stringMsg);
        //
      }

      @Override
      public void deliveryComplete(IMqttDeliveryToken token) {// Called when a outgoing publish is complete
      }
    });
    onConnect.CreateConnect();
    onConnect.client.subscribe(topic);
  }

  public String getMqttMsg() {
    statusMsg = false;
    return stringMsg;
  }

  public boolean receivedMsg() {
    return statusMsg;
  }

  public void disconnect() {
    try {
      onConnect.client.disconnect();
    } catch (MqttException e) {}
  }
}
