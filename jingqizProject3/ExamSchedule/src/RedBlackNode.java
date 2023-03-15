/**
 * The RedBlackNode class is used for constructing RedBlackTree.
 *
 * @Author Mia Zhang
 * @AndrewID jingqiz
 */
public class RedBlackNode {
    public static final int BLACK = 0;
    public static final int RED = 1;

    private int id;
    private String course;
    private int color;
    private RedBlackNode p;
    private RedBlackNode lc;
    private RedBlackNode rc;

    RedBlackNode() {
        this.id = -1;
        this.course = "NIL";
        this.color = BLACK;
        this.p = this;
        this.lc = this;
        this.rc = this;
    }

    public int getId() {
        return this.id;
    }

    public String getCourse() {
        return this.course;
    }

    public int getColor() {
        return color;
    }

    public RedBlackNode getP() {
        return p;
    }

    public RedBlackNode getLc() {
        return lc;
    }

    public RedBlackNode getRc() {
        return rc;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setP(RedBlackNode p) {
        this.p = p;
    }

    public void setLc(RedBlackNode lc) {
        this.lc = lc;
    }

    public void setRc(RedBlackNode rc) {
        this.rc = rc;
    }

}
