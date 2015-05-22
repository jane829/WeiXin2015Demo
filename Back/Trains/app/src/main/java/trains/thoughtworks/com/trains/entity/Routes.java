package trains.thoughtworks.com.trains.entity;

import java.util.List;

/**
 * Created by jane on 15-5-23.
 */
public class Routes {
    // start Node
    private String startNode;

    // end Node
    private String endNode;

    //all choose-able routes
    private List<Route> routes;

    public String getStartNode() {
        return startNode;
    }

    public void setStartNode(String startNode) {
        this.startNode = startNode;
    }

    public String getEndNode() {
        return endNode;
    }

    public void setEndNode(String endNode) {
        this.endNode = endNode;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public List<Route> getShortestRoutes() {
        return null;
    }

    private class Route {
        //all nodes in the route
        private String[] nodes;

        //route's distance
        private long distance;
    }

    public interface RouteOperate {
        //get all routes whose distance less than distance
        List<RouteVector> getAllRoutesLessThan(long distance);
    }

}
