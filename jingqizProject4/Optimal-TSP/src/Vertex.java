/**
 * HeapNode Data Structure for representing each vertex in a graph
 */
public class Vertex {
    private double X_coordinate;
    private double Y_coordinate;
    private String crimeRecord;

    /**
     * Construct a Vertex
     */
    public Vertex(String crimeRecord) {
        this.crimeRecord = crimeRecord;
        X_coordinate = Double.parseDouble(crimeRecord.split(",")[0]);
        Y_coordinate = Double.parseDouble(crimeRecord.split(",")[1]);
    }

    public double getX_coordinate() {
        return X_coordinate;
    }

    public void setX_coordinate(double x_coordinate) {
        X_coordinate = x_coordinate;
    }

    public double getY_coordinate() {
        return Y_coordinate;
    }

    public void setY_coordinate(double y_coordinate) {
        Y_coordinate = y_coordinate;
    }

    public String getCrimeRecord() {
        return crimeRecord;
    }

    public void setCrimeRecord(String crimeRecord) {
        this.crimeRecord = crimeRecord;
    }

}
