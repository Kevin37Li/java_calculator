package calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScientificCalculator extends BasicCalculator
{
    // set up a frame
    private static JFrame frame = new JFrame("ScientificCalculator");

    // consisting of all the additional functionality for a scientificCalculator
    private JPanel additionalPanel;

    // all the operations a scientificCalculator can do
    private JButton sin;
    private JButton cos;
    private JButton tan;
    private JButton log;
    private JButton squared;
    private JButton cubed;
    private JButton sqrt;
    private JButton graph;
    private JButton closingParenthesis;
    private JButton openingParenthesis;


    public ScientificCalculator() {
        createSwitchButton();

        openingParenthesis.addActionListener(e -> displayArea.append("("));
        closingParenthesis.addActionListener(e -> displayArea.append(")"));
    }

    /**
     * Override the one in BasicCalculator class, creates the switchButton for Scientific to
     * switch to Basic
     */
    protected void createSwitchButton()
    {
        switchButton.addActionListener(e ->
            {
                // destroy the current frame
                frame.setVisible(false);
                frame.dispose();

                // set up a new frame for BasicCalculator
                BasicCalculator.main(null);
            }
        );
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
