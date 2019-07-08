package source;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Graph {
    protected final int VERTEX_MAX = 100;
    protected int[] vertexList;
    protected int[][] matrix;
    private int edgeAmount = 0;
    protected Vector<Point> bridges;
    protected boolean Bridge;

    public Graph(){
        bridges = new Vector();
        vertexList = new int[VERTEX_MAX];
        matrix = new int[VERTEX_MAX][VERTEX_MAX];
        Bridge = false;
    }

    public int[] getVertexList(){
        return vertexList;
    }

    public int getEdgeAmount() {
        return edgeAmount;
    }

    public int[][] getMatrix(){
       return matrix;
    }



    public Point[] getEdges() {  //Возвращает массив пар индексов: "вершина старт" - "вершина конец"
        Point[] alledges = new Point[2 * getVertexList().length * (getVertexList().length - 1) / 2];
        for(int i = 0; i < (getVertexList().length * (getVertexList().length - 1) / 2); i++) {
            alledges[i] = new Point();
        }
        int iter = 0;
        for(int i = 0; i < vertexList.length; i++) {
            for(int j = i; j < vertexList.length; j++) {
                if(matrix[i][j] == 1){
                    alledges[iter].x = i;
                    alledges[iter].y = j;
                    iter++;
                }
            }
        }
        edgeAmount = iter;
        return alledges;
    }


    public void printBridgesToTextAre(JTextArea textArea){
        if(bridges.size() <= 0)
            textArea.append("Мостов не обнаружено" + "\n");
        for(int i = 0; i < bridges.size(); i++)
            textArea.append(bridges.elementAt(i).x + " " + bridges.elementAt(i).y + "\n");
    }

    public Vector<Point> getBridges(){
        return bridges;
    }
}

