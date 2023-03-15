/**
 * This is a tree node class which is used to construct the 2D tree.
 * Each node contains a X-Y coordinate, left child, right child, and crime data.
 *
 * @author Mia Zhang
 */
public class TreeNode {

    public XYCoordinate xyCoordinate;

    public TreeNode left;

    public TreeNode right;

    public String crimeRecord;

    public TreeNode(double x, double y, String crimeRecord) {
        this.xyCoordinate = new XYCoordinate(x, y);
        this.crimeRecord = crimeRecord;
    }

    @Override
    public String toString() {
        return crimeRecord;
    }
}

