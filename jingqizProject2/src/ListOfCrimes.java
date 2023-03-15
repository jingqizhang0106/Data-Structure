import java.io.FileWriter;
import java.io.IOException;

/**
 * This class provide methods for adding crimes to the list and retrieving crimes from the list.
 * It is built using a singly linked list with a single head pointer.
 * It has a toString() method to return the list as a String and a toKML() method to return a KML representation
 * of the list.
 *
 * @author Mia Zhang
 */
public class ListOfCrimes {
    ListNode head;
    int count_crimes;
    int count_visited_nodes;

    public ListOfCrimes() {
        head = null;
        count_crimes = 0;
        count_visited_nodes = 0;
    }

    public void addCrime(String crime) {
        if (head == null) head = new ListNode(crime);
        else {
            ListNode curr = head;
            while (curr.next != null) {
                curr = curr.next;
            }
            curr.next = new ListNode(crime);
        }
        count_crimes++;
    }

    /**
     * Write the nodes data into the file named "PGHCrimes.KML"
     */
    public void toKML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<kml xmlns=\"http://earth.google.com/kml/2.2\">\n" +
                "<Document>\n" +
                " <Style id=\"style1\">\n" +
                " <IconStyle>\n" +
                " <Icon>\n" +
                "\n" +
                "<href>http://maps.gstatic.com/intl/en_ALL/mapfiles/ms/micons/blue\n" +
                "-dot.png</href>\n" +
                " </Icon>\n" +
                " </IconStyle>\n" +
                " </Style>\n");
        ListNode curr = head;
        for (int i = 0; i < count_crimes; i++) {
            String[] crimeInfo = ((String) curr.data).split(",");
            sb.append("<Placemark>\n" +
                    " <name>" + crimeInfo[4] + "</name>\n" +
                    " <description>" + crimeInfo[3] + "</description>\n" +
                    " <styleUrl>#style1</styleUrl>\n" +
                    " <Point>\n" +
                    " <coordinates>-\n" +
                    crimeInfo[8] + "," + crimeInfo[7] + ",0.000000</coordinates>\n" +
                    " </Point>\n" +
                    " </Placemark>\n");
            curr = curr.next;
        }
        sb.append("</Document>\n" +
                "</kml>");

        try {
            FileWriter myWriter = new FileWriter("PGHCrimes.KML");
            myWriter.write(sb.toString());
            myWriter.close();
            System.out.println("The crime data has been written to PGHCrimes.KML. It is viewable in Google Earth Pro.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void retrieveCrime(String crime) {
        if (head == null) return;
        ListNode curr = head, pre = head;
        while (curr.next != null) {
            if (curr.data.equals(crime)) {
                pre.next = curr.next;
            }
            pre = curr;
            curr = curr.next;
        }
    }

    @Override
    public String toString() {
        ListNode curr = head;
        StringBuilder sb = new StringBuilder();
        while (curr != null) {
            sb.append(curr.data.toString()).append('\n');
            curr = curr.next;
        }
        return sb.toString();
    }
}
