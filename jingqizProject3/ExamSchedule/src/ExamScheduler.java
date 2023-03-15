import java.util.Iterator;
import java.util.LinkedList;

/**
 * The ExamScheduler class uses greedy algorithm to color a graph and returns a possible exam schedule.
 *
 * @Author Mia Zhang
 * @AndrewID jingqiz
 */
public class ExamScheduler {
    public static LinkedList<LinkedList<Integer>> generateSchedule(Graph graph) {
        LinkedList<LinkedList<Integer>> schedule = new LinkedList<>();
        while (!graph.allColored()) {   // repeat greedy until all vertices in the graph are colored
            LinkedList newclr = new LinkedList();
            greedy(graph, newclr);
            schedule.add(newclr);
        }
        return schedule;
    }

    /**
     * greedy() assigns to newclr those vertices that may be given the same color
     */
    private static void greedy(Graph graph, LinkedList<Integer> newclr) {
        boolean found;
        int v = graph.getNextUncoloredVertex(0);
        Object w;
        while (v != -1) {
            found = false;
            Iterator<Integer> it = newclr.iterator();
            w = it.hasNext() ? it.next() : null;
            while (w != null) {
                if (graph.getAdj(v, (int) w) == 1) {    // if there is an edge between v and w
                    found = true;
                }
                w = it.hasNext() ? it.next() : null;
            }
            if (!found) {   // if v is not adjacent to any vertex in newclr
                graph.setColored(v);
                newclr.add(v);
            }
            v = graph.getNextUncoloredVertex(v + 1);
        }
    }
}
