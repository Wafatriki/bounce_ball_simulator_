package es.ulpgc.dis.view;

public class Coordinates {
    public final int x;
    public final int y;
    public static int width;
    public static int height;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Coordinates at(int x, int y) {
        return new Coordinates(x - width / 2, height - y);
    }
}