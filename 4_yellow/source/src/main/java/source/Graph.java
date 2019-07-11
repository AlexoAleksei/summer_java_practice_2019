package source;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Graph {
    protected final int VERTEX_MAX = 100;
    protected int[] vertexList;
    protected int[][] matrix;
    protected int edgeAmount = 0;
    protected Vector<Point> bridges;

    public Graph(){
        bridges = new Vector<Point>();
        vertexList = new int[0];
        matrix = new int[VERTEX_MAX][VERTEX_MAX];
    }

    public int[] getVertexList(){
        return vertexList;
    }

    public int[][] getMatrix(){
        return matrix;
    }

    public int getEdgeAmount() {
        return edgeAmount;
    }
    public void decrementEdgeAmount() { edgeAmount--; }

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

    public void setBridges(Vector<Point> bridges){
        this.bridges = bridges;
    }

    public void clearBridges(){
        bridges.clear();
    }

    public void deleteVertexFromMatrix(int index){
        for(int i = 0; i < vertexList.length; i++) {
            matrix[index][i] = 0;
            matrix[i][index] = 0;
        }
        for(int i = index; i < vertexList.length - 1; i++) {
            for(int j = 0; j < vertexList.length; j++) {
                matrix[i][j] = matrix[i + 1][j];
            }
        }
        for(int i = index; i < vertexList.length - 1; i++) {
            for(int j = 0; j < vertexList.length; j++) {
                matrix[j][i] = matrix[j][i + 1];
            }
        }
        for(int i = 0; i < vertexList.length; i++) {
            matrix[vertexList.length - 1][i] = 0;
            matrix[i][vertexList.length - 1] = 0;
        }
        vertexList = new int[vertexList.length - 1];
        for (int i = 0; i < vertexList.length; i++) {
            vertexList[i] = i;
        }
    }
}