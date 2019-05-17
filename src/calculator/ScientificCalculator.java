package calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScientificCalculator extends BasicCalculator
{
    // set up a frame
    private static JFrame frame = new JFrame("ScientificCalculator");

    private JPanel additionalPanel;
    private JButton switchToBasic;
    private JButton button1;
    private JButton closingParenthesis;
    private JButton button3;
    private JButton button4;
    private JButton button6;
    private JButton button7;
    private JButton button5;
    private JButton openingParenthesis;
    private JButton button9;

    public ScientificCalculator() {
        switchToBasic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // destroy the frame
                frame.setVisible(false);
                frame.dispose();

                // set up a new frame for BasicCalculator
                BasicCalculator.main(null);
            }
        });
        openingParenthesis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.append("(");
            }
        });
        closingParenthesis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.append(")");
            }
        });
    }

    public static void main(String[] args) {
        // create a ScientificCalculator
        ScientificCalculator scientificCalculator = new ScientificCalculator();

        // add the additionalPanel to its centralPanel which inherited from the BasicCalculator
        scientificCalculator.centralPanel.add(scientificCalculator.additionalPanel, BorderLayout.EAST);

        // set up the contentPane of the frame with centralPanel from the scientificCalculator
        frame.setContentPane(scientificCalculator.centralPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(530, 250);
        frame.setResizable(false);
        frame.setVisible(true);

    }


}
