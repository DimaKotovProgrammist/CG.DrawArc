import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ColorConvertOp;
import java.util.ArrayList;
import java.util.List;


public class Frame extends JFrame {

    private JPanel paneMain;
    private JPanel jPanel;
    private int x,y;

    private Point point = new Point(false);
    private class OvalPanel extends JPanel {
        public void paintComponent(Graphics g) {
            if (point.isFlag()) {
                Graphics2D g2 = (Graphics2D) g;
                int x = point.getX();
                int y = point.getY();
                int size = point.getSize() - 10;
                g2.setColor(point.getColor());
                g2.fillOval(x - point.getSize() / 2, y - point.getSize() / 2, point.getSize(), point.getSize());
                g2.setColor(Color.WHITE);
                g2.fillOval(x - size / 2, y - size / 2, size, size);
            }
        }
    }

    public Frame() {
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        this.setContentPane(paneMain);
        setVisible(true);

        jPanel.setLayout(new BorderLayout());
        OvalPanel ovalPanel = new OvalPanel();
        jPanel.add(ovalPanel);

        ovalPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (!point.isFlag()) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        point = new Point(e.getX(), e.getY(), 100,true, Color.BLACK);
                        repaint();
                    }
                }
            }
        });

        ovalPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (Math.pow((point.getX() - e.getX()), 2) + Math.pow((point.getY() - e.getY()), 2) <= Math.pow(point.getSize()/10, 2)
                        && SwingUtilities.isLeftMouseButton(e)) {
                    point.setColor(Color.RED);
                    point.setMotion(true);
                    point.setIncrease(false);
                    repaint();
                }
                if  (SwingUtilities.isRightMouseButton(e)) {
                    point.setColor(Color.BLACK);
                    point.setMotion(false);
                    point.setIncrease(false);
                    repaint();
                }
                if (Math.pow((point.getX() - e.getX()), 2) + Math.pow((point.getY() - e.getY()), 2) <= Math.pow(point.getSize()/2, 2)
                        && Math.pow((point.getX() - e.getX()), 2) + Math.pow((point.getY() - e.getY()), 2) >= Math.pow(point.getSize()/2-5, 2) && SwingUtilities.isLeftMouseButton(e)) {
                    point.setColor(Color.BLUE);
                    point.setIncrease(true);
                    point.setMotion(false);
                    x = e.getX();
                    y = e.getY();
                    repaint();
                }
            }
        });


        ovalPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (Math.pow((point.getX() - e.getX()), 2) + Math.pow((point.getY() - e.getY()), 2) <= Math.pow(point.getSize()/2, 2)
                        && point.isMotion()) {
                    point.setX(e.getX());
                    point.setY(e.getY());
                    repaint();
                }
                if (Math.pow((point.getX() - e.getX()), 2) + Math.pow((point.getY() - e.getY()), 2) <= Math.pow(point.getSize()/2+5, 2)
                        && Math.pow((point.getX() - e.getX()), 2) + Math.pow((point.getY() - e.getY()), 2) >= Math.pow(point.getSize()/2-5, 2)
                        && point.isIncrease()) {
                    double r = Math.pow(e.getX() - point.getX(), 2) + Math.pow(e.getY() - point.getY(), 2);
                    r = Math.pow(r, 0.5);
                    point.setSize(2* (int)r);
                    repaint();
                }
            }
        });
        ovalPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (Math.pow((point.getX() - e.getX()), 2) + Math.pow((point.getY() - e.getY()), 2) <= Math.pow(point.getSize()/2, 2)
                        && Math.pow((point.getX() - e.getX()), 2) + Math.pow((point.getY() - e.getY()), 2) >= Math.pow(point.getSize()/2-5, 2)
                        && !point.isIncrease() && !point.isMotion() && SwingUtilities.isRightMouseButton(e)) {
                    JColorChooser jColorChooser = new JColorChooser();
                    Color color = JColorChooser.showDialog(null,"Select a color",Color.black);
                    jPanel.add()
                }
            }
        });
    }
}
