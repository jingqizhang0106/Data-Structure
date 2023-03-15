import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Find a approximate solution for TSP
 */
public class ApproxTSP {
    static LinkedList<Integer> hamiltonianCycle;
    static int start = 0; //  According to Project 4 requirement: the root of the minimum spanning tree will be the first index

    /**
     * Prim algorithm to generate a MST using minHeap for optimization
     *
     * @param graph the graph for generating the MST
     * @return MST: an array of linkedList representing the children of each tree node
     */
    public static LinkedList<Integer>[] prim(Graph graph) {
        MinHeap minHeap = new MinHeap(graph, start); // generate a minHeap that contains all vertices in graph, comparing with distances from the start vertex
        LinkedList<Integer>[] mst = new LinkedList[graph.getSize()];
        // initialize the MST
        for (int i = 0; i < graph.getSize(); i++) {
            mst[i] = new LinkedList<>();
        }
        minHeap.deleteMin();  // initialize MST with the start point
        while (!minHeap.isEmpty()) {
            HeapNode minNode = minHeap.deleteMin();   // select the node with min distance to the MST
            mst[minNode.getParent()].add(minNode.getIndex());   // record where the node comes from
            minHeap.updateDistance(minNode, graph); // update the minimum distances of the current frontier edges
        }
        return mst;
    }

    /**
     * Get a Hamiltonian Cycle from the graph by using Prim-MST algorithm
     *
     * @param graph the graph for generating the Hamiltonian Cycle
     * @return Hamiltonian Cycle: a linkedList of the vertices index
     */
    public static LinkedList<Integer> getHamiltonianCycle(Graph graph) {
        LinkedList<Integer>[] mst = prim(graph);    // generate a MST
        hamiltonianCycle = new LinkedList<>();
        preOrderTraverse(mst, start);    // traverse the MST from root vertex
        hamiltonianCycle.add(start); // return back to the start point
        return hamiltonianCycle;
    }

    /**
     * Pre-order traverse the MST to generate a Hamiltonian Cycle
     */
    private static void preOrderTraverse(LinkedList<Integer>[] mst, int root) {
        hamiltonianCycle.add(root); // add current tree node into Hamiltonian Cycle
        LinkedList<Integer> nextNodes = mst[root];
        for (Integer i : nextNodes) {
            preOrderTraverse(mst, i);   // recursively traverse all next-layer tree nodes in MST
        }
    }

    /**
     * Compute the total length of the Hamiltonian Cycle
     */
    public static double getCycleLength(LinkedList<Integer> hamiltonianCycle, Graph graph) {
        double cycleLength = 0.0;
        int from = hamiltonianCycle.getFirst(); // edge starts from the "from" point
        ListIterator iterator = hamiltonianCycle.listIterator(1);
        while (iterator.hasNext()) {
            int to = (int) iterator.next(); // edge ends at the "to" point
            cycleLength += graph.getDistance(from, to); // get the length of current edge and add to total length
            from = to;  // now re-start from the previous ending point
        }
        return cycleLength;
    }
}
