
import java.awt.*;

public class Point {
    private int x;
    private int y;
    private int size;
    private boolean flag;

    private boolean motion;
    private Color color;

    private boolean increase;

    public Point(int x, int y, int size, boolean flag, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.flag = flag;
        this.color = color;
    }
    public Point(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isMotion() {
        return motion;
    }

    public void setMotion(boolean motion) {
        this.motion = motion;
    }

    public boolean isIncrease() {
        return increase;
    }

    public void setIncrease(boolean increase) {
        this.increase = increase;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
