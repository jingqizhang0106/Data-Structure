import java.util.LinkedList;

/**
 * Graph Data Structure using 1-D array for vertices and 2-D array for weighted adjacent matrix
 */
public class Graph {
    private Vertex[] vertices;
    private double[][] distance_matrix;
    private int size;

    /**
     * Generate a graph from the crime record list
     */
    public Graph(LinkedList<String> crimeRecords) {
        size = crimeRecords.size();
        vertices = new Vertex[size];
        distance_matrix = new double[size][size];
        for (int i = 0; i < size; i++) {    // for each line of crime record
            vertices[i] = new Vertex(crimeRecords.get(i));    // create a vertex
            for (int j = 0; j < i; j++) {   // calculate the distance to all previous vertices
                double distanceX = vertices[i].getX_coordinate() - vertices[j].getX_coordinate();
                double distanceY = vertices[i].getY_coordinate() - vertices[j].getY_coordinate();
                double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY) * 0.00018939;    // convert from feet to miles
                distance_matrix[i][j] = distance;   // update the adjacent matrix
                distance_matrix[j][i] = distance;
            }
        }
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public double[][] getDistance_matrix() {
        return distance_matrix;
    }

    public int getSize() {
        return size;
    }

    /**
     * @return the distance from vertex i to vertex j
     */
    public double getDistance(int i, int j) {
        return distance_matrix[i][j];
    }

}
