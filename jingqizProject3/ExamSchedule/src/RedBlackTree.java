/**
 * The RedBlackTree class offers methods to construct a RedBlackTree, insert new element, and look up data.
 *
 * @Author Mia Zhang
 * @AndrewID jingqiz
 */

public class RedBlackTree {
    private static final int BLACK = 0;
    private static final int RED = 1;
    private static final RedBlackNode NIL = new RedBlackNode();
    private RedBlackNode root;
    private int size;


    public RedBlackTree() {
        root = NIL;
        root.setColor(0);
        size = 0;
    }

    public int getSize() {
        return size;
    }

    /**
     * returns true if the String course is found in the RedBlackTree and false otherwise.
     */
    public boolean contains(String course) {
        RedBlackNode curr = root;
        while (curr != NIL) {
            if (curr.getCourse().compareTo(course) == 0) return true;
            else if (curr.getCourse().compareTo(course) > 0) curr = curr.getLc();
            else curr = curr.getRc();
        }
        return false;
    }

    /**
     * look up course name and returns the corresponding course id; or returns -1 if course is not found
     */
    public int getCourseId(String course) {
        RedBlackNode curr = root;
        while (curr != NIL) {
            if (curr.getCourse().compareTo(course) == 0) return curr.getId();
            else if (curr.getCourse().compareTo(course) > 0) curr = curr.getLc();
            else curr = curr.getRc();
        }
        return -1;
    }

    /**
     * insert a new node with course name and a new id
     */
    public void insert(String course) {
        RedBlackNode curr = root, y = NIL, z = new RedBlackNode();
        while (curr != NIL) {
            y = curr;   // record the parent
            if (course.compareTo(curr.getCourse()) < 0) curr = curr.getLc();
            else
                curr = curr.getRc();
        }
        z.setP(y);  // set new node's parent
        if (y == NIL)
            root = z;
        else {
            if (course.compareTo(y.getCourse()) < 0)
                y.setLc(z); // set as parent's child
            else
                y.setRc(z);
        }
        z.setId(size++);
        z.setCourse(course);
        z.setLc(NIL);
        z.setRc(NIL);
        z.setColor(RED);

        RBInsertFixup(z);   // do the fix up
    }

    /**
     * fixing up the tree so that Red Black Properties are preserved
     */
    private void RBInsertFixup(RedBlackNode z) {
        while (z.getP().getColor() == RED) {
            RedBlackNode grandparent = z.getP().getP();
            RedBlackNode uncle;
            if (grandparent.getLc() == z.getP()) { // uncle is right child
                uncle = grandparent.getRc();
                if (uncle.getColor() == RED) {
                    z.getP().setColor(BLACK);
                    uncle.setColor(BLACK);
                    grandparent.setColor(RED);
                    z = grandparent;
                } else {  // if uncle is black
                    if (z == z.getP().getRc()) {    // if zig zag (LR), make it a zig zig (LL)
                        z = z.getP();
                        leftRotate(z);
                    }
                    z.getP().setColor(BLACK);
                    z.getP().getP().setColor(RED);
                    rightRotate(z.getP().getP());
                }
            } else { // uncle is left child
                uncle = grandparent.getLc();
                if (uncle.getColor() == RED) {
                    z.getP().setColor(BLACK);
                    uncle.setColor(BLACK);
                    grandparent.setColor(RED);
                    z = grandparent;
                } else {  // if uncle is black
                    if (z == z.getP().getLc()) {    // if zig zag (RL), make it a zig zig (RR)
                        z = z.getP();
                        rightRotate(z);
                    }
                    z.getP().setColor(BLACK);
                    z.getP().getP().setColor(RED);
                    leftRotate(z.getP().getP());
                }
            }
        }
        root.setColor(BLACK);
    }

    /**
     * performs a single left rotation.
     *
     * @param x the node around which to perform rotation
     */
    private void leftRotate(RedBlackNode x) {
        RedBlackNode y = x.getRc();
        x.setRc(y.getLc());
        y.getLc().setP(x);
        y.setP(x.getP());
        if (x.getP() == NIL) {  // if x is at root then y becomes new root
            root = y;
        } else {
            if (x == x.getP().getLc()) x.getP().setLc(y);   // if x is left child
            else x.getP().setRc(y); // if x is right child
        }
        y.setLc(x);
        x.setP(y);
    }

    /**
     * performs a single right rotation.
     *
     * @param x the node around which to perform rotation
     */
    public void rightRotate(RedBlackNode x) {
        RedBlackNode y = x.getLc();
        x.setLc(y.getRc());
        y.getRc().setP(x);
        y.setP(x.getP());
        if (x.getP() == NIL) {  // if x is at root then y becomes new root
            root = y;
        } else {
            if (x == x.getP().getLc()) x.getP().setLc(y);   // if x is left child
            else x.getP().setRc(y); // if x is right child
        }
        y.setRc(x);
        x.setP(y);
    }


    public void inOrderTraversal() {
        inOrderTraversal(root);
    }

    private void inOrderTraversal(RedBlackNode curr) {
        if (curr == NIL) return;
        if (curr.getLc() != NIL) {
            inOrderTraversal(curr.getLc());
        }
        System.out.println(curr.getId() + " -> " + curr.getCourse());
        if (curr.getRc() != NIL) {
            inOrderTraversal(curr.getRc());
        }
    }

}
