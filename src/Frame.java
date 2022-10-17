import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics;


public class Frame extends JFrame {

    private JPanel paneMain;
    private JPanel jPanel;


    private List<PointClass> pointList = new ArrayList<>();

    private double[] colorDifference;
    private PointClass point = new PointClass(false);

    private Line line1;
    private Line line2;
    private class OvalPanel extends JPanel {
        public void paintComponent(Graphics g) {
            if (point.isFlag()) {

                Graphics2D g2 = (Graphics2D) g;
                int x = point.getX();
                int y = point.getY();
                int size = point.getSize() - 8;
                g2.setColor(point.getColor());
                g2.fillOval(x - point.getSize() / 2, y - point.getSize() / 2, point.getSize(), point.getSize());
                g2.setColor(Color.WHITE);
                g2.fillOval(x - size / 2, y - size / 2, size, size);
            }
            if(point.isArc()) {
                Graphics2D px = (Graphics2D) g;
                px.setColor(Color.RED);
                int r = point.getSize()/2;
                int x = 0;
                int y = r;
                int d = 1 - 2*r;
                int e;
                while (y >= x) {
                    drawPixel(px,point.getX() + x,point.getY() + y);
                    drawPixel(px,point.getX() + x,point.getY() - y);
                    drawPixel(px,point.getX() - x,point.getY() + y);
                    drawPixel(px,point.getX() - x,point.getY() - y);
                    drawPixel(px,point.getX() + y,point.getY() + x);
                    drawPixel(px,point.getX() + y,point.getY() - x);
                    drawPixel(px,point.getX() - y, point.getY() + x);
                    drawPixel(px,point.getX() - y,point.getY() - x);
                    e = 2 * (d + y) - 1;
                    if((d < 0) && (e <= 0)) {
                        d += 2 * ++x +1;
                        continue;
                    }
                    if((d > 0) && (e > 0)) {
                        d -= 2 * --y + 1;
                        continue;
                    }
                    d += 2 * (++x - --y);
                }
                pointList = new ArrayList<>();
            }
        }
    }

    private boolean gotToDraw(int x, int y, Line line1, Line line2) {
        if (line1.getX1() > point.getX() && line2.getX1() > point.getX()) {
            if (line1.getK() > line2.getK()) {
                Line temp = line1;
                line1 = line2;
                line2 = temp;
            }
            return line1.isPointUnderLine(x, y)  && !line2.isPointUnderLine(x, y);

        } else if (line1.getX1() < point.getX() && line2.getX1() < point.getX()) {
            if (line1.getK() > line2.getK()) {
                Line temp = line1;
                line1 = line2;
                line2 = temp;
            }
            return !line1.isPointUnderLine(x, y)  && line2.isPointUnderLine(x, y);

        } else if (line1.getY1() >= point.getY() && line2.getY1() >= point.getY()) {

            return line1.isPointUnderLine(x, y)  && line2.isPointUnderLine(x, y);
        } else {

            return !line1.isPointUnderLine(x, y)  && !line2.isPointUnderLine(x, y);
        }
    }
    public int getAngle(int x1l, int y1l, int x2l, int y2l, int cx, int cy) {
        int[] v1 = getVector(cx,cy,x1l,y1l);
        int[] v2 = getVector(cx,cy,x2l,y2l);;
        int x1 = v1[0];
        int x2 = v2[0];
        int y1 = v1[1];
        int y2 = v2[1];
        return (int) Math.toDegrees(Math.acos((x1*x2 + y1*y2)/(Math.sqrt(Math.pow(x1, 2) + Math.pow(y1, 2)) * Math.sqrt(Math.pow(x2,2) + Math.pow(y2,2)))));
    }
    public int[] getVector(int x1, int y1, int x2, int y2) {
        return new int[] {x2 - x1, y2-y1};
    }
    private int fixRgbValues(int value) {
        if (value < 0) value = 0;
        if (value > 255) value = 255;
        return value;
    }


    private double[] findColorsDifference(Color color1, Color color2) {
        int angle = getAngle((int) line1.getX1(), (int) line1.getY1(), (int) line2.getX1(), (int)line2.getY1(),point.getX(),point.getY());
        double Dr = (color1.getRed() - color2.getRed()) / (angle * 1.0);
        double Dg = (color1.getGreen() - color2.getGreen()) / (angle * 1.0);
        double Db = (color1.getBlue() - color2.getBlue()) / (angle * 1.0);

        Dr = (color1.getRed() - color2.getRed() >= 0) ? Math.abs(Dr) * -1 : Math.abs(Dr);
        Dg = (color1.getGreen() - color2.getGreen() >= 0) ? Math.abs(Dg) * -1 : Math.abs(Dg);
        Db = (color1.getBlue() - color2.getBlue() >= 0) ? Math.abs(Db) * -1 : Math.abs(Db);

        return new double[] {Dr, Dg, Db};
    }
    public void drawPixel (Graphics2D g,double x, double y) {
        Color color2 = pointList.get(1).getColor();
        int angle = getAngle((int) x, (int) y, (int) line2.getX1(), (int)line2.getY1(),point.getX(),point.getY());
        Color temp = new Color(fixRgbValues((int) (color2.getRed() + colorDifference[0] * angle)),
                fixRgbValues((int) (color2.getGreen() + colorDifference[1] * angle)),
                fixRgbValues((int) (color2.getBlue() + colorDifference[2] * angle)));
        if (gotToDraw((int) x, (int) y, line1, line2)) {
            g.setColor(temp);
            g.fillRect((int) x, (int) y, 4,4);
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
                        point = new PointClass(e.getX(), e.getY(), 100,true, Color.BLACK);
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
                        && Math.pow((point.getX() - e.getX()), 2) + Math.pow((point.getY() - e.getY()), 2) >= Math.pow(point.getSize()/2-4, 2) && SwingUtilities.isLeftMouseButton(e)) {
                    point.setColor(Color.BLUE);
                    point.setIncrease(true);
                    point.setMotion(false);
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
                if (Math.pow((point.getX() - e.getX()), 2) + Math.pow((point.getY() - e.getY()), 2) <= Math.pow(point.getSize()/2+4, 2)
                        && Math.pow((point.getX() - e.getX()), 2) + Math.pow((point.getY() - e.getY()), 2) >= Math.pow(point.getSize()/2-4, 2)
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
                        && Math.pow((point.getX() - e.getX()), 2) + Math.pow((point.getY() - e.getY()), 2) >= Math.pow(point.getSize()/2-4, 2)
                        && !point.isIncrease() && !point.isMotion() && SwingUtilities.isRightMouseButton(e) && pointList.size() < 2) {
                    Color color = JColorChooser.showDialog(null, "Choose color", Color.BLACK);
                    pointList.add(new PointClass(e.getX(), e.getY(), color));
                    System.out.println("x"+ pointList.size() + " = " + pointList.get(pointList.size()-1).getX() + ", " + "y"+ pointList.size() + " = " + pointList.get(pointList.size()-1).getY());
                    if (pointList.size() == 2) {
                        point.setFlag(false);
                        line1 = new Line(point.getX(), point.getY(), pointList.get(0).getX(), pointList.get(0).getY());
                        line2 = new Line(point.getX(), point.getY(), pointList.get(1).getX(), pointList.get(1).getY());
                        colorDifference = findColorsDifference(pointList.get(1).getColor(), pointList.get(0).getColor());
                        System.out.println("px = " + point.getX() + ", py = " + point.getY());
                        point.setArc(true);
                        repaint();
                    }
                }
            }
        });


    }
}
