/**
 * The Graph class creates adjacency matrix and records colored vertices.
 *
 * @Author Mia Zhang
 * @AndrewID jingqiz
 */
public class Graph {
    private final int MAX = 20;
    private boolean[] coloredVertices;
    private int[][] adjMatrix;
    private int size;

    public Graph() {
        coloredVertices = new boolean[MAX];
        adjMatrix = new int[MAX][MAX];
        size = 0;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /**
     * set two vertices as connected
     */
    public void setAdj(int x, int y) {
        adjMatrix[x][y] = 1;
        adjMatrix[y][x] = 1;
    }

    /**
     * returns true if two vertices are connected
     */
    public int getAdj(int x, int y) {
        return adjMatrix[x][y];
    }

    /**
     * mark vertex v as colored
     */
    public void setColored(int v) {
        coloredVertices[v] = true;
    }

    /**
     * returns true if all vertices are colored
     */
    public boolean allColored() {
        boolean allColored = true;
        for (int i = 0; i < size; i++) {
            if (!coloredVertices[i]) allColored = false;
        }
        return allColored;
    }

    /**
     * returns the index of the next uncolored vertex from the start index; or -1 if not found
     */
    public int getNextUncoloredVertex(int start) {
        for (int i = start; i < size; i++) {
            if (!coloredVertices[i]) {
                return i;
            }
        }
        return -1;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                s += adjMatrix[i][j];
            }
            s += "\n";
        }
        return s;
    }
}
