import java.io.*;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * The Main class handles file input reading, constructing the tree and graph, and file output writing.
 *
 * @Author Mia Zhang
 * @AndrewID jingqiz
 */
public class Main {
    public static void main(String[] args) {
        RedBlackTree courseDictionary = new RedBlackTree();
        Graph graph = new Graph();
        StringTokenizer st;
        int num, id;
        String course;
        String[] courseNames = new String[20];  // For easy course id, name mapping

        try {
            BufferedReader in = new BufferedReader(new FileReader(args[0]));
            String line;
            line = in.readLine();

            // According to the input file, construct course tree and graph
            while (line != null) {
                st = new StringTokenizer(line, " \t");
                st.nextToken();
                num = Integer.parseInt(st.nextToken());
                int[] studentCourses = new int[num];    // to record conflict courses (adjacent vertices) for this student
                // handle courses this student takes
                for (int i = 0; i < num; i++) {
                    course = st.nextToken();
                    if (!courseDictionary.contains(course)) courseDictionary.insert(course);    // If course is not seen before, add it to dictionary
                    id = courseDictionary.getCourseId(course);
                    studentCourses[i] = id;
                    courseNames[id] = course;
                }
                // set adjacent matrix
                for (int i = 0; i < num; i++) {
                    for (int j = i + 1; j < num; j++) {
                        graph.setAdj(studentCourses[i], studentCourses[j]);
                    }
                }
                line = in.readLine();
            }

            // show course id assignment on console
            courseDictionary.inOrderTraversal();

            graph.setSize(courseDictionary.getSize());
            LinkedList<LinkedList<Integer>> schedule = ExamScheduler.generateSchedule(graph);
            // System.out.println(schedule);

            PrintWriter pw = null;
            try {
                pw = new PrintWriter(new FileWriter("result.txt",true));    // append writing
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // print the adjacent matrix
            pw.println(graph);

            // print the course schedule
            int period = 0;
            for (LinkedList<Integer> list : schedule) {
                String printLine = "Final Exam Period " + ++period + " =>";
                for (Integer i : list) {
                    printLine += " " + courseNames[i];
                }
                pw.println(printLine);
            }
            pw.println();
            pw.close();
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
    }

}
