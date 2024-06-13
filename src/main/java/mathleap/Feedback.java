package mathleap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Feedback extends JPanel {
    private JTextField answerField;
    private JLabel equationLabel;
    private int currentEquation = 1;
    private int hintStep = 1;
    private int levelChoice;
    private Boolean correct = false;

    public Feedback(int level) {
        setLayout(new FlowLayout());
        this.levelChoice=level;
        Equations.setLevelChoice(level);
        //System.out.println(levelChoice);
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
    }
    private void showSolutions(){
        String s = Solution.getSolution(Equations.getEquation(currentEquation, levelChoice));
        JOptionPane.showMessageDialog(this, s);
    }
    private void checkAnswer() {

        try {
            if(currentEquation%10 == 0){
                String userAns = answerField.getText();
                String corrAns = Equations.getAnswer(currentEquation, levelChoice);
                if (userAns.equals(corrAns)){
                    correct = true;
                }
                else {
                    correct = false;
                }
            }
            else{
                double userAnswer = Double.parseDouble(answerField.getText());
                double correctAnswer = Double.valueOf(Equations.getAnswer(currentEquation, levelChoice));
                if(Math.abs(userAnswer - correctAnswer) < 0.0001){
                    correct = true;
                }
                else {
                    correct = false;
                }
            }
            if (correct) {
                //correct answer
                JOptionPane.showMessageDialog(this, "Correct!");
                GameData.getInstance().setResult(0);
                //add score
                GameData.getInstance().setScore(GameData.getInstance().getScore()+1);
                GameData.getInstance().changeScoreLabelText();
                //move player to next building if player isn't at the bottom already
                if (GameData.getInstance().getPlayer().getY() != GameData.getInstance().getScrollPaneHeight()-100) {
                    GameData.getInstance().nextBuilding();
                }
                Player player = GameData.getInstance().getPlayer();
                GameData.getInstance().getPlayer().moveTo(GameData.getInstance().getCurrBuilding().getX(),player.getY());
                GameData.getInstance().recalculate();
                GameData.getInstance().getSp().getViewport().repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect! The correct answer is: " + Double.valueOf(Equations.getAnswer(currentEquation, levelChoice)));
                GameData.getInstance().setResult(1);
                GameData.getInstance().getPlayer().setHp(GameData.getInstance().getPlayer().getHp()-1);
                GameData.getInstance().recalculate();
                GameData.getInstance().getSp().getViewport().repaint();
            }
            JButton hintButton = new JButton("Hint");
            hintButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showHint();
                }
            });
            //hints feature is only available after you're score is greater than 7
            if ( GameData.getInstance().getScore() > 7){
                add(hintButton);
            }
            JButton solButton = new JButton("Solution");
            solButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showSolutions();
                }
            });
            //solution button is only accessible if score is more than 5 and health is less than 2
            if (GameData.getInstance().getPlayer().getHp() < 2){
                add(solButton);
            }
            currentEquation++;
            hintStep = 1;
            //if (currentEquation > 8)
            //   currentEquation = 1;
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

    public void setLevelChoice(int i) {
        this.levelChoice = i;
    }
}
