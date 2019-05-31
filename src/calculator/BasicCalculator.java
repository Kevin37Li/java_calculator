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
    private JButton pos_neg;


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
                String expression = getExpression();
                if (expression.length() != 0 && Character.isDigit(expression.charAt(expression.length() - 1)))
                {
                    String num = getNum();

                    // Check to see if the number is already a decimal
                    if (!num.contains("."))
                    {
                        displayArea.append(".");
                    }
                }
                else if (expression.length() == 0 || expression.charAt(expression.length() - 1) != '.' &&
                        expression.charAt(expression.length() - 1) != ')')
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
                String expression = getExpression();
                double result = getExpressionResult(expression);

                if (result == Double.POSITIVE_INFINITY || result == Double.NEGATIVE_INFINITY)
                {
                    JOptionPane.showMessageDialog(null, "Invalid Value!\n" +
                                "Cannot divide by zero");
                    resetNum(expression, "");
                }
                else
                {
                    displayArea.append("\n" + String.format("%.2f", result));
                }
            });

        // clear all the data within displayArea
        clear.addActionListener(e -> displayArea.setText(""));

        // delete one character
        delete.addActionListener(e ->
            {
                String content = displayArea.getText();
                int length = content.length();
                if (length != 0)
                {
                    displayArea.setText(content.substring(0,
                            length - 1));
                }
            });

        pos_neg.addActionListener(e ->
            {
                String num = getNum();
                if (!num.equals(""))
                {
                    if (Double.parseDouble(num) > 0)
                    {
                        if (getExpression().indexOf(getNum()) != 0)
                        {
                            resetNum(num, "(-" + num + ")");
                        }
                        else
                        {
                            resetNum(num, "-" + num);
                        }
                    }
                    else if (Double.parseDouble(num) < 0)
                    {
                        // Get rid of the negative sign
                        resetNum(num, num.substring(1));
                    }
                }
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
     * @return the String version of the number
     */
    protected String getNum()
    {
        String expression = getExpression();
        StringBuilder sb = new StringBuilder();

        for (int i = expression.length() - 1; i >= 0; i--)
        {
            char token = expression.charAt(i);

            if (token == ')')
            {
                continue;
            }
            if (Character.isDigit(token) || token == '.')
            {
                sb.append(expression.charAt(i));
            }
            else if (token == '-')
            {
                // '-' is a negative sign
                if (i == 0 || expression.charAt(i - 1) == '(')
                {
                    sb.append(expression.charAt(i));
                }
                // '-' is a minus sign not a negative sign
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }
        return sb.reverse().toString();
    }

    /**
     * This method helps to replace the operand num with the result after the operation like sin
     * or cos within the displayArea
     * @param num the operand
     * @param result the result obtained after the operation
     */
    protected void resetNum(String num, String result)
    {
        String content = displayArea.getText();
        String expression = getExpression();
        int indexForContent = content.indexOf(num);
        int indexForExpression = expression.indexOf(num);

        if (num.contains("-") && !result.contains("-"))
        {
            if (indexForExpression != 0 && expression.charAt(indexForExpression - 1) == '(')
            {
                displayArea.setText(content.substring(0, indexForContent - 1) + result);
                return;
            }
        }

        displayArea.setText(content.substring(0, indexForContent) + result);
    }

    /**
     * This method adds symbols like "+" to the displayArea
     * @param symbol to be appended to displayArea
     */
    protected void addSymbol(String symbol)
    {
        String content = displayArea.getText();
        if (content.length() != 0 && (Character.isDigit(content.charAt(content.length() - 1)) ||
                content.charAt(content.length() - 1) == ')'))
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
        frame.setResizable(false);
        frame.setVisible(true);
    }

}
