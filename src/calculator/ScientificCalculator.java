package calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
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

    // Components needed for drawingPanel
    private JPanel drawingPanel;
    private DrawingComponent drawLine;
    private boolean drawingMode;
    private JPanel header;
    private JButton draw;
    private JTextField m;
    private JTextField b;

    // The dimension of the drawLine component
    private static final int DRAWING_WIDTH = 440;
    private static final int DRAWING_HEIGHT = 440;

    // The two coordinates needed for drawing a linear line
    private double y1;
    private double y2;
    static final double x1 = DRAWING_WIDTH / 2.0;
    static final double x2 = DRAWING_WIDTH / -2.0;

    public ScientificCalculator()
    {
        drawingMode = false;
        createSwitchButton();

        createParenthesisButtons();

        createOperationButtons();

        createDrawingPanel();
    }

    /**
     * create buttons for adding opening or closing parenthesis
     */
    private void createParenthesisButtons()
    {
        closingParenthesis.addActionListener(e ->
                {
                    String expression = getExpression();
                    int countOpenParenthesis = 0;
                    int countCloseParentesis = 0;

                    for (int i = 0; i < expression.length(); i++)
                    {
                        if (expression.charAt(i) == '(')
                        {
                            countOpenParenthesis++;
                        }
                        else if (expression.charAt(i) == ')')
                        {
                            countCloseParentesis++;
                        }
                    }

                    if (countCloseParentesis < countOpenParenthesis)
                    {
                        addSymbol(")");
                    }

                });

        openingParenthesis.addActionListener(e ->
        {
            String content = displayArea.getText();
            if (content.length() != 0)
            {
                char lastChar = content.charAt(content.length() - 1);
                if ((lastChar != ')' && !Character.isDigit(lastChar)))
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
    }

    /**
     * Creates buttons for doing operations
     */
    private void createOperationButtons()
    {
        sin.addActionListener(e ->
        {
            String num = getNum();
            if (!num.equals(""))
            {
                double result = Math.sin(Math.toRadians(Double.parseDouble(num)));

                // replace the num with the corresponding result with the displayArea
                resetNum(num, String.format("%.2f", result));
            }
        });
        cos.addActionListener(e ->
        {
            String num = getNum();
            if (!num.equals(""))
            {
                double result = Math.cos(Math.toRadians(Double.parseDouble(num)));
                resetNum(num, String.format("%.2f", result));
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
                            resetNum(strNum, String.format("%.2f", result));
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
                    resetNum(num, String.format("%.2f", result));
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
                resetNum(num, String.format("%.2f", result));
            }
        });
        cubed.addActionListener(e ->
        {
            String num = getNum();
            if (!num.equals(""))
            {
                double result = Math.pow(Double.parseDouble(num), 3);
                resetNum(num, String.format("%.2f", result));
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
                    resetNum(strNum, String.format("%.2f", result));
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

    /**
     * This method sets up the DrawingPanel for displaying the graph and its associated content
     */
    private void createDrawingPanel()
    {
        // Sets up the panel which is responsible for holding all the related components
        drawingPanel = new JPanel();
        drawingPanel.setLayout(new BorderLayout());

        // Sets up the header(top) part of the drawing panel
        createDrawPanelHeader();
        drawingPanel.add(header, BorderLayout.NORTH);

        // Sets up the panel where the graph will be in
        JPanel drawing = new JPanel();

        drawing.add(new JPanel());            // Sets an empty panel to the left of the graph
        createDrawingComponent();             // Sets the actual graph
        drawing.add(drawLine);
        drawing.add(new JPanel());            // Sets an empty panel to the right of the graph

        drawingPanel.add(drawing, BorderLayout.CENTER);

        // Puts the drawingPanel at the bottom of the centralPanel
        this.centralPanel.add(drawingPanel, BorderLayout.SOUTH);
        drawingPanel.setVisible(drawingMode);
    }

    /**
     * This method sets up the graph
     */
    private void createDrawingComponent()
    {
        drawLine = new DrawingComponent();
        drawLine.setPreferredSize(new Dimension(DRAWING_WIDTH, DRAWING_HEIGHT));
    }

    /**
     * This method sets up the top part of the drawingPanel, including buttons and textField for
     * user to do input
     */
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

    /**
     * Creates textField for user to enter the value
     */
    private void createHeaderTextField()
    {
        final int FIELD_WIDTH = 8;
        m = new JTextField(FIELD_WIDTH);
        b = new JTextField(FIELD_WIDTH);

        // Sets the default value
        m.setText("1");
        b.setText("0");
    }

    /**
     * Creates the button to generate new graph once being hit according to the value user has
     * entered
     */
    private void createDrawButton()
    {
        draw = new JButton("draw");
        draw.addActionListener(e ->
            {
                // Gets the m and b value entered by the user
                double m = Double.parseDouble(this.m.getText());
                double b = Double.parseDouble(this.b.getText());

                // each y value of 1 is equivalent to 5 pixel-length when shown on the screen
                y1 = x1 * m + b * -DrawingComponent.yScale;
                y2 = x2 * m + b * -DrawingComponent.yScale;
                drawLine.reDraw(y1, y2);
            });
    }


    public static void main(String[] args)
    {
        ScientificCalculator calculator = new ScientificCalculator();

        // If it's switching from Basic, not the first time starting the ScientificCalculator
        if (args.length != 0)
        {
            calculator.displayArea.setText(args[0]);
        }

        // add the additionalKeys to its centralPanel which inherited from the BasicCalculator
        calculator.centralPanel.add(calculator.additionalKeys, BorderLayout.EAST);

        frame.setContentPane(calculator.centralPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }
}

/**
 * This class helps to draw a linear function graph
 */
class DrawingComponent extends JComponent
{
    private double x1;
    private double x2;
    private double y1;
    private double y2;
    static double xScale = 5;
    static double yScale = -5;

    /**
     * Constructs a DrawingComponent with  the default valus for x1, x2, y1, y2
     */
    public DrawingComponent()
    {
        x1 = ScientificCalculator.x1;
        x2 = ScientificCalculator.x2;
        y1 = x1;
        y2 = x2;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D gr = (Graphics2D) g;

        // Moves the origin of the coordinate system to the center of the component
        gr.translate(this.getWidth() / 2.0, this.getHeight() / 2.0);

        // Stores the current transformation into originalFormat for printing text later on
        AffineTransform originalFormat = gr.getTransform();

        // Sets the scale for x and y values
        gr.scale(xScale, yScale);

        double maxOfX = this.getWidth() / 2.0 / xScale;
        double maxOfY = this.getHeight() / 2.0 / -yScale;
        gr.draw(new Line2D.Double(0, maxOfY, 0, -maxOfY));
        gr.draw(new Line2D.Double(maxOfX, 0 , -maxOfX, 0));

        // drawing the arrows
        double arrowLength = 3.5;
        // Arrow points to the positive direction of y axis
        gr.draw(new Line2D.Double(0, maxOfY, -arrowLength+1, maxOfY - arrowLength));
        gr.draw(new Line2D.Double(0, maxOfY, arrowLength-1, maxOfY - arrowLength));

        // Arrow points to the positive direction of x axis
        gr.draw(new Line2D.Double(maxOfX, 0, maxOfX - arrowLength, arrowLength-1));
        gr.draw(new Line2D.Double(maxOfX, 0, maxOfX - arrowLength, -arrowLength+1));

        // Drawing segments on x axis, each represents 10 units
        for (int i = 0; i < maxOfX; i+= 10) {
            gr.drawLine(i, 0, i, 1);
        }
        for (int i = 0; i > -maxOfX; i-= 10) {
            gr.drawLine(i, 0, i, 1);
        }

        // Drawing segments on y axis, each represents 10 units
        for (int i = 0; i < maxOfY; i+= 10) {
            gr.drawLine(0, i, 1, i);
        }
        for (int i = 0; i > -maxOfY; i-= 10) {
            gr.drawLine(0, i, 1, i);
        }

        // Draw one single segment on the lower right of the component representing 10 units
        gr.draw(new Line2D.Double(maxOfX-5, -maxOfY+5, maxOfX-15, -maxOfY+5));
        gr.draw(new Line2D.Double(maxOfX-5, -maxOfY+5, maxOfX-5, -maxOfY+6));
        gr.draw(new Line2D.Double(maxOfX-15, -maxOfY+5, maxOfX-15, -maxOfY+6));

        // Draws the graph of a liner function provided with two points
        gr.draw(new Line2D.Double(x1 / xScale, y1  / -yScale, x2 / xScale, y2 / -yScale));

        // Sets scale back to normal for printing text(the positive direction of y is changed
        // as going down, the positive direction of x remains the same going right)
        gr.setTransform(originalFormat);
        gr.setFont(new Font(null, Font.BOLD, 20));
        int _maxOfX = this.getWidth() / 2;
        int _maxOfY = this.getHeight() / 2;
        gr.drawString("y", 20, -_maxOfY + 20);
        gr.drawString("x",  _maxOfX-20, 30);
        gr.drawString("10", _maxOfX-60, _maxOfY-35);
    }

    public void reDraw(double y1, double y2)
    {
        this.y1 = y1;
        this.y2 = y2;
        repaint();
    }
}
