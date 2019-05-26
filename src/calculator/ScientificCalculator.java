package calculator;

import javax.swing.*;
import java.awt.*;

public class ScientificCalculator extends BasicCalculator
{
    // set up a frame
    private static JFrame frame = new JFrame("ScientificCalculator");

    // consisting of all the additional functionality for a scientificCalculator
    private JPanel additionalKeys;

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

        createDrawingPanel();


        closingParenthesis.addActionListener(e -> addSymbol(")"));

        openingParenthesis.addActionListener(e ->
            {
                String content = displayArea.getText();
                if (content.length() != 0)
                {
                    char lastChar = content.charAt(content.length() - 1);
                    if ((lastChar != '(' && lastChar != ')' && !Character.isDigit(lastChar)))
                    {
                        displayArea.append("(");
                    }
                }
                else
                {
                    displayArea.append("(");
                }
            });


        sin.addActionListener(e ->
            {
                double num = getNum();
                double result = Math.sin(Math.toRadians(num));

                // replace the num with the corresponding result with the displayArea
                resetNum(num, String.format("%.5f", result));
            });
        cos.addActionListener(e ->
            {
                double num = getNum();
                double result = Math.cos(Math.toRadians(num));

                resetNum(num, String.format("%.5f", result));
            });
        tan.addActionListener(e ->
            {
                double num = getNum();

                // if 90 degree or 270 degree and so on
                if ((num / 90) % 2 == 1)
                {
                    JOptionPane.showMessageDialog(null, "invalid value " +
                            "for tangent (result is undefined)");
                    resetNum(num, "");
                }
                // all the other values are valid for tangent
                else
                {
                    double result = Math.tan(Math.toRadians(num));
                    resetNum(num, String.format("%.5f", result));
                }
            }
        );
        ln.addActionListener(e ->
            {
                double num = getNum();
                double result = Math.log(num);
                resetNum(num, String.format("%.5f", result));
            });
        squared.addActionListener(e ->
            {
                double num = getNum();
                double result = Math.pow(num, 2);
                resetNum(num, String.format("%.5f", result));
            });
        cubed.addActionListener(e ->
            {
                double num = getNum();
                double result = Math.pow(num, 3);
                resetNum(num, String.format("%.5f", result));
            });
        sqrt.addActionListener(e ->
            {
                double num = getNum();
                double result = Math.sqrt(num);
                resetNum(num, String.format("%.5f", result));
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
                BasicCalculator.main(new String[] {displayArea.getText()});
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

    private void createDrawingPanel()
    {
        JPanel drawingPanel = new JPanel();
        drawingPanel.setLayout(new BorderLayout());

        JPanel header = new JPanel();
        final int FIELD_WIDTH = 8;
        JTextField m = new JTextField(FIELD_WIDTH);
        JTextField b = new JTextField(FIELD_WIDTH);
        JButton draw = new JButton("draw");

        header.add(draw);
        header.add(new JLabel("  y = mx + b;  "));
        header.add(new JLabel("m ="));
        header.add(m);
        header.add(new JLabel("b ="));
        header.add(b);


        drawingPanel.add(header, BorderLayout.NORTH);
        this.centralPanel.add(drawingPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args)
    {
        ScientificCalculator calculator = new ScientificCalculator();

        // there is still content left in displayArea when switching from basic to scientific
        if (args.length != 0)
        {
            calculator.displayArea.setText(args[0]);
        }

        // add the additionalKeys to its centralPanel which inherited from the BasicCalculator
        calculator.centralPanel.add(calculator.additionalKeys, BorderLayout.EAST);
        frame.setContentPane(calculator.centralPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(530, 700);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

    }


}
