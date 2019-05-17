package practice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FilledFrame extends JFrame
{
    private static final int FRAME_WIDTH = 450;
    private static final int FRAME_HEIGHT = 100;

    private static final double DEFAULT_RATE = 5;
    private static final double INITIAL_BALANCE = 1000;

    private JButton button;
    private JLabel rateLabel;
    private JTextField rateField;
    private JTextArea resultArea;
    private JLabel resultLabel;
    private double balance;
    private JPanel panel;

    public FilledFrame()
    {
        this.balance = INITIAL_BALANCE;
//        this.resultLabel = new JLabel("Balance: " + this.balance);

        resultArea = new JTextArea(10, 30);
        resultArea.setText(balance + "\n");
        resultArea.setEditable(false);

        this.createTextField();
        this.createButton();
        this.createPanel();

        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    }

    private void createTextField()
    {
        rateLabel = new JLabel("Rate: ");

        final int FIELD_WIDTH = 20;
        rateField = new JTextField(FIELD_WIDTH);

        rateField.setText("" + DEFAULT_RATE);
    }

    private void createButton()
    {
        this.button = new JButton("Add Interest");

        this.button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                balance += balance * (Double.parseDouble(rateField.getText()) / 100);
//                resultLabel.setText("Balance: " + balance);
                resultArea.append(balance + "\n");
            }
        });
    }

    private void createPanel()
    {
//        this.panel = new JPanel();
//        this.panel.setLayout(new BorderLayout());
//        this.panel.add(rateLabel);
//        this.panel.add(rateField);
//        this.panel.add(button);
//        this.panel.add(resultLabel);
//        this.panel.add(new JScrollPane(resultArea));
//        this.add(panel);

        this.add(rateLabel, BorderLayout.NORTH);
        this.add(rateField, BorderLayout.SOUTH);
        this.add(button);
//        this.add(resultLabel);
    }


}
