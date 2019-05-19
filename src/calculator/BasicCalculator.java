package calculator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class BasicCalculator
{
    // set up a frame
    private static JFrame frame = new JFrame("BasicCalculator");

    // the centralPanel contains all the components
    protected JPanel centralPanel;

    // the panel containing all the keys for BasicCalculator
    protected JPanel keyPad;

    // the area displaying the text entered by the user
    protected JTextArea displayArea;

    // add scrollbars to displayArea
    private JScrollPane scrollPane;

    // all the number keys from 0 to 9
    private JButton num0;
    private JButton num1;
    private JButton num2;
    private JButton num3;
    private JButton num4;
    private JButton num5;
    private JButton num6;
    private JButton num7;
    private JButton num8;
    private JButton num9;

    // the button helps switch to ScientificCalculator
    protected JButton switchButton;

    // buttons for operators
    private JButton division;
    private JButton multiplication;
    private JButton minus;
    private JButton plus;
    private JButton equal;
    private JButton dot;
    private JButton clear;
    private JButton delete;


    public BasicCalculator() {
        createNumberKeys();

        createSwitchButton();

        createOperatorButtons();
    }

    /**
     * this method helps to attach action listeners to each number key
     */
    private void createNumberKeys()
    {
        dot.addActionListener(e -> displayArea.append("."));
        num0.addActionListener(e -> displayArea.append("0"));
        num1.addActionListener(e -> displayArea.append("1"));
        num2.addActionListener(e -> displayArea.append("2"));
        num3.addActionListener(e -> displayArea.append("3"));
        num4.addActionListener(e -> displayArea.append("4"));
        num5.addActionListener(e -> displayArea.append("5"));
        num6.addActionListener(e -> displayArea.append("6"));
        num7.addActionListener(e -> displayArea.append("7"));
        num8.addActionListener(e -> displayArea.append("8"));
        num9.addActionListener(e -> displayArea.append("9"));
    }

    protected void createSwitchButton()
    {
        switchButton.addActionListener(e ->
            {
                // destroy the frame
                frame.setVisible(false);
                frame.dispose();

                // set up a new frame for ScientificCalculator
                ScientificCalculator.main(null);
            }
        );
    }

    private void createOperatorButtons()
    {
        division.addActionListener(e ->
            {
                displayArea.append("/");
            }
        );
        multiplication.addActionListener(e ->
            {
                displayArea.append("*");
            }
        );
        minus.addActionListener(e ->
            {
                displayArea.append("-");
            }
        );
        plus.addActionListener(e ->
            {
                displayArea.append("+");
            }
        );

        equal.addActionListener(e ->
            {
                String lastLine = "";
                Scanner read = new Scanner(displayArea.getText());

                while (read.hasNextLine())
                {
                    lastLine = read.nextLine();
                }

                double result = getExpressionResult(lastLine);

                displayArea.append("\n" + result);
            }
        );
        clear.addActionListener(e ->
            {
                displayArea.setText("");
            }
        );
        delete.addActionListener(e ->
            {
                displayArea.setText(displayArea.getText().substring(0,
                        displayArea.getText().length() - 1));
            }
        );
    }

    private double getExpressionResult(String inputExpression)
    {
        ExpressionEvaluator expression = new ExpressionEvaluator(inputExpression);

        return expression.getExpressionValue();
    }


    public static void main(String[] args)
    {
        frame.setContentPane(new BasicCalculator().centralPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(330, 250);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
