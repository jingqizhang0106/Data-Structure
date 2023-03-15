import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

/**
 * Application Main Driver
 * The program will find the optimal TSP tour that visits the crime locations of those crimes
 * that occurred between start_date and end_date inclusive using brute force.
 *
 * @author Mia Zhang
 */
public class Main {
    public static void main(String[] args) {
        // prompt for user inputs
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter start date");
        String start_date = sc.nextLine();
        System.out.println("Enter end date");
        String end_date = sc.nextLine();
        //  if(!isValid(start_date, end_date)){}  // validity check, omitted

        try {
            BufferedReader br = new BufferedReader(new FileReader("CrimeLatLonXY1990.csv"));
            String line, date;
            Boolean flag = false;   // if current record is between the start and end dates
            LinkedList<String> records_within_dates = new LinkedList<>();
            while ((line = br.readLine()) != null) {
                date = line.split(",")[5];
                if (date.equals(start_date)) {
                    flag = true;    // next line need to be default added
                    records_within_dates.add(line);
                }
                if (date.equals(end_date)) {
                    flag = false;    // do not default add next line
                    if (!start_date.equals(end_date))
                        records_within_dates.add(line);
                }
                if (flag && !date.equals(start_date) && !date.equals(end_date)) {   // current line of record hasn't been added
                    records_within_dates.add(line);
                }
            }
            System.out.println("Crime records between " + start_date + " and " + end_date);
            for (int i = 0; i < records_within_dates.size(); i++) {
                System.out.println(records_within_dates.get(i));
            }

            if (records_within_dates.size() > 0) {
                Graph graph = new Graph(records_within_dates);  // generate a graph from records list

                // Approximate TSP
                LinkedList<Integer> hamiltonianCycle_approx = ApproxTSP.getHamiltonianCycle(graph); // get a Hamiltonian Cycle from graph
                System.out.println("\nHamiltonian Cycle (not necessarily optimum):");
                ListIterator iterator = hamiltonianCycle_approx.listIterator();
                while (iterator.hasNext()) {
                    System.out.print(iterator.next() + " ");
                }
                System.out.println("\nLength of Cycle: " + ApproxTSP.getCycleLength(hamiltonianCycle_approx, graph) + " miles");   // get the length of the Hamiltonian Cycle

                // Optimal TSP
                OptimalTSP optimalTSP=new OptimalTSP(graph);
                LinkedList<Integer> hamiltonianCycle_optimal = optimalTSP.getHamiltonianCycle(); // get a Hamiltonian Cycle from graph
                System.out.println("\nLooking at every permutation to find the optimal solution\nThe best permutation");
                ListIterator iterator2 = hamiltonianCycle_optimal.listIterator();
                while (iterator2.hasNext()) {
                    System.out.print(iterator2.next() + " ");
                }
                System.out.println("\nOptimal Cycle length = " + optimalTSP.getCycleLength() + " miles");   // get the length of the Hamiltonian Cycle

            } else {
                System.out.println("No crime records found.");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}