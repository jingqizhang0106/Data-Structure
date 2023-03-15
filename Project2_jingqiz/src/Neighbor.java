/**
 * This class contains a distance field and a pointer into the 2d tree.
 * It also keeps track of number of nodes visited in the 2d tree.
 *
 * @author Mia Zhang
 */
public class Neighbor {
    public double distance;
    public TreeNode nearestNeighbor;
    public int count_visited_nodes;

    public Neighbor() {
        nearestNeighbor = null;
        distance = Double.MAX_VALUE;
        count_visited_nodes = 0;
    }

}
