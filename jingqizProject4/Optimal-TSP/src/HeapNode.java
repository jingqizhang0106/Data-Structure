/**
 * HeapNode Data Structure for representing each heap node in a MinHeap
 */
public class HeapNode {
    private int index;  // vertex index
    private double distance;    // current minimum distance from the MST
    private int parent; // the previous vertex in MST

    /**
     * Construct a HeapNode
     */
    public HeapNode(int index, double distance, int parent) {
        this.index = index;
        this.distance = distance;
        this.parent = parent;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

}
