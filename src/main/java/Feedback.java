import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Feedback extends JPanel {
    private JTextField answerField;
    private JLabel equationLabel;
    private int currentEquation = 1;
    private int hintStep = 1;
    private int levelChoice = 2;

    public Feedback() {
        setLayout(new FlowLayout());

        equationLabel = new JLabel("Equation: " + Equations.getEquation(currentEquation, levelChoice));
        add(equationLabel);
        JLabel answerTextLabel = new JLabel("Enter your answer:");
        if(levelChoice == 3){
            answerTextLabel = new JLabel("Enter the bigger root:");
        }
        answerField = new JTextField(10);

        add(answerTextLabel);
        add(answerField);
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
            }
        });
        add(submitButton);

        JButton hintButton = new JButton("Hint");
        hintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHint();
            }
        });
        add(hintButton);

        JButton solButton = new JButton("Solution");
        solButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSolutions();
            }
        });
        add(solButton);
    }
    private void showSolutions(){
        String s = Solution.getSolution(Equations.getEquation(currentEquation, levelChoice));
        JOptionPane.showMessageDialog(this, s);
    }
    private void checkAnswer() {
        try {
            double userAnswer = Double.parseDouble(answerField.getText());
            double correctAnswer = Equations.getAnswer(currentEquation, levelChoice);
            if (Math.abs(userAnswer - correctAnswer) < 0.0001) {
                JOptionPane.showMessageDialog(this, "Correct!");
                GameData.getInstance().setResult(0);
                //move player to next building if player isn't at the bottom already
                if (GameData.getInstance().getPlayer().getY() != GameData.getInstance().getScrollPaneHeight()-100) {
                    GameData.getInstance().nextBuilding();
                }
                GameData.getInstance().recalculate();
                GameData.getInstance().getSp().getViewport().repaint();
                Player player = GameData.getInstance().getPlayer();
                System.out.println("player x after repaint:" + player.getX());
                System.out.println("player y after repaint :" + player.getY());
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect! The correct answer is: " + correctAnswer);
                GameData.getInstance().setResult(1);
                GameData.getInstance().getPlayer().setHp(GameData.getInstance().getPlayer().getHp()-1);
                GameData.getInstance().recalculate();
                GameData.getInstance().getSp().getViewport().repaint();
            }
            currentEquation++;
            hintStep = 1;
//            JLabel clickedLabel = (JLabel) e.getSource();
//            clickedLabel.setText("" + Equations.getAnswer(currentEquation));
            if (currentEquation > 8)
                currentEquation = 1;
            equationLabel.setText("Equation: " + Equations.getEquation(currentEquation, levelChoice));
            answerField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
        }
    }
    private void showHint() {
        String hint = Hints.getHint(Equations.getEquation(currentEquation, levelChoice), hintStep);
        JOptionPane.showMessageDialog(this, hint);
        hintStep++;
    }
}
