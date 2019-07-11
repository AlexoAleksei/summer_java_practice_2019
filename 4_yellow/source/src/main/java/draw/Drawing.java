package draw;

import java.awt.*;
import java.util.Vector;
import source.*;

public class Drawing implements Drawable {
    private Graph graph;
    private Vector<Vertex> vertex;
    private Vector<Edge> edges;
    private Point center;
    private Condition condition;

    public Drawing() {
        graph = null;
        vertex = new Vector<Vertex>();
    }

    public void draw(Graphics2D g) {
        if(vertex == null || vertex.size() == 0)
            return;
        int ovalSide = vertex.elementAt(0).getSide();
        for(int i = 0; i < edges.size(); i++) {
            if(edges.elementAt(i).IsBridge()) {
                g.setColor(Color.RED);
            }
            else {
                g.setColor(edges.elementAt(i).getColor());
            }
            g.drawLine(edges.elementAt(i).getStartV().getVertexCenter().x, edges.elementAt(i).getStartV().getVertexCenter().y,
                    edges.elementAt(i).getEndV().getVertexCenter().x, edges.elementAt(i).getEndV().getVertexCenter().y);
        }
        for(int i = vertex.size()-1; i > -1; i--) {
            g.setColor(vertex.elementAt(i).getColor());
            g.fillOval(vertex.elementAt(i).getVertexCenter().x - ovalSide/2, vertex.elementAt(i).getVertexCenter().y - ovalSide/2,
                    ovalSide, ovalSide);
            g.setColor(Color.BLACK);
            g.drawString(Integer.toString(i + 1), vertex.elementAt(i).getVertexCenter().x - 3 - 3*(i+1)/10, vertex.elementAt(i).getVertexCenter().y + 4);
        }
    }

    public void drawWithCondition(Graphics2D g){
        graph.setBridges(condition.getBridges());
        setBridges();
        if(vertex == null || vertex.size() == 0)
            return;
        int ovalSide = vertex.elementAt(0).getSide();
        for(int i = 0; i < edges.size(); i++) {
            if(edges.elementAt(i).IsBridge()) {
                g.setColor(Color.RED);
            }
            else {
                g.setColor(edges.elementAt(i).getColor());
            }
            g.drawLine(edges.elementAt(i).getStartV().getVertexCenter().x, edges.elementAt(i).getStartV().getVertexCenter().y,
                    edges.elementAt(i).getEndV().getVertexCenter().x, edges.elementAt(i).getEndV().getVertexCenter().y);
        }
        for(int i = vertex.size()-1; i > -1; i--) {
            if(condition.getCurrentV() == i) {
                g.setColor(Color.GREEN);
            }
            else if(condition.getUsed()[i]) {
                g.setColor(Color.ORANGE);
            }
            else {
                g.setColor(vertex.elementAt(i).getColor());
            }
            g.fillOval(vertex.elementAt(i).getVertexCenter().x - ovalSide/2, vertex.elementAt(i).getVertexCenter().y - ovalSide/2,
                    ovalSide, ovalSide);
            g.setColor(Color.BLACK);
            g.drawString(Integer.toString(i + 1), vertex.elementAt(i).getVertexCenter().x - 3 - 3*(i+1)/10, vertex.elementAt(i).getVertexCenter().y + 4);
        }
    }

    public void setGraph(Graph graph){
        this.graph = graph;
        for(int i = 0; i < graph.getVertexList().length; i++) {
            vertex.add(new Vertex(50));
        }
        edges = new Vector<Edge>();
        setAllCoordinates();
        //setBridges();
    }

    public void setCenterCoordinates(Point center){ this.center = center; }

    private void setAllCoordinates(){
        int R = 50 + graph.getVertexList().length * graph.getVertexList().length;
        for(int i = 0; i < graph.getVertexList().length; i++){
            vertex.elementAt(i).setVertexCenter(new Point((int)(center.x + R * Math.cos((double)360/graph.getVertexList().length + 2 * Math.PI * i / graph.getVertexList().length)),
                    (int)(center.y + R * Math.sin((double)360/graph.getVertexList().length + 2 * Math.PI * i / graph.getVertexList().length))));
        }
        Point[] edges_arr = graph.getEdges();
        for (int i = 0; i < graph.getEdgeAmount(); i++) {
            edges.add(new Edge(vertex.elementAt(edges_arr[i].x), vertex.elementAt(edges_arr[i].y)));
        }
    }

    public void setBridges() {
        if(!graph.getBridges().isEmpty()) {
            for(int i = 0; i < graph.getBridges().size(); i++){
                for(int j = 0; j < graph.getEdgeAmount(); j++) {
                    if (vertex.elementAt(graph.getBridges().elementAt(i).x - 1) == edges.elementAt(j).getStartV() ||
                            vertex.elementAt(graph.getBridges().elementAt(i).x - 1) == edges.elementAt(j).getEndV()) {
                        if (vertex.elementAt(graph.getBridges().elementAt(i).y - 1) == edges.elementAt(j).getEndV() ||
                                vertex.elementAt(graph.getBridges().elementAt(i).y - 1) == edges.elementAt(j).getStartV())
                        {
                            edges.elementAt(j).setBridge(true);
                        }
                    }
                }
            }
        }
    }

    public Vector<Vertex> getVertices(){ return vertex; }
    public Vector<Edge> getEdges(){ return edges; }

    public void removeBridges(){
        graph.clearBridges();
        for(int i=0; i < edges.size(); i++){
            edges.elementAt(i).setBridge(false);
        }
    }

    public void clearBridges(){
        for(int i=0; i < edges.size(); i++){
            edges.elementAt(i).setBridge(false);
        }
    }

    public void setCondition(Condition condition){ this.condition = condition; }
}