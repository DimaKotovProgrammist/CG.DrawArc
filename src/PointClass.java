
import java.awt.*;

public class PointClass {
    private int x;
    private int y;
    private int size;
    private boolean flag;

    private boolean motion;
    private Color color;

    private boolean arc;

    private boolean increase;

    public PointClass(int x, int y, int size, boolean flag, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.flag = flag;
        this.color = color;
    }
    public PointClass(boolean flag) {
        this.flag = flag;
    }
    public PointClass(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
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

    public boolean isArc() {
        return arc;
    }

    public void setArc(boolean arc) {
        this.arc = arc;
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
