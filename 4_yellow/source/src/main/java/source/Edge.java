package source;

import java.awt.*;

public class Edge {
    private Vertex StartV;
    private Vertex EndV;
    private boolean Bridge;
    private Color color;

    public Edge() {
        StartV = new Vertex();
        EndV = new Vertex();
        Bridge = false;
        color = Color.BLACK;
    }

    public Edge(Vertex startV, Vertex endV){
        this.StartV = startV;
        this.EndV = endV;
        Bridge = false;
        color = Color.BLACK;
    }

    public Vertex getStartV() {
        return StartV;
    }
    public Vertex getEndV() {
        return EndV;
    }
    public void setStartV(Vertex startV) { StartV = startV; }
    public void setEndV(Vertex endV) {
        EndV = endV;
    }
    public void setBridge(boolean bool) { Bridge = bool; }
    public boolean IsBridge() { return Bridge; }
    public Color getColor() {
        return color;
    }
    public void setColor(Color newColor) {
        color = newColor;
    }
}