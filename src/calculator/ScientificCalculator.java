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
    private JButton ln;
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


        sin.addActionListener(e ->
            {
                // Get the number to be calculated
                double num = getNum();
                double result = Math.sin(Math.toRadians(num));

                // replace the num with the corresponding result with the displayArea
                resetNum(num, String.format("%.4f", result));
            });
        cos.addActionListener(e ->
            {
                // Get the number to be calculated
                double num = getNum();
                double result = Math.cos(Math.toRadians(num));

                // replace the num with the corresponding result with the displayArea
                resetNum(num, String.format("%.4f", result));
            });
        tan.addActionListener(e ->
            {
                // Get the number to be calculated
                double num = getNum();

                // if 90 degree or 270 degree and so on
                if ((num / 90) % 2 == 1)
                {
                    JOptionPane.showMessageDialog(null, "invalid value for tangent (result is " +
                            "undefined)");
                    resetNum(num, "");
                }
                // all the other values are valid for tangent
                else
                {
                    double result = Math.tan(Math.toRadians(num));

                    // replace the num with the corresponding result with the displayArea
                    resetNum(num, String.format("%.4f", result));
                }
            }
        );
        ln.addActionListener(e ->
            {
                double num = getNum();
                double result = Math.log(num);
                resetNum(num, String.format("%.4f", result));
            });
        squared.addActionListener(e ->
            {
                double num = getNum();
                double result = Math.pow(num, 2);
                resetNum(num, "" + result);
            });
        cubed.addActionListener(e ->
            {
                double num = getNum();
                double result = Math.pow(num, 3);
                resetNum(num, "" + result);
            });
        sqrt.addActionListener(e ->
            {
                double num = getNum();
                double result = Math.sqrt(num);
                resetNum(num, "" + result);
            });
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

    /**
     * This method helps to replace the operand num with the result after the operation like sin
     * or cos within the displayArea
     * @param num the operand
     * @param result the result obtained after the operation
     */
    private void resetNum(double num, String result)
    {
        // Get all the content within the displayArea
        String displayContent = displayArea.getText();
        // the starting index of the num which will be replaced by result
        int cut;

        if (num == (int) num)
        {
            cut = displayContent.lastIndexOf("" + (int)num);
        }
        else
        {
            cut = displayContent.lastIndexOf("" + num);
        }

        displayArea.setText(displayContent.substring(0, cut) + result);
    }

    public static void main(String[] args)
    {
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
