package source;

import java.awt.*;

public class Vertex {
    private Point vertexCenter;
    private Color color;
    private int side;

    public Vertex() {
        vertexCenter = new Point();
        color = Color.CYAN;
    }

    public Vertex(int side) {
        vertexCenter = new Point();
        color = Color.CYAN;
        this.side = side;
    }

    public Point getVertexCenter() {
        return vertexCenter;
    }

    public void setVertexCenter(Point center) {
        //vertexCenter.x = x;
        //vertexCenter.y = y;
        vertexCenter = center;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color newColor) {
        color = newColor;
    }

    public int getSide(){ return side; }
}
