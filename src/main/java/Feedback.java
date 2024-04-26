import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Feedback extends JDialog {
    private JTextArea feedbackTextArea;

    public Feedback(JFrame parent) {
        super(parent, "Feedback", true);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Please provide your feedback:");
        add(label, BorderLayout.NORTH);

        feedbackTextArea = new JTextArea(10, 30);
        feedbackTextArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(feedbackTextArea);
        add(scrollPane, BorderLayout.CENTER);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitFeedback();
            }
        });
        add(submitButton, BorderLayout.SOUTH);

        setSize(400, 300);
        setLocationRelativeTo(parent);
    }

    private void submitFeedback() {
        String feedback = feedbackTextArea.getText();
        //for now just print it
        System.out.println("Feedback submitted: " + feedback);
        JOptionPane.showMessageDialog(this, "Thank you for your feedback!");
        dispose();
    }

    public static void showDialog(JFrame parent) {
        Feedback feedbackDialog = new Feedback(parent);
        feedbackDialog.setVisible(true);
    }
}
