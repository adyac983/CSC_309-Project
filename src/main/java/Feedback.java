import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Feedback extends JPanel {
    private JTextField answerField;
    private JLabel equationLabel;
    private int currentEquation = 1;

    public Feedback() {
        setLayout(new FlowLayout());

        equationLabel = new JLabel("Equation: " + Equations.getEquation(currentEquation));
        add(equationLabel);

        JLabel answerTextLabel = new JLabel("Your Answer:");
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

    private void checkAnswer() {
        try {
            double userAnswer = Double.parseDouble(answerField.getText());
            double correctAnswer = Equations.getAnswer(currentEquation);
            if (Math.abs(userAnswer - correctAnswer) < 0.0001) {
                JOptionPane.showMessageDialog(this, "Correct!");
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect! The correct answer is: " + correctAnswer);
            }
            currentEquation++;
            if (currentEquation > 8)
                currentEquation = 1;
            equationLabel.setText("Equation: " + Equations.getEquation(currentEquation));
            answerField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
        }
    }
}
