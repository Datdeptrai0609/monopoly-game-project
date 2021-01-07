import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Publish extends CreateClient {

    public Publish() throws MqttException {
    }

    private String content = "{\"id\":\"1\",\"version\":\"1.0\",\"params\":{\"LightSwitch\":1}}";;

    CreateClient onConnect = new CreateClient();

    public void Publish(String topic, String content) throws MqttException {
        MqttMessage message =  new MqttMessage(content.getBytes());
        onConnect.CreateConnect();
        onConnect.client.publish(topic, message);

    }
}
