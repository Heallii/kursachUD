package Objects;

public class Trip {

    private int id;
    private String startPoint;
    private float distance;
    private String endPoint;

    public Trip(int id, String startPoint, float distance, String endPoint) {
        this.id = id;
        this.startPoint = startPoint;
        this.distance = distance;
        this.endPoint = endPoint;
    }

    public int getId() {
        return id;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public float getDistance() {
        return distance;
    }

    public String getEndPoint() {
        return endPoint;
    }
}
