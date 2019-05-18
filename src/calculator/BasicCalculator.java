package calculator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BasicCalculator
{
    // set up a frame
    private static JFrame frame = new JFrame("BasicCalculator");

    // the centralPanel contains all the components
    protected JPanel centralPanel;

    // the panel containing all the keys for BasicCalculator
    private JPanel keyPad;

    // the area displaying the text entered by the user
    protected JTextArea displayArea;

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
    private JButton switchToScientific;

    // buttons for operators
    private JButton division;
    private JButton multiplication;
    private JButton minus;
    private JButton plus;

    private JButton dot;
    private JButton equal;
    private JButton clear;


    public BasicCalculator() {
        createNumberKeys();

        switchToScientific.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // destroy the frame
                frame.setVisible(false);
                frame.dispose();

                // set up a new frame for ScientificCalculator
                ScientificCalculator.main(null);
            }
        });
        division.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.append("/");
            }
        });
        multiplication.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.append("*");
            }
        });
        minus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.append("-");
            }
        });
        plus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.append("+");
            }
        });

        equal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputExpression = displayArea.getText();

                double result = getExpressionResult(inputExpression);

                displayArea.setText("" + result);
            }
        });
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.setText("");
            }
        });
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

    /**
     * this method helps to attach action listeners to each number key
     */
    private void createNumberKeys()
    {
        dot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.append(".");
            }
        });
        num0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.append("0");
            }
        });
        num1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.append("1");
            }
        });
        num2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.append("2");
            }
        });
        num3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.append("3");
            }
        });
        num4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.append("4");
            }
        });
        num5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.append("5");
            }
        });
        num6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.append("6");
            }
        });
        num7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.append("7");
            }
        });
        num8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.append("8");
            }
        });
        num9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.append("9");
            }
        });
    }

    protected double getExpressionResult(String inputExpression)
    {
        ExpressionEvaluator expression = new ExpressionEvaluator(inputExpression);

        return expression.getExpressionValue();
    }
}
