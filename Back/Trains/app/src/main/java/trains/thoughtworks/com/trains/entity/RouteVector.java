package trains.thoughtworks.com.trains.entity;

/**
 * Created by jane on 15-5-21.
 */
public class RouteVector {

    // start point
    private String start;

    // end point
    private String end;

    // distance of the route
    private long distance;

    public RouteVector() {
    }

    public RouteVector(String start, String end, long distance) {
        this.start = start;
        this.end = end;
        this.distance = distance;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return start + end + distance;
    }
}
