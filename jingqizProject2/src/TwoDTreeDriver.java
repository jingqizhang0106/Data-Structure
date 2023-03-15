import java.util.Scanner;

/**
 * This is the main driver class. When the main method is run, it will load the crime
 * data file into the 2d tree and interact with the user.
 * User input validation is omitted, assuming the user always enters the data correctly.
 *
 * @author Mia Zhang
 */
public class TwoDTreeDriver {
    public static void main(String[] args) {
        TwoDTree tree = new TwoDTree("CrimeLatLonXY.csv");
        System.out.println("Crime file loaded into 2d tree with " + tree.count_node + " records.");
        while (true) {
            System.out.println("\nWhat would you like to do?\n"
                    + "1: inorder\n"
                    + "2: preorder\n"
                    + "3: levelorder\n"
                    + "4: postorder\n"
                    + "5: reverseLevelOrder\n"
                    + "6: search for points within rectangle\n"
                    + "7: search for nearest neighbor\n"
                    + "8: quit");

            Scanner sc = new Scanner(System.in);
            int user_choice = sc.nextInt();
            switch (user_choice) {
                case 1:
                    tree.inOrderPrint();
                    break;
                case 2:
                    tree.preOrderPrint();
                    break;
                case 3:
                    tree.levelOrderPrint();
                    break;
                case 4:
                    tree.postOrderPrint();
                    break;
                case 5:
                    tree.reverseLevelOrderPrint();
                    break;
                case 6:
                    System.out.println("Enter a rectangle bottom left (X1,Y1) and top right (X2, Y2) as four doubles each separated by a space.");
                    double x1 = sc.nextDouble();
                    double y1 = sc.nextDouble();
                    double x2 = sc.nextDouble();
                    double y2 = sc.nextDouble();
                    System.out.println("Searching for points within (" + x1 + "," + y1 + ") and (" + x2 + "," + y2 + ")\n");
                    ListOfCrimes resultList = tree.findPointsInRange(x1, y1, x2, y2);
                    System.out.println("Examined " + resultList.count_visited_nodes + " nodes during search.");
                    System.out.println("Found " + resultList.count_crimes + " crimes.\n");
                    System.out.println(resultList);
                    resultList.toKML();
                    break;
                case 7:
                    System.out.println("Enter a point to find nearest crime. Separate with a space.");
                    double target_X = sc.nextDouble();
                    double target_Y = sc.nextDouble();
                    Neighbor neighbor = tree.nearestNeighbor(target_X, target_Y);
                    System.out.println("Looked at " + neighbor.count_visited_nodes + " nodes in tree. Found the nearest crime at:");
                    System.out.println(neighbor.nearestNeighbor);
                    break;
            }
            if (user_choice == 8) {
                System.out.println("Thank you for exploring Pittsburgh crimes in the 1990’s.");
                break;
            }
        }
    }
}
