import org.eclipse.paho.client.mqttv3.MqttException;

public class Subscribe extends CreateClient {

    public Subscribe() throws MqttException {
    }

    CreateClient onConnect = new CreateClient();

    CallBack callBack = new CallBack();

    public void Subscribe(String topic) throws MqttException {
        callBack.CallBack(onConnect.client);
        onConnect.CreateConnect();
        onConnect.client.subscribe(topic);
    }
}
