import org.eclipse.paho.client.mqttv3.MqttException;

public class Test {
    public static void main(String[] args) throws MqttException {
        Subscribe sub = new Subscribe();
        sub.Subscribe("test");
//        Publish pub = new Publish();
//        pub.Publish("test", "Kuerl!");
    }
}
