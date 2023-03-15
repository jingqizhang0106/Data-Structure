import java.io.*;
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
    static int num_TestCase;

    public static void main(String[] args) {
        num_TestCase = 0;
        while (true) {
            num_TestCase++;

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
                    for (int i : hamiltonianCycle_approx) {
                        System.out.print(i + " ");
                    }
                    double approx_length = ApproxTSP.getCycleLength(hamiltonianCycle_approx, graph);
                    System.out.println("\nLength of Cycle: " + approx_length + " miles");   // get the length of the Hamiltonian Cycle

                    // Optimal TSP
                    OptimalTSP optimalTSP = new OptimalTSP(graph);
                    LinkedList<Integer> hamiltonianCycle_optimal = optimalTSP.getHamiltonianCycle(); // get a Hamiltonian Cycle from graph
                    System.out.println("\nLooking at every permutation to find the optimal solution\nThe best permutation");
                    for (int i : hamiltonianCycle_optimal) {
                        System.out.print(i + " ");
                    }
                    double optimal_length = optimalTSP.getCycleLength();
                    System.out.println("\nOptimal Cycle length = " + optimal_length + " miles");   // get the length of the Hamiltonian Cycle


                    // generate KML file
                    generateKML(graph, hamiltonianCycle_approx, hamiltonianCycle_optimal);


                    // Generate result.txt
                    generateResult(hamiltonianCycle_approx, hamiltonianCycle_optimal, approx_length, optimal_length);

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

    public static void generateResult(LinkedList<Integer> hamiltonianCycle_approx, LinkedList<Integer> hamiltonianCycle_optimal, double approx_length, double optimal_length) {
        StringBuilder sb = new StringBuilder();
        if (num_TestCase == 1) {
            sb.append("jingqiz\n\n");
        }
        sb.append("TestCase" + num_TestCase + "\nHamiltonian Cycle\n");
        for (int i : hamiltonianCycle_approx) {
            sb.append(i + " ");
        }
        sb.append("\nLength\n" + approx_length + "\nOptimum path\n");
        for (int i : hamiltonianCycle_optimal) {
            sb.append(i + " ");
        }
        sb.append("\nLength\n" + optimal_length + "\n\n");

        // write into results.txt
        try {
            FileWriter fw = new FileWriter("result.txt", true);
            fw.write(sb.toString());
            fw.close();
            System.out.println("\nThe results has been written to results.txt\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateKML(Graph graph, LinkedList<Integer> hamiltonianCycle_approx, LinkedList<Integer> hamiltonianCycle_optimal) {

        // Approximate TSP
        StringBuilder approx_sb = new StringBuilder();
        for (int i : hamiltonianCycle_approx) {
            approx_sb.append(graph.getVertices()[i].getCrimeRecord().split(",")[8])    // get Longitude
                    .append(",")
                    .append(graph.getVertices()[i].getCrimeRecord().split(",")[7] + 0.1)  // get Latitude
                    .append(",0.000000\n");
        }
        String approx_TSP_KML = approx_sb.toString();

        // Optimal TSP
        StringBuilder optimal_sb = new StringBuilder();
        for (int i : hamiltonianCycle_optimal) {
            System.out.print(i + " ");
            optimal_sb.append(graph.getVertices()[i].getCrimeRecord().split(",")[8])    // get Longitude
                    .append(",")
                    .append(graph.getVertices()[i].getCrimeRecord().split(",")[7])  // get Latitude
                    .append(",0.000000\n");
        }
        String optimal_TSP_KML = optimal_sb.toString();


        // generate KML file
        String kml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<kml xmlns=\"http://earth.google.com/kml/2.2\">\n" +
                "<Document>\n" +
                "<name>Pittsburgh TSP</name>" +
                "<description>TSP on Crime</description>" +
                "<Style id=\"style6\">\n" +
                "<LineStyle>\n<color>73FF0000</color>\n<width>5</width>\n" +
                "</LineStyle>\n</Style>\n" +
                "<Style id=\"style5\">\n<LineStyle>\n<color>507800F0</color>\n" +
                "<width>5</width>\n</LineStyle>\n</Style>\n<Placemark>\n" +
                "<name>TSP Path</name>\n" +
                "<description>TSP Path</description>\n" +
                "<styleUrl>#style6</styleUrl>\n" +
                "<LineString>\n<tessellate>1</tessellate>\n" +
                "<coordinates>\n" +
                approx_TSP_KML +
                "</coordinates>\n" +
                "</LineString>\n</Placemark>\n<Placemark>\n" +
                "<name>Optimal Path</name>\n" +
                "<description>Optimal Path</description>\n" +
                "<styleUrl>#style5</styleUrl>\n" +
                "<LineString>\n<tessellate>1</tessellate>\n" +
                "<coordinates>\n" +
                optimal_TSP_KML +
                "</coordinates>\n" +
                "</LineString>\n</Placemark>\n</Document>\n</kml>";

        // write into PGHCrimes.KML
        try {
            FileWriter fw = new FileWriter("PGHCrimes.KML");
            fw.write(kml);
            fw.close();
            System.out.println("\nThe crime data has been written to PGHCrimes.KML. It is viewable in Google Earth Pro.\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}