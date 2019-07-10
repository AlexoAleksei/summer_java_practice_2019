package source;

import org.junit.Test;

import java.awt.*;
import java.util.Vector;

import static org.junit.Assert.*;

public class GraphTest {

    @Test
    public void getVertexListTest1() {
        int[][] matrix = {{0, 1, 0}, {1, 0, 1}, {0, 1, 0}};
        int [] a = new int[] {0, 1, 2};
        BridgeFinder n = new BridgeFinder(matrix);
        assertArrayEquals(a, n.getVertexList());
    }

    @Test
    public void getVertexListTest2() {
        int[][] matrix = new int[0][0];
        int [] a = new int[0];
        BridgeFinder n = new BridgeFinder(matrix);
        assertArrayEquals(a, n.getVertexList());
    }

    @Test
    public void getEdgeAmountTest1() {
        int[][] matrix = new int[0][0];
        BridgeFinder n = new BridgeFinder(matrix);
        assertEquals(0, n.getEdgeAmount());
    }

    @Test
    public void getEdgeAmountTest2() {
        int[][] matrix = {{0, 1, 0}, {1, 0, 1}, {0, 1, 0}};
        BridgeFinder n = new BridgeFinder(matrix);
        n.getEdges();
        assertEquals(2, n.getEdgeAmount());
    }

    @Test
    public void getMatrixTest1() {
        int[][] matrix = new int[100][100];
        matrix[0][0] = 0;
        matrix[0][1] = 1;
        matrix[0][2] = 0;
        matrix[1][0] = 1;
        matrix[1][1] = 0;
        matrix[1][2] = 1;
        matrix[2][0] = 0;
        matrix[2][1] = 1;
        matrix[2][2] = 0;
        BridgeFinder n = new BridgeFinder(matrix);
        assertArrayEquals(matrix, n.getMatrix());
    }

    @Test
    public void getMatrixTest2() {
        int[][] matrix = new int[0][0];
        int[][] matrix1 = new int[100][100];
        BridgeFinder n = new BridgeFinder(matrix);
        assertArrayEquals(matrix1, n.getMatrix());
    }

    @Test
    public void getEdgesTest1() {
        int[][] matrix = new int[0][0];
        int[][] matrix1 = new int[100][100];
        BridgeFinder n = new BridgeFinder(matrix);
        Point [] bridges = new Point[0];
        Object[] k = n.getEdges();
       // for(int i = 0; i < 4950; i++) {
       //     bridges[i] = new Point();
       // }
        assertArrayEquals(bridges,k);

    }

    @Test
    public void getEdgesTest2() {
        int[][] matrix = {{0, 1, 0}, {1, 0, 1}, {0, 1, 0}};
        BridgeFinder n = new BridgeFinder(matrix);
        Point [] bridges = new Point[6];
        for(int i = 0; i < 3; i++) {
            bridges[i] = new Point();
        }
        bridges[0].x = 0;
        bridges[0].y = 1;
        bridges[1].x = 1;
        bridges[1].y = 2;

        Object[] k = n.getEdges();
        assertArrayEquals(bridges,k);

    }


    @Test
    public void getBridgesTest1() {
        int[][] matrix = {{0, 1, 0}, {1, 0, 1}, {0, 1, 0}};
        BridgeFinder n = new BridgeFinder(matrix);
        Vector<Point> bridges = new Vector();
        bridges.add(new Point(2, 3));
        bridges.add(new Point(1, 2));
        assertArrayEquals(bridges.toArray(), n.FindBridgeT(matrix, 3));
    }

    @Test
    public void getBridgesTest2() {
        int[][] matrix = new int[0][0];
        BridgeFinder n = new BridgeFinder(matrix);
        Vector<Point> bridges = new Vector();
        bridges.add(new Point(0, 0));

        assertArrayEquals(bridges.toArray(), n.FindBridgeT(matrix, 0));
    }
}