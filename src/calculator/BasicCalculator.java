package calculator;

import javax.swing.*;
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

        createOperationButtons();
    }

    /**
     * This method helps to attach action listeners to each number key
     */
    private void createNumberKeys()
    {
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
        dot.addActionListener(e ->
            {
                String content = displayArea.getText();
                if (content.length() != 0 && Character.isDigit(content.charAt(content.length() - 1)))
                {
                    double num = getNum();

                    // Check if the number entered is an int
                    if (num == (int) num)
                    {
                        displayArea.append(".");
                    }
                }
                else
                {
                    displayArea.append("0.");
                }
            });
    }

    /**
     * This method creates the switchButton for BasicCalculator to switch to ScientificCalculator
     */
    protected void createSwitchButton()
    {
        switchButton.addActionListener(e ->
            {
                // destroy the current frame
                frame.setVisible(false);
                frame.dispose();

                // set up a new frame for ScientificCalculator
                ScientificCalculator.main(new String[] {displayArea.getText()});
            });
    }

    /**
     * This method creates buttons for different kinds of basic operations within a calculator
     */
    private void createOperationButtons()
    {
        division.addActionListener(e -> addSymbol("/"));
        multiplication.addActionListener(e -> addSymbol("*"));
        minus.addActionListener(e -> addSymbol("-"));
        plus.addActionListener(e -> addSymbol("+"));

        equal.addActionListener(e ->
            {
                double result = getExpressionResult(getExpression());

                displayArea.append("\n" + String.format("%.2f", result));
            });

        // clear all the data within displayArea
        clear.addActionListener(e ->
            {
                displayArea.setText("");
            });

        // delete one character
        delete.addActionListener(e ->
            {
                String content = displayArea.getText();
                displayArea.setText(content.substring(0,
                        content.length() - 1));
            });
    }

    /**
     * This method computes an arithmetic expression and returns the final result
     * @param inputExpression the arithmetic expression about to be calculated
     * @return the final result of that expression
     */
    private double getExpressionResult(String inputExpression)
    {
        ExpressionEvaluator expression = new ExpressionEvaluator(inputExpression);

        return expression.getExpressionValue();
    }

    /**
     * This method reads through the displayArea and gets the last line of text as the expression
     * that is about to be evaluated
     * @return the last line within the displayArea TextArea
     */
    protected String getExpression()
    {
        String expression = "";
        Scanner read = new Scanner(displayArea.getText());

        while (read.hasNextLine())
        {
            expression = read.nextLine();
        }

        return expression;
    }

    /**
     * This method gets the number user just entered for operator like sin or cos to act upon on
     * @return the number as an operand
     */
    protected double getNum()
    {
        String expression = getExpression();
        StringBuilder sb = new StringBuilder();

        for (int i = expression.length() - 1; i >= 0; i--)
        {
            if (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')
            {
                sb.append(expression.charAt(i));
            }
            else
            {
                break;
            }
        }

        // The String version of the number
        String strNum = sb.reverse().toString();

        return Double.parseDouble(strNum);
    }

    /**
     * This method adds symbols like "+" to the displayArea
     * @param symbol to be appended to displayArea
     */
    protected void addSymbol(String symbol)
    {
        String content = displayArea.getText();
        if (content.length() != 0 && Character.isDigit(content.charAt(content.length() - 1)))
        {
            displayArea.append(symbol);
        }
    }

    public static void main(String[] args)
    {
        BasicCalculator calculator = new BasicCalculator();

        // there is still content left in displayArea when switching from scientific to basic
        if (args.length != 0)
        {
            calculator.displayArea.setText(args[0]);
        }

        frame.setContentPane(calculator.centralPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(330, 250);
        frame.setResizable(false);
        frame.setVisible(true);
    }

}
