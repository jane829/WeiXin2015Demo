package trains.thoughtworks.com.trains.entity;

import android.content.Context;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import trains.thoughtworks.com.trains.TrainsApplication;
import trains.thoughtworks.com.trains.dao.RouteVectorDao;

/**
 * Created by jane on 15-5-21.
 */
public class Method {
    private TrainsApplication trainsApplication;

    // all the vectors saved in the database
    private List<RouteVector> routeVectors = null;

    // all nodes has been visited
    private LinkedList<String> traversalNodes = new LinkedList<>();

    // the valid routes from start to end
    private Hashtable<String, Long> resultSet = new Hashtable<String, Long>();
//    private SortedTreeMap<String, Long> resultSet = new SortedTreeMap<String,Long>();
//    private TreeMap<Long, String> resultSet = new TreeMap<Long, String>(new SortedComparator());

    // the visited circle route, like CD,DC
    private Set<RouteVector> circleList = new HashSet<>();

    // distance of each routes from begin->end
    private long routeDistance = 0;

    // circle nodes ,like C->D,D->C
    private Set<String> circleNodes;


    public Method(Context context) {
        trainsApplication = (TrainsApplication) context.getApplicationContext();
        routeVectors = trainsApplication.getRouteVectors();
        getAllCircleNodes();
    }

    public Hashtable<String, Long> getResultSet() {
        return resultSet;
    }

    public void setRouteDistance(long routeDistance) {
        this.routeDistance = routeDistance;
    }

    public Set<String> getAllCircleNodes() {
        circleNodes = new HashSet<String>();
        if(routeVectors == null) {
            return null;
        } else {
            for(int i = 0;i<routeVectors.size(); i++) {
                RouteVector vector = routeVectors.get(i);
                String start = vector.getStart();
                String end = vector.getEnd();
                for(int j=i+1;j<routeVectors.size(); j++) {
                    RouteVector v = routeVectors.get(j);
                    if(v.getStart().equals(end) && v.getEnd().equals(start)) {
                        circleNodes.add(start);
                        circleNodes.add(end);
                    }
                }
            }

            return circleNodes;
        }
    }

    public void getAllRoutes(String start, String end) {

        traversalNodes.add(start);
        if (traversalNodes.size() >1) {
            routeDistance = routeDistance +
                    getVector(traversalNodes.get(traversalNodes.size() - 2), start).getDistance();
        }

        List<RouteVector> tempRouteVectors = getVectorWithStartNode(start);


        for(int z = 0; z< tempRouteVectors.size(); z++) {
            RouteVector routeVector = tempRouteVectors.get(z);
            String startNode = routeVector.getStart();
            String endNode = routeVector.getEnd();
            long distance = routeVector.getDistance();

            if(startNode.equals(start)) {

                if(endNode.equals(end)) {
                    boolean isVectorVisited = checkIfVectorVisited(routeVector);
                    if(!isVectorVisited){
                        resultSet.put(traversalNodes.toString().substring(0, traversalNodes.toString()
                                .lastIndexOf("]")) + ", " + end + "]",routeDistance+distance);
                        if(!traversalNodes.contains(end) && circleNodes.contains(end)) {
                            getAllRoutes(endNode, end);
                        }
                    }
                    continue;
                }
                if(!traversalNodes.contains(endNode)) {
                    getAllRoutes(endNode, end);
                }else {
                    if (!circleNodes.contains(endNode)) {
                        continue;
                    } else {
                        boolean isVectorVisited = checkIfVectorVisited(routeVector);
                        if(!isVectorVisited ) {
                            circleList.add(new RouteVector(endNode, startNode, distance));
                            circleList.add(routeVector);
                            getAllRoutes(endNode, end);
                        }
                    }

                }
            }
        }

        if(traversalNodes.size()>1){
            String e = traversalNodes.get(traversalNodes.size()-1);
            String s = traversalNodes.get(traversalNodes.size()-2);
            RouteVector vector;
            vector = getVector(s, e);
            routeDistance = routeDistance - vector.getDistance();
        }
        traversalNodes.removeLast();
        circleList.clear();
    }

    private boolean checkIfVectorVisited(RouteVector routeVector) {
        boolean isVectorVisited = false;
        for(RouteVector v : circleList) {
            if(routeVector.toString().equals(v.toString())){
                isVectorVisited = true;
            }
        }
        return isVectorVisited;
    }

    private RouteVector getVector(String s, String e) {
        for(RouteVector v: routeVectors) {
            if(v.getStart().equals(s) && v.getEnd().equals(e)) {
                return v;
            }
        }
        return null;
    }

    private List<RouteVector> getVectorWithStartNode(String start) {
        List<RouteVector> vectors = new ArrayList<RouteVector>();
        for (RouteVector v : routeVectors) {
            if (start.equals(v.getStart())) {
                vectors.add(v);
            }
        }
        return vectors;
    }

    public Set<RouteVector> getCircleList() {
        return circleList;
    }

    private class SortedComparator implements Comparator {
        @Override
        public int compare(Object o, Object t1) {
            if ((Long)o > (Long)t1){
                return 1;
            }
            return 0;
        }
    }
}
