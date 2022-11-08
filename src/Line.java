public class Line {
    private final double x1;
    private final double y1;
    private final double  k;
    private final double b;

    public Line(double x0, double y0, double x1, double y1) {
        this.x1 = x1;
        this.y1 = y1;
        this.k = (y1 - y0) / (x1 - x0);
        this.b = (x1 * y0 - x0 * y1) / (x1 - x0);
    }

    public boolean isPointUnderLine(double x, double y) {
        return y > x * k + b;
    }

    public double getX1() {
        return x1;
    }

    public double getY1() {
        return y1;
    }

    public double getK() {
        return k;
    }
}