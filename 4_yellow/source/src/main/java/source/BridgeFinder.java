package source;

import java.awt.*;
import java.util.Vector;

public class BridgeFinder extends Graph {
    private boolean[] used;
    private int timer;
    private int[] tin;
    private int[] fup;
    private int q = -1;
    private Vector<Condition> conditionList;
    private int lastVisitedVertex;

    public BridgeFinder(){
        used = new boolean[vertexList.length];
        tin = new int[vertexList.length];
        fup = new int[vertexList.length];
        conditionList = new Vector<Condition>();
    }

    public BridgeFinder(int[][] matrix){
        if(matrix.length != 0) {
            bridges = new Vector<Point>();
            setMatrixValues(matrix);
            vertexList = new int[matrix[0].length];
            for (int i = 0; i < vertexList.length; i++) {
                vertexList[i] = i;
            }

            used = new boolean[vertexList.length];
            tin = new int[vertexList.length];
            fup = new int[vertexList.length];
        }
        conditionList = new Vector<Condition>();
    }

    //метод добавления вершины к vertexList
    public void addNewVertex(){
        vertexList = new int[vertexList.length + 1];
        for (int i = 0; i < vertexList.length; i++) {
            vertexList[i] = i;
        }
        used = new boolean[vertexList.length];
        tin = new int[vertexList.length];
        fup = new int[vertexList.length];
    }

    //инициализирует ребра в основной матрице 100х100 по переданной
    private void setMatrixValues(int[][] newMatrix){
        for(int i=0; i < newMatrix.length; i++){
            for(int k=0; k < newMatrix[i].length; k++){
                this.matrix[i][k] = newMatrix[i][k];
            }
        }
    }

    //добавление ребра
    public void addNewEdge(int startV, int endV){
        matrix[startV][endV] = 1;
        matrix[endV][startV] = 1;
        edgeAmount++;
    }

    //удаление ребра
    public void deleteEdge(int startV, int endV){
        matrix[startV][endV] = 0;
        matrix[endV][startV] = 0;
        if(edgeAmount > 0)
            edgeAmount--;
    }

    public void startFind(){
        conditionList.add(new Condition(new Vector<Point>(bridges), makeNewUsed(used), -1)); //дефолтное состояние для возврата к редактированию графа
        timer = 0;
        for(int i = 0; i < vertexList.length; i++){
            used[i] = false;
        }
        for(int i=0; i < vertexList.length; i++) {
            if (!used[i]) {
                DFS(i, q);
                q = -1;
            }
        }
        used[lastVisitedVertex] = true;
        conditionList.add(new Condition(new Vector<Point>(bridges), makeNewUsed(used), -1));
    }

    private void DFS(int v, int p){
        conditionList.add(new Condition(new Vector<Point>(bridges), makeNewUsed(used), v));
        lastVisitedVertex = v;
        used[v] = true;
        tin[v] = fup[v] = timer++;
        for(int i=0; i < matrix[v].length; ++i){
            if(matrix[v][i] == 0 || i == p){
                continue;
            }
            if(used[i]){
                fup[v] = Math.min(fup[v], tin[i]);
            }
            else {
                DFS(i, v);
                fup[v] = Math.min(fup[v], fup[i]);
                if(fup[i] > tin[v]){
                    bridges.add(new Point(v+1, i+1));
                }
            }
        }
    }

    public Object[] FindBridgeT(int[][] matrix, int vertex){
        if(vertex <= 0 || vertex > 100){
            BridgeFinder BridgeT = new BridgeFinder(matrix);
            BridgeT.bridges.add(new Point(vertex, vertex));
            return BridgeT.bridges.toArray();
        }
        BridgeFinder BridgeT = new BridgeFinder(matrix);
        BridgeT.startFind();
        return BridgeT.bridges.toArray();

    }

    public Vector<Condition> getConditionList(){ return conditionList; }

    private boolean[] makeNewUsed(boolean[] used){
        boolean[] newUsed = new boolean[used.length];
        for(int i=0; i< used.length; i++){
            newUsed[i] = used[i];
        }
        return newUsed;
    }
}