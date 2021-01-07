import org.eclipse.paho.client.mqttv3.*;

public class CallBack{

    public CallBack() throws MqttException {
    }

    public void CallBack(MqttClient client) throws MqttException {
        client.setCallback(new MqttCallback() {

            @Override
            public void connectionLost(Throwable cause) { //Called when the client lost the connection to the broker
                System.out.println(cause);
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String stringMsg = message.toString();
                System.out.println(message);
                //
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {//Called when a outgoing publish is complete
            }
        });
    }
}
