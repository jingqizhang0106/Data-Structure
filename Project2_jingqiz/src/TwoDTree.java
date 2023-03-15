import java.io.*;
import java.util.*;

/**
 * This is the implementation of 2D tree which is used to store the crime records.
 * Each crime record is stored per node in the tree.
 *
 * @author Mia Zhang
 */
public class TwoDTree {
    public int count_node;
    private TreeNode root;
    private Comparator<TreeNode> comparatorX = (p1, p2) -> Double.compare(p1.xyCoordinate.x, p2.xyCoordinate.x);
    private Comparator<TreeNode> comparatorY = (p1, p2) -> Double.compare(p1.xyCoordinate.y, p2.xyCoordinate.y);

    /**
     * pre-condition: The String crimeDataLocation contains the path to
     * a file formatted in the exact same way as CrimeLatLonXY.csv
     * <p>
     * post-condition: The 2d tree is constructed and may be printed or queried.
     */
    public TwoDTree(String crimeDataLocation) {
        List<TreeNode> nodeList = generateList(crimeDataLocation);
        count_node = nodeList.size();
        root = buildTree(nodeList, true);
    }

    /**
     * Recursively build the 2D tree by divide and conquer.
     * Choosing the node in the middle of the sorted list as root would make the tree balanced, thus more efficient.
     */
    TreeNode buildTree(List<TreeNode> nodeList, boolean compareX) {
        if (nodeList == null || nodeList.isEmpty()) return null;
        Collections.sort(nodeList, compareX ? comparatorX : comparatorY);
        int mid = nodeList.size() >> 1;
        TreeNode midNode = nodeList.get(mid);
        midNode.left = buildTree(nodeList.subList(0, mid), !compareX);
        midNode.right = buildTree(nodeList.subList(mid + 1, nodeList.size()), !compareX);
        return midNode;
    }

