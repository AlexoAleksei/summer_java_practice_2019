package gui;

import draw.*;
import source.*;
//import sun.corba.Bridge;

import javax.swing.*;
import java.awt.*;

public class GraphViewPanel extends JPanel {
    private draw.Drawing graphDraw;
    private BridgeFinder graph;

    public GraphViewPanel(){
        graphDraw = new draw.Drawing();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //обязательная проверка
        if(graph == null)
            return;

        graphDraw.draw(g2);
    }

    public BridgeFinder getGraph(){
        return graph;
    }

    public void setGraph(BridgeFinder graph){
        this.graph = graph;
        graphDraw.setCenterCoordinates(new Point(this.getVisibleRect().width/2, this.getVisibleRect().height/2));
    }

    public draw.Drawing getGraphDraw(){ return graphDraw; }
}

