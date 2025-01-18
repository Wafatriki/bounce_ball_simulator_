package es.ulpgc.dis.model;

public class Physics {
    private final double dt;

    public Physics(double dt) {
        this.dt = dt;
    }

    public double newVelocityOf(double v, double g, double timeToBounce, double cr) {
        return willBounce(v, timeToBounce) ? newVelocityAfterBounce(v, g, timeToBounce, cr) : v + g * dt;
    }

    public double newHeightOf(double h, double r, double v, double g, double timeToBounce, double cr) {
        return willBounce(v, timeToBounce) ? newHeightAfterBounce(h, r, v, g, timeToBounce, cr) : h + v * dt;
    }

    private double newVelocityAfterBounce(double v, double g, double timeToBounce, double cr) {
        return -cr * (v + g * timeToBounce);
    }

    private double newHeightAfterBounce(double h, double r, double v, double g, double timeToBounce, double cr) {
        double remainingTime = dt - timeToBounce;
        return r + newVelocityAfterBounce(v, g, timeToBounce, cr) * remainingTime + 0.5 * g * Math.pow(remainingTime, 2);
    }

    public double timeToBounceOf(double h, double r, double v) {
        return -(h - r) / v;
    }

    public boolean willBounce(double v, double timeToBounce) {
        return isFalling(v) && dt > timeToBounce;
    }

    private static boolean isFalling(double v) {
        return v < 0;
    }
}