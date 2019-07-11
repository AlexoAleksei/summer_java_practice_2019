package source;

import java.awt.*;
import java.util.Vector;

public class Condition {
    private Vector<Point> bridges;
    private boolean[] used;
    private int currentV;

    public Condition(Vector<Point> bridges, boolean[] used, int currentV){
        this.bridges = bridges;
        this.used = used;
        this.currentV = currentV;
    }

    public int getCurrentV() {
        return currentV;
    }
    public Vector<Point> getBridges() {
        return bridges;
    }
    public boolean[] getUsed() {
        return used;
    }
}

















































