/**
 * MinHeap is used for optimizing the lookup for the frontier node with the least distance from MST
 */
public class MinHeap {
    private HeapNode[] minHeap; // heap represented in an array
    private int size;   // heap size

    /**
     * Construct a MinHeap from graph
     */
    public MinHeap(Graph graph, int start) {
        minHeap = new HeapNode[graph.getSize()];    // create the array with max length
        this.size = 0;  // Initialize the heap size
        for (int i = 0; i < graph.getSize(); i++) {
            insert(new HeapNode(i, graph.getDistance(i, start), start));  // insert all vertices in the graph into minHeap
        }
    }

    /**
     * Insert a new node into MinHeap
     */
    public void insert(HeapNode heapNode) {
        int index = size;
        minHeap[index] = heapNode;    // put the new node into the last position
        siftUp(index);  // do sift up adjustment
        size++;
    }

    /**
     * Delete the min node of the MinHeap
     *
     * @return the removed min node
     */
    public HeapNode deleteMin() {
        if(size==0) return null;
        HeapNode minNode = minHeap[0];
        int index = 0;
        HeapNode lastNode = minHeap[--size];
        double lastDistance = lastNode.getDistance();    // move last node to heap top

        // sift down
        for (int child = index * 2 + 1; child <= size; ) {  // compare lastDistance with children (left child first)
            if (child < size - 1 && minHeap[child].getDistance() > minHeap[child + 1].getDistance()) { // if right child exists and right child is smaller than left child
                child++;    // compare with right child
            }
            if (lastDistance < minHeap[child].getDistance()) {
                break;  // if lastDistance is smaller than the smaller one of the two children, stop sifting down
            } else {
                minHeap[index] = minHeap[child];  // move child upward
                index = child;
                child = child * 2 + 1;
            }
        }
        minHeap[index] = lastNode;
        return minNode;
    }

    /**
     * Sift up adjustment
     */
    private void siftUp(int index) {
        HeapNode heapNode = minHeap[index];
        // if distance is smaller than parent
        while (index != 0 && minHeap[index].getDistance() < minHeap[(index - 1) / 2].getDistance()) {
            minHeap[index] = minHeap[index / 2];    // move parent node to current position
            index /= 2;
        }
        minHeap[index] = heapNode; // put the heapNode into the correct place
    }

    /**
     * Insert a new node into MinHeap
     */
    public void updateDistance(HeapNode minNode, Graph graph) {
        for (int i = 0; i < size; i++) {
            HeapNode node = minHeap[i];   // for each vertex that hasn't been in the MST
            double newDistance = graph.getDistance(node.getIndex(), minNode.getIndex());  // get its distance from minNode (the newly-added-into-MST vertex)
            // if a node's distance to the minNode is smaller than its current distance to mst, update it
            if (newDistance < node.getDistance()) {
                node.setDistance(newDistance);  // update distance to MST
                node.setParent(minNode.getIndex()); // update where this distance comes from
                siftUp(i);  // since the distance is updated to be smaller, we need to adjust the heap by sifting up
            }
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public HeapNode[] getMinHeap() {
        return minHeap;
    }

    public void setMinHeap(HeapNode[] minHeap) {
        this.minHeap = minHeap;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


}
