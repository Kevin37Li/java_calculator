package practice;

import javax.swing.*;
import java.awt.*;

public class UIprac {

    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.setTitle("test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JComponent component = new ChartComponent();
        JPanel panel = new JPanel();

        panel.add(component);
        component.setPreferredSize(new Dimension(400, 400));

        frame.add(component);

        frame.pack();

        frame.setVisible(true);

    }
}

class ChartComponent extends JComponent
{
    public void paintComponent(Graphics g)
    {
        Graphics2D graphics = (Graphics2D) g;
        g.fillRect(0, 10, 100, 10);
        g.fillRect(0, 30, 200, 10);

        g.drawLine(0, 50, 50, 50);



        graphics.translate(200, 200);
        graphics.scale(1, -1);

        g.drawLine(-200, -200, 200, 800);





    }

}