    /**
     * Read from the file and convert the lines into a node list.
     */
    public List<TreeNode> generateList(String fileName) {
        List<TreeNode> list = new ArrayList<>();
        try {
            File file = new File(fileName);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineText = null;
                bufferedReader.readLine();
                while ((lineText = bufferedReader.readLine()) != null && lineText.indexOf(',') != -1) {
                    double x = Double.valueOf(lineText.split(",")[0]);
                    double y = Double.valueOf(lineText.split(",")[1]);
                    TreeNode treeNode = new TreeNode(x, y, lineText);
                    list.add(treeNode);
                }
            } else {
                System.out.println("File doesn't exist.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * pre-condition: The 2d tree has been constructed.
     * post-condition: The 2d tree is displayed with a pre-order traversal.
     */
    public void preOrderPrint() {
        preOrderPrint(root);
    }

    public void preOrderPrint(TreeNode root) {
        if (root == null)
            return;
        System.out.println(root.crimeRecord);
        preOrderPrint(root.left);
        preOrderPrint(root.right);
    }

    /**
     * pre-condition: The 2d tree has been constructed.
     * post-condition: The 2d tree is displayed with an in-order traversal.
     * <p>
     * Complexity: θ(n)
     */
    public void inOrderPrint() {
        inOrderPrint(root);
    }

    public void inOrderPrint(TreeNode root) {
        if (root == null)
            return;
        inOrderPrint(root.left);
        System.out.println(root.crimeRecord);
        inOrderPrint(root.right);
    }

    /**
     * pre-condition: The 2d tree has been constructed.
     * post-condition: The 2d tree is displayed with a post-order traversal.
     */
    public void postOrderPrint() {
        postOrderPrint(root);
    }

    public void postOrderPrint(TreeNode root) {
        if (root == null)
            return;
        postOrderPrint(root.left);
        postOrderPrint(root.right);
        System.out.println(root.crimeRecord);
    }

    /**
     * pre-condition: The 2d tree has been constructed.
     * post-condition: The 2d tree is displayed with a level-order traversal.
     * <p>
     * Complexity: θ(n)
     */
    public void levelOrderPrint() {
        if (root == null) return;
        Queue queue = new Queue();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            for (int i = 0; i < queue.size(); i++) {
                TreeNode node = (TreeNode) queue.dequeue();
                if (node == null) {
                    continue;
                }
                System.out.println(node.crimeRecord);
                if (node.left != null) queue.enqueue(node.left);
                if (node.right != null) queue.enqueue(node.right);
            }
        }
    }

    /**
     * pre-condition: The 2d tree has been constructed.
     * post-condition: The 2d tree is displayed with a reverse levelorder traversal.
     * <p>
     * Complexity: θ(n) because we only need to traverse all nodes for one time,
     * and the in/out operation of Queue and Stack are both θ(1)
     */
    public void reverseLevelOrderPrint() {
        if (root == null) return;
        Queue queue = new Queue();
        queue.enqueue(root);
        Stack<TreeNode> resultList = new Stack<>();
        while (!queue.isEmpty()) {
            for (int i = 0; i < queue.size(); i++) {
                TreeNode node = (TreeNode) queue.dequeue();
                if (node == null) {
                    continue;
                }
                resultList.push(node);
                if (node.left != null) queue.enqueue(node.left);
                if (node.right != null) queue.enqueue(node.right);
            }
        }
        while (!resultList.isEmpty()) {
            System.out.println(resultList.poll().crimeRecord);
        }
    }

    /**
     * pre-condition: The 2d tree has been constructed.
     * <p>
     * post-condition: A list of 0 or more crimes is returned. These crimes occurred within the rectangular range
     * specified by the four parameters. The pair (x1, y1) is the left bottom of the rectangle.
     * The pair (x2, y2) is the top right of the rectangle.
     */
    public ListOfCrimes findPointsInRange(double x1, double y1, double x2, double y2) {
        ListOfCrimes resultList = new ListOfCrimes();
        findPointsInRange(x1, y1, x2, y2, resultList, root, true);
        return resultList;
    }

    public void findPointsInRange(double x1, double y1, double x2, double y2, ListOfCrimes resultList, TreeNode node, boolean compareX) {
        if (node == null) return;
        resultList.count_visited_nodes++;

        // if the point is in the range, add it to resultList
        if (Double.compare(node.xyCoordinate.x, x1) >= 0 && Double.compare(node.xyCoordinate.y, y1) >= 0
                && Double.compare(node.xyCoordinate.x, x2) <= 0 && Double.compare(node.xyCoordinate.y, y2) <= 0)
            resultList.addCrime(node.crimeRecord);

        if (Double.compare(compareX ? node.xyCoordinate.x : node.xyCoordinate.y, compareX ? x1 : y1) < 0)
            // current point is to the left/bottom of the range, then search right children (with larger coordinates)
            findPointsInRange(x1, y1, x2, y2, resultList, node.right, !compareX);
        else if (Double.compare(compareX ? node.xyCoordinate.x : node.xyCoordinate.y, compareX ? x2 : y2) > 0)
            // current point is to the right/upper of the range, then search left children (with smaller coordinates)
            findPointsInRange(x1, y1, x2, y2, resultList, node.left, !compareX);
        else { // current point dividing line crosses the rectangle region, so search both sides
            findPointsInRange(x1, y1, x2, y2, resultList, node.left, !compareX);
            findPointsInRange(x1, y1, x2, y2, resultList, node.right, !compareX);
        }
    }

    /**
     * pre-condition: the 2d tree has been constructed.
     * The (x1,y1) pair represents a point in space near Pittsburgh and
     * in the state plane coordinate system.
     * <p>
     * post-condition: the distance in feet to the nearest node is
     * returned in Neighbor. In addition, the Neighbor object contains a
     * reference to the nearest neighbor in the tree.
     */
    public Neighbor nearestNeighbor(double x1, double y1) {
        Neighbor neighbor = new Neighbor();
        XYCoordinate coord = new XYCoordinate(x1, y1);
        nearestNeighbor(coord, root, neighbor, true);
        return neighbor;
    }

    public void nearestNeighbor(XYCoordinate coord, TreeNode node, Neighbor neighbor, boolean compareX) {
        if (node == null) return;
        neighbor.count_visited_nodes++;

        // calculate the distance between current visited point and the target point
        double currentDistance = Math.sqrt(Math.pow((coord.x - node.xyCoordinate.x), 2) + Math.pow((coord.y - node.xyCoordinate.y), 2));
        // if current distance is less than the minimum record, then update this point as the new nearest neighbor
        if (currentDistance <= neighbor.distance) {
            neighbor.distance = currentDistance;
            neighbor.nearestNeighbor = node;
        }

        double node_XY = compareX ? node.xyCoordinate.x : node.xyCoordinate.y;
        double coord_XY = compareX ? coord.x : coord.y;

        // Imagine a circle with the target point as center, minimum distance as radius.
        // If current point forms a dividing line which does not intersect with the circle,
        // then it means we can neglect the further half from the target point.
        if (Math.abs(node_XY - coord_XY) > neighbor.distance) {
            if (node_XY > coord_XY) // current point is to the right/top of target point, then just search left child
                nearestNeighbor(coord, node.left, neighbor, !compareX);
            else // current point is to the left/bottom of target point, then just search right child
                nearestNeighbor(coord, node.right, neighbor, !compareX);
        } else { // do not prune, search both sides
            nearestNeighbor(coord, node.left, neighbor, !compareX);
            nearestNeighbor(coord, node.right, neighbor, !compareX);
        }
    }

}
