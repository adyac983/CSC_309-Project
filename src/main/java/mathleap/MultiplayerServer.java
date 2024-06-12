package mathleap;

import org.eclipse.paho.client.mqttv3.*;
import javax.swing.*;
import java.awt.*;

public class MultiplayerServer extends JFrame implements MqttCallback {
    private MqttClient client;
    private BuildingPanel buildingPanel;

    public MultiplayerServer() {
        setLayout(new GridLayout(1, 1));
        buildingPanel = new BuildingPanel(GameData.getInstance().getBuildings(),"city");
        add(buildingPanel);
    }

    private static void message (String text, int icon) {
        JOptionPane.showMessageDialog(null, text, "Client", icon);
    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) {
        String message = new String(mqttMessage.getPayload());
        if (message.startsWith("SCORE:")) {
            int opponentScore = Integer.parseInt(message.substring(6));
            // Update opponent's score display (if necessary)
            System.out.println("opponent score: " + opponentScore);
            GameData.getInstance().setClientPlayerScore(opponentScore);
        } else if (message.equals("WIN")) {
            JOptionPane.showMessageDialog(this, "You Lose! Opponent Reached 10 Points");
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
    private void updateScore() {
        if (GameData.getInstance().getServerPlayerScore() >= 10) {
            JOptionPane.showMessageDialog(this, "You Win! Reached 10 Points");
        }
    }
    public void simulateGame() {
        String broker = "tcp://test.mosquitto.org:1883";
        String topic = "server";
        String topic_to_sub = "client";
        String clientId = "ASU-subscriber";

        new Thread(() -> {
            JScrollPane scrollPane = new JScrollPane(buildingPanel);
            scrollPane.setViewportView(buildingPanel);
            GameData.getInstance().setScrollPane(scrollPane);
            message("Server starting ....", JOptionPane.INFORMATION_MESSAGE);
            this.setTitle("Math Game Server");
            this.setSize(800, 600);
            this.setResizable(false);
            this.setLocation(0, 0);
            this.setVisible(true);

            try {
                MqttClient client = new MqttClient(broker, clientId);
                client.setCallback(new MultiplayerServer());
                client.connect();
                message("Server connected ....", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Connected to broker: " + broker);
                client.subscribe(topic_to_sub);
                System.out.println("Subscribed to topic: " + topic_to_sub);
                GameData.getInstance().setWhoIAm(GameData.SERVER);

                while (true) {
                    int score = GameData.getInstance().getServerPlayerScore();
                    String content = "SCORE:"+score;
                    MqttMessage message = new MqttMessage(content.getBytes());
                    message.setQos(2);
                    if (client.isConnected())  client.publish(topic, message);

                    updateScore();
                    Thread.sleep(1000 / 30);
                }
            } catch (MqttException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
