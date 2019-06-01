package calculator;

import javax.sound.sampled.Line;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.function.DoublePredicate;

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

    // Components needed for drawingPanel
    private JPanel drawingPanel;
    private DrawingComponent drawLine;
    private boolean drawingMode;
    private JPanel header;
    private JButton draw;
    private JTextField m;
    private JTextField b;

    // The dimension of the drawLine component
    static final int DRAWING_WIDTH = 440;
    static final int DRAWING_HEIGHT = 440;

    // The two coordinates needed for drawing a linear line
    private double y1;
    private double y2;
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
                // If there is no content
                else
                {
                    displayArea.append("(");
                }
            });


        sin.addActionListener(e ->
            {
                String num = getNum();
                if (!num.equals(""))
                {
                    double result = Math.sin(Math.toRadians(Double.parseDouble(num)));

                    // replace the num with the corresponding result with the displayArea
                    resetNum(num, String.format("%.5f", result));
                }
            });
        cos.addActionListener(e ->
            {
                String num = getNum();
                if (!num.equals(""))
                {
                    double result = Math.cos(Math.toRadians(Double.parseDouble(num)));
                    resetNum(num, String.format("%.5f", result));
                }
            });
        tan.addActionListener(e ->
            {
                String strNum = getNum();
                if (!strNum.equals(""))
                {
                    double num = Double.parseDouble(strNum);
                    // if 90 degree or 270 degree and so on
                    if ((num / 90) % 2 == 1) {
                        JOptionPane.showMessageDialog(null, "Invalid Value!\n" +
                                "Result is undefined");
                        resetNum(strNum, "");
                    }
                    // all the other values are valid for tangent
                    else {
                        double result = Math.tan(Math.toRadians(num));
                        resetNum(strNum, String.format("%.5f", result));
                    }
                }
            }
        );
        ln.addActionListener(e ->
            {
                String num = getNum();
                if (!num.equals(""))
                {
                    if (!num.contains("-"))
                    {
                        double result = Math.log(Double.parseDouble(num));
                        resetNum(num, String.format("%.5f", result));
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Invalid Value!\n" +
                                "Cannot ln a negative number");
                        resetNum(num, "");
                    }
                }
            });
        squared.addActionListener(e ->
            {
                String num = getNum();
                if (!num.equals(""))
                {
                    double result = Math.pow(Double.parseDouble(num), 2);
                    resetNum(num, String.format("%.5f", result));
                }
            });
        cubed.addActionListener(e ->
            {
                String num = getNum();
                if (!num.equals(""))
                {
                    double result = Math.pow(Double.parseDouble(num), 3);
                    resetNum(num, String.format("%.5f", result));
                }
            });
        sqrt.addActionListener(e ->
            {
                String strNum = getNum();
                if (!strNum.equals(""))
                {
                    double num = Double.parseDouble(strNum);
                    if (num < 0)
                    {
                        JOptionPane.showMessageDialog(null, "Invalid Value!\n " +
                                "Cannot sqrt a negative number");
                        resetNum(strNum, "");
                    }
                    else
                    {
                        double result = Math.sqrt(num);
                        resetNum(strNum, String.format("%.5f", result));
                    }
                }
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



    private void createDrawPanel()
    {
        drawingPanel = new JPanel();
        drawingPanel.setLayout(new BorderLayout());

        createDrawPanelHeader();
        drawingPanel.add(header, BorderLayout.NORTH);

        JPanel drawing = new JPanel();
        drawing.add(new JPanel());
        createDrawComponent();
        drawing.add(drawLine);
        drawing.add(new JPanel());
        drawingPanel.add(drawing, BorderLayout.CENTER);


        this.centralPanel.add(drawingPanel, BorderLayout.SOUTH);

        drawingPanel.setVisible(drawingMode);
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
    private double x1;
    private double x2;
    private double y1;
    private double y2;

    public DrawingComponent()
    {
        x1 = ScientificCalculator.x1;
        x2 = ScientificCalculator.x2;
        y1 = x1;
        y2 = x2;
    }
    public void paintComponent(Graphics g)
    {
        Graphics2D gr = (Graphics2D) g;

        // Moves the origin of the coordinate system to the center of the component
        gr.translate(this.getWidth() / 2.0, this.getHeight() / 2.0);
        double xScale = 5;
        double yScale = -5;
        gr.scale(xScale, yScale);

        double maxOfX = this.getWidth() / 2.0 / xScale;
        double maxOfY = this.getHeight() / 2.0 / -yScale;
        gr.draw(new Line2D.Double(0, maxOfY, 0, -maxOfY));
        gr.draw(new Line2D.Double(maxOfX, 0 , -maxOfX, 0));

        // drawing the arrows
        double arrowLength = 3.5;
        // Arrow points to the positive direction of y axis
        gr.draw(new Line2D.Double(0, maxOfY, -arrowLength, maxOfY - arrowLength));
        gr.draw(new Line2D.Double(0, maxOfY, arrowLength, maxOfY - arrowLength));
        // Arrow points to the positive direction of x axis
        gr.draw(new Line2D.Double(maxOfX, 0, maxOfX - arrowLength, arrowLength));
        gr.draw(new Line2D.Double(maxOfX, 0, maxOfX - arrowLength, -arrowLength));

        // Draws the graph of a liner function provided with two points
        gr.draw(new Line2D.Double(x1 / xScale, y1 / -yScale, x2 / xScale, y2 / -yScale));
    }

    public void reDraw(double y1, double y2)
    {
        this.y1 = y1;
        this.y2 = y2;
        repaint();
    }
}
