import java.util.LinkedList;

/**
 * A bruce force method to find the optimal solution to TSP
 * by listing every possible tour and compute the length of each.
 * There are |V|! permutations of the |V| vertices.
 */
public class OptimalTSP {
    private LinkedList<Integer> hamiltonianCycle;
    private double cycleLength;

    /**
     * Calculate the cycle length of each possible permutation using backtracking
     */
    public OptimalTSP(Graph graph) {
        cycleLength = Double.MAX_VALUE;
        LinkedList<Integer> visited = new LinkedList<>();
        for (int i = 0; i < graph.getSize(); i++) {
            visited.add(i);     // try starting from each vertex
            backTracking(0.0, visited, graph);
            visited.removeLast();   // retrieve
        }
    }

    /**
     * Recursively try next vertex along the route, update length, and backtrack
     */
    private void backTracking(double currentLength, LinkedList<Integer> visited, Graph graph) {
        if (currentLength > cycleLength)
            return;   // prune if current length is already larger than the optimal length so far
        if (visited.size() == graph.getSize()) {   // if all vertices have been visited
            currentLength += graph.getDistance(visited.getLast(), visited.getFirst());    // add the length back to the starting point
            if (currentLength < cycleLength) {  // if new optimal cycle length found, update it
                cycleLength = currentLength;
                hamiltonianCycle = (LinkedList<Integer>) visited.clone();
                hamiltonianCycle.addLast(visited.getFirst());   // record the route
            }
            return;
        }
        for (int i = 0; i < graph.getSize(); i++) {
            if (visited.contains(i)) continue;   // for each vertex hasn't been visited
            double edgeLength = graph.getDistance(i, visited.getLast()); // save the edge length from last vertex for the later-on retrieving
            currentLength += edgeLength;
            visited.add(i);
            backTracking(currentLength, visited, graph);
            currentLength -= edgeLength;  // retrieve
            visited.removeLast();   // retrieve
        }
    }

    public LinkedList<Integer> getHamiltonianCycle() {
        return hamiltonianCycle;
    }

    public double getCycleLength() {
        return cycleLength;
    }


}
