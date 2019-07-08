package draw;

import java.awt.*;
import source.*;

public class Drawing implements Drawable {
    private Graph graph;
    private Vertex[] vertex;
    private Edge[] edges;
    private Point center;

    public Drawing() {
        graph = null;
    }

   // @Override
    public void draw(Graphics2D g) {
        if(vertex == null)
            return;
        int ovalSide = vertex[0].getSide();
        for(int i = 0; i < edges.length; i++) {
            if(edges[i].IsBridge()) {
                g.setColor(Color.RED);
            }
            else g.setColor(edges[i].getColor());
            g.drawLine(edges[i].getStartV().getVertexCenter().x, edges[i].getStartV().getVertexCenter().y,
                    edges[i].getEndV().getVertexCenter().x, edges[i].getEndV().getVertexCenter().y);
        }
        for(int i = 0; i < vertex.length; i++) {
            g.setColor(vertex[i].getColor());
            g.fillOval(vertex[i].getVertexCenter().x - ovalSide/2, vertex[i].getVertexCenter().y - ovalSide/2,
                    ovalSide, ovalSide);
            g.setColor(Color.BLACK);
            g.drawString(Integer.toString(i + 1), vertex[i].getVertexCenter().x - 3 - 3*(i+1)/10, vertex[i].getVertexCenter().y + 4);
        }
    }

    public void setGraph(Graph graph){
        this.graph = graph;
        vertex = new Vertex[graph.getVertexList().length];
        for(int i = 0; i < vertex.length; i++) {
            vertex[i] = new Vertex(50 + vertex.length);
        }

        int max_edges = (graph.getVertexList().length * (graph.getVertexList().length - 1) / 2);
        edges = new Edge[max_edges];
        for(int i = 0; i < max_edges; i++) {
            edges[i] = new Edge();
        }
        setAllCoordinates(max_edges);
        setBridges();
    }

    public void setCenterCoordinates(Point center){ this.center = center; }

    private void setAllCoordinates(int max_edges){
        int R = 50 + graph.getVertexList().length * graph.getVertexList().length;
        for(int i = 0; i < graph.getVertexList().length; i++){
            vertex[i].setVertexCenter(new Point((int)(center.x + R * Math.cos((double)360/graph.getVertexList().length + 2 * Math.PI * i / graph.getVertexList().length)),
                    (int)(center.y + R * Math.sin((double)360/graph.getVertexList().length + 2 * Math.PI * i / graph.getVertexList().length))));
        }
        Point[] edges_arr = new Point[max_edges];
        edges_arr = graph.getEdges();
        if(edges_arr == null)
            System.out.println("Ребер нет, но вы держитесь");
        else {
            for (int i = 0; i < graph.getEdgeAmount(); i++) {
                edges[i].setStartV(vertex[edges_arr[i].x]);
                edges[i].setEndV(vertex[edges_arr[i].y]);
            }
        }
    }

    public void setBridges() {
        if(!graph.getBridges().isEmpty()) {
            for(int i = 0; i < graph.getBridges().size(); i++){
                for(int j = 0; j < graph.getEdgeAmount(); j++) {
                    if (vertex[graph.getBridges().elementAt(i).x - 1] == edges[j].getStartV() ||
                            vertex[graph.getBridges().elementAt(i).x - 1] == edges[j].getEndV()) {
                        if (vertex[graph.getBridges().elementAt(i).y - 1] == edges[j].getEndV() ||
                                vertex[graph.getBridges().elementAt(i).y - 1] == edges[j].getStartV())
                        {
                            edges[j].setBridge();
                        }
                    }
                }
            }
        }
    }

    public Vertex[] getVertices(){ return vertex; }

    public Graph getGraph(){ return graph; }

}