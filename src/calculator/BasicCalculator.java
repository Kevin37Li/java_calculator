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
        num0.addActionListener(e -> addDigit("0"));
        num1.addActionListener(e -> addDigit("1"));
        num2.addActionListener(e -> addDigit("2"));
        num3.addActionListener(e -> addDigit("3"));
        num4.addActionListener(e -> addDigit("4"));
        num5.addActionListener(e -> addDigit("5"));
        num6.addActionListener(e -> addDigit("6"));
        num7.addActionListener(e -> addDigit("7"));
        num8.addActionListener(e -> addDigit("8"));
        num9.addActionListener(e -> addDigit("9"));
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

        // Convert positive number to negative and vice versa
        // All negative numbers will be enclosed within the parenthesis (to be distinguished
        // from the minus sign), positive numbers will not
        pos_neg.addActionListener(e ->
            {
                String num = getNum();
                if (!num.equals(""))
                {
                    // if is a positive number
                    if (!num.contains("-"))
                    {
                        resetNum(num, "-" + num);
                    }
                    // if is a negative number
                    else
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
     * This method gets the number user just entered, and for negative numbers, it only gets the
     * number excluding the parenthesis surrounding that number
     * @return the String version of the number
     */
    protected String getNum()
    {
        String expression = getExpression();
        StringBuilder sb = new StringBuilder();

        for (int i = expression.length() - 1; i >= 0; i--)
        {
            char token = expression.charAt(i);
            // Ignore all ")"
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
     * or cos.
     * For negative number converting to positive number, this method will help to get rid of the
     * parenthesis surrounding the negative number if that number is not at the beginning of a
     * expression.
     * Also for positive number to negative, it will add parenthesis around the negative number
     * if that number is not at the beginning of a expression.
     * @param num the operand
     * @param result the result obtained after the operation
     */
    protected void resetNum(String num, String result)
    {
        String content = displayArea.getText();
        String expression = getExpression();
        int indexForContent = content.lastIndexOf(num);
        int indexForExpression = expression.lastIndexOf(num);

        // If involves in changing negative to positive
        if (num.contains("-") && !result.contains("-"))
        {
            // if the negative number is being surrounded by the parenthesis
            if (indexForExpression != 0 && expression.charAt(indexForExpression - 1) == '(')
            {
                displayArea.setText(content.substring(0, indexForContent - 1) + result);
                return;
            }
        }
        // Needs to convert neg to neg
        else if (num.contains("-") && result.contains("-"))
        {
            // if the negative number is being surrounded by the parenthesis
            if (indexForExpression != 0 && expression.charAt(indexForExpression - 1) == '(')
            {
                displayArea.setText(content.substring(0, indexForContent) + result + ")");
                return;
            }
        }
        // Needs to covert pos to neg
        else if (!num.contains("-") && result.contains("-"))
        {
            // If the expression doesn't start with the num
            if (indexForExpression != 0)
            {
                displayArea.setText(content.substring(0, indexForContent) + "(" + result + ")");
                return;
            }
        }

        // if doesn't have to get rid of the parenthesis, do the regular replacement
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

    /**
     * This method adds digits to the displayArea
     * @param digit to be added
     */
    private void addDigit(String digit)
    {
        String content = displayArea.getText();

        if (content.length() != 0 && content.charAt(content.length() - 1) != ')')
        {
            displayArea.append(digit);
        }
        else if (content.length() == 0)
        {
            displayArea.append(digit);
        }
    }

    public static void main(String[] args)
    {
        BasicCalculator calculator = new BasicCalculator();

        // If it's switching from Scientific, not the first time starting the BasicCalculator
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
