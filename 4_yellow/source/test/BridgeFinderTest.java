package source;

import java.awt.*;
import java.util.Vector;

import static org.junit.Assert.*;

public class BridgeFinderTest {

    @org.junit.Test
    public void FindBridgeNotNull() {
        BridgeFinder Bridge = new BridgeFinder();
        Vector<Point> bridges = new Vector();
        bridges.add(new Point(2, 3));
        bridges.add(new Point(1, 2));
        int[][] matrix = {{0, 1, 0}, {1, 0, 1}, {0, 1, 0}};
        Object[] a = Bridge.FindBridgeT(matrix, 3);
        assertArrayEquals(bridges.toArray(), a);
    }

    @org.junit.Test
    public void FindBridgeNull() {
        BridgeFinder Bridge = new BridgeFinder();
        Vector<Point> bridges = new Vector();
        bridges.add(new Point(0, 0));
        int[][] matrix = new int[0][0];
        Object[] a = Bridge.FindBridgeT(matrix, 0);
        assertArrayEquals(bridges.toArray(), a);
    }

    @org.junit.Test
    public void FindBridgeNegative() {
        BridgeFinder Bridge = new BridgeFinder();
        Vector<Point> bridges = new Vector();
        int i = -5;
        bridges.add(new Point(i, i));
        int[][] matrix = new int[0][0];
        Object[] a = Bridge.FindBridgeT(matrix, i);
        assertArrayEquals(bridges.toArray(), a);
    }


    @org.junit.Test
    public void FindBridge100() {
        BridgeFinder Bridge = new BridgeFinder();
        Vector<Point> bridges = new Vector();
        bridges.add(new Point(1, 100));
        int[][] matrix = new int[100][100];
        for(int i = 0; i < 99; i++) {
            for (int j = 0; j < 99; j++) {
                matrix[i][j] = 1;
            }
        }
            matrix[0][99] = 1;
            for(int k = 1; k < 99; k++){
                matrix[k][99] = 0;
            }
            matrix[99][0] = 1;
        for(int k = 1; k < 100; k++){
            matrix[99][k] = 0;
        }


        Object[] a = Bridge.FindBridgeT(matrix, 100);
        assertArrayEquals(bridges.toArray(), a);
    }

    @org.junit.Test
    public void FindBridgeMoreThan100() {
        BridgeFinder Bridge = new BridgeFinder();
        Vector<Point> bridges = new Vector();
        int i = 110;
        bridges.add(new Point(i, i));
        int[][] matrix = new int[0][0];
        Object[] a = Bridge.FindBridgeT(matrix, i);
        assertArrayEquals(bridges.toArray(), a);
    }
}