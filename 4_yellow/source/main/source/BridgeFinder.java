package source;

import java.awt.Point;
import java.util.Vector;
import javax.swing.JFrame;
//import gui.Window;


public class BridgeFinder extends Graph {
    private boolean[] used;
    private int timer;
    private int[] tin;
    private int[] fup;
    private int q = -1;

    public BridgeFinder() {
        this.used = new boolean[this.vertexList.length];
        this.tin = new int[this.vertexList.length];
        this.fup = new int[this.vertexList.length];
    }

    public BridgeFinder(int[][] matrix) {
        if(matrix.length != 0) {
            this.bridges = new Vector();
            this.matrix = matrix;
            this.vertexList = new int[matrix[0].length];

            for (int i = 0; i < this.vertexList.length; this.vertexList[i] = i++) {
            }

            this.used = new boolean[this.vertexList.length];
            this.tin = new int[this.vertexList.length];
            this.fup = new int[this.vertexList.length];
        }
    }

    public void startFind() {
        this.timer = 0;

        int i;
        for(i = 0; i < this.vertexList.length; ++i) {
            this.used[i] = false;
        }

        for(i = 0; i < this.vertexList.length; ++i) {
            if (!this.used[i]) {
                this.DFS(i, q);
            }
        }

    }

    private void DFS(int v, int p) {
        this.used[v] = true;
        this.tin[v] = this.fup[v] = this.timer++;
        for(int i = 0; i < this.matrix[v].length; ++i) {
            if (this.matrix[v][i] != 0 && i != p) {
                if (this.used[i]) {
                    this.fup[v] = Math.min(this.fup[v], this.tin[i]);
                } else {
                    this.DFS(i, v);
                    this.fup[v] = Math.min(this.fup[v], this.fup[i]);
                    if (this.fup[i] > this.tin[v]) {
                        this.Bridge = true;
                        this.bridges.add(new Point(v + 1, i + 1));
                    }
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

}
