package mathleap;

import org.eclipse.paho.client.mqttv3.*;
import javax.swing.*;
import java.awt.*;

public class MultiplayerClient extends JFrame implements MqttCallback {
    private BuildingPanel buildingPanel;
    private static JLabel hpLabel;
    private static JLabel scoreLabel;

    private static JLabel opponentScoreLabel;
    String broker = "tcp://test.mosquitto.org:1883";
    String topic = "client";
    String topic_to_sub = "server";
    String clientId = "ASU-client";

    public MultiplayerClient() {

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
        if (message.startsWith("SERVER SCORE:")) {
            int opponentScore = Integer.parseInt(message.substring(13));
            // Update opponent's score display (if necessary)
            System.out.println("server score: " + opponentScore);
            GameData.getInstance().setServerPlayerScore(opponentScore);
        } else if (message.equals("WIN")) {
            message("You Lose! Opponent Reached 10 Points" , JOptionPane.INFORMATION_MESSAGE);
        } else if (message.equals("LOSE")) {
            message("You Win! Opponent hp reached 0" , JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
    public void simulateGame() {

        new Thread(() -> {
            this.setTitle("Math Game Client");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setSize(800, 600);
            GameData.getInstance().setFrame(this);

            buildingPanel = new BuildingPanel(GameData.getInstance().getBuildings(),"city");
            JScrollPane scrollPane = new JScrollPane(buildingPanel);
            scrollPane.setViewportView(buildingPanel);
            GameData.getInstance().setScrollPane(scrollPane);

            Building firstBuilding = GameData.getInstance().getCurrBuilding();
            Player player = new Player(firstBuilding.getX(), this.getHeight()-(int)firstBuilding.getLength()-100);
            String playerchoice = GameData.getInstance().getPlayerchoice();
            if (playerchoice=="boy"){
                player.setImage("boy.png");
            }
            else if (playerchoice=="girl"){
                player.setImage("girl.png");
            }
            else if (playerchoice=="nezuko"){
                player.setImage("nezuko.png");
            }
            else if (playerchoice=="pikachu"){
                player.setImage("pikachu.png");
            }
            else if (playerchoice=="amongus"){
                player.setImage("amongus.png");
            }
            else if (playerchoice=="dinosaur"){
                player.setImage("dinosaur.png");
            }
            else if (playerchoice=="gojo"){
                player.setImage("gojo.png");
            }
            GameData.getInstance().setPlayer(player);

            add(scrollPane, BorderLayout.CENTER);

            JPanel feedbackPanel = new JPanel(new BorderLayout());
            Feedback feedback = new Feedback(GameData.getInstance().getLevelChoice());
            feedbackPanel.add(feedback, BorderLayout.SOUTH);
            add(feedbackPanel, BorderLayout.SOUTH);

            // Create HP and Score panel
            JPanel hpScorePanel = new JPanel(new GridLayout(1, 1));
            hpLabel = new JLabel("HP: ");
            scoreLabel = new JLabel("Score: 0");
            opponentScoreLabel = new JLabel("Opponent Score: 0");

            hpLabel.setForeground(Color.RED);
            opponentScoreLabel.setForeground(Color.RED);
            scoreLabel.setForeground(Color.RED);
            hpScorePanel.add(hpLabel);
            hpScorePanel.add(scoreLabel);
            GameData.getInstance().setScoreLabel(scoreLabel);
            GameData.getInstance().setHpLabel(hpLabel);
            GameData.getInstance().setOpponentScoreLabel(opponentScoreLabel);

            if (GameData.getInstance().getMultiplayer() == 1)
                hpScorePanel.add(opponentScoreLabel);

            add(hpScorePanel, BorderLayout.NORTH);
            setVisible(true);
            GameData.getInstance().changeHpLabelText();
            GameData.getInstance().changeScoreLabelText();
            try {
                MqttClient client = new MqttClient(broker, clientId);
                client.setCallback(new MultiplayerClient());
                client.connect();
                message("Client connected ....", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Client Connected to broker: " + broker);
                client.subscribe(topic_to_sub);
                System.out.println("Client Subscribed to topic: " + topic_to_sub);
                GameData.getInstance().setWhoIAm(GameData.CLIENT);

                while (true) {
                    int score = GameData.getInstance().getClientPlayerScore();
                    String content = "CLIENT SCORE:"+score;
                    MqttMessage message = new MqttMessage(content.getBytes());
                    message.setQos(2);
                    if (client.isConnected())  client.publish(topic, message);

                    if (GameData.getInstance().getServerPlayerScore() >= 10) {
                        message("You Win! You Reached 10 Points" , JOptionPane.INFORMATION_MESSAGE);
                        content = "WIN";
                        message = new MqttMessage(content.getBytes());
                        if (client.isConnected())  client.publish(topic, message);
                    }
                    if (GameData.getInstance().getPlayer().getHp() <= 0) {
                        message("You Lose! You Reached 0 HP" , JOptionPane.INFORMATION_MESSAGE);
                        content = "LOSE";
                        message = new MqttMessage(content.getBytes());
                        if (client.isConnected())  client.publish(topic, message);
                    }
                    Thread.sleep(1000 / 30);
                }
            } catch (MqttException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
