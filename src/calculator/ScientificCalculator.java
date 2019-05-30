package calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

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

    private JPanel drawingPanel;
    private DrawingComponent drawLine;
    private boolean drawingMode;
    private JPanel header;
    private JButton draw;
    JTextField m;
    JTextField b;


    private double y1;
    private double y2;

    static final int DRAWING_WIDTH = 500;
    static final int DRAWING_HEIGHT = 400;
    static final double x1 = DRAWING_WIDTH / 2.0;
    static final double x2 = DRAWING_WIDTH / -2.0;

    public ScientificCalculator()
    {
        drawingMode = false;
        createSwitchButton();

        createDrawPanel();

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
        graph.addActionListener(e ->
            {
                if (!drawingMode)
                {
                    drawingPanel.setVisible(true);
                    frame.pack();
                    drawingMode = true;
                }
                else
                {
                    drawingPanel.setVisible(false);
                    frame.pack();
                    drawingMode = false;
                }
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

    private void createDrawPanel()
    {
        drawingPanel = new JPanel();
        drawingPanel.setLayout(new BorderLayout());

        createDrawPanelHeader();
        drawingPanel.add(header, BorderLayout.NORTH);

        createDrawComponent();
        drawingPanel.add(drawLine, BorderLayout.CENTER);

        this.centralPanel.add(drawingPanel, BorderLayout.SOUTH);
    }

    private void createDrawComponent()
    {
        drawLine = new DrawingComponent();
        drawLine.setPreferredSize(new Dimension(DRAWING_WIDTH, DRAWING_HEIGHT));
    }

    private void createDrawPanelHeader()
    {
        header = new JPanel();

        createHeaderTextField();
        createDrawButton();

        header.add(draw);
        header.add(new JLabel("  y = mx + b;  "));
        header.add(new JLabel("m ="));
        header.add(m);
        header.add(new JLabel("b ="));
        header.add(b);
    }

    private void createHeaderTextField()
    {
        final int FIELD_WIDTH = 8;
        m = new JTextField(FIELD_WIDTH);
        b = new JTextField(FIELD_WIDTH);

        m.setText("1");
        b.setText("0");
    }

    private void createDrawButton()
    {
        draw = new JButton("draw");
        draw.addActionListener(e ->
            {
                double m = Double.parseDouble(this.m.getText());
                double b = Double.parseDouble(this.b.getText());

                y1 = x1 * m + b;
                y2 = x2 * m + b;
                drawLine.reDraw(y1, y2);
            });
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

/**
 * A component that draws a linear line
 */
class DrawingComponent extends JComponent
{
    private double y1;
    private double y2;

    public DrawingComponent()
    {
        y1 = ScientificCalculator.x1;
        y2 = ScientificCalculator.x2;
    }
    public void paintComponent(Graphics g)
    {
        Graphics2D gr = (Graphics2D) g;
        gr.translate(ScientificCalculator.DRAWING_WIDTH / 2.0,
                ScientificCalculator.DRAWING_HEIGHT / 2.0);
//        gr.translate(200, 200);
        gr.scale(10, -10);

        gr.drawLine(0, 200, 0, -200);
        gr.drawLine(200, 0, -200, 0);

        Shape line = new Line2D.Double(ScientificCalculator.x1, y1, ScientificCalculator.x2, y2);
        gr.draw(line);
    }

    public void reDraw(double y1, double y2)
    {
        this.y1 = y1;
        this.y2 = y2;
        repaint();
    }
}
