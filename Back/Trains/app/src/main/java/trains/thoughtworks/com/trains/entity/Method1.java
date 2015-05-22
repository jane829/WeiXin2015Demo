package trains.thoughtworks.com.trains.entity;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import trains.thoughtworks.com.trains.TrainsApplication;
import trains.thoughtworks.com.trains.dao.RouteVectorDao;

/**
 * Created by jane on 15-5-21.
 */
public class Method1 {
    private TrainsApplication trainsApplication;
    private RouteVectorDao mDao;
    //all the vectors saved in the database //or maybe the valid vactors
    List<RouteVector> routeVectors = null;

    //all nodes has been visited
    List<String> traversalNodes = new ArrayList<>();
    HashMap<String, Boolean> traversalNodesExisted = new HashMap<String, Boolean>();

    //the valid routes from start to end
    Set<String> resultSet = new HashSet<>();

    //circle List
    Set<RouteVector> circleList = new HashSet<>();
    private long routeDistance = 0;

    //circle nodes ,like C->D,D->C
    private Set<String> circleNodes;

    private boolean isEndNodeCircle;

    public Method1(Context context) {
        trainsApplication = (TrainsApplication) context.getApplicationContext();
        routeVectors = trainsApplication.getRouteVectors();
        mDao = new RouteVectorDao(context);
        getAllCircleNodes();
    }

    public Set<String> getResultSet() {
        return resultSet;
    }

    public void setRouteDistance(long routeDistance) {
        this.routeDistance = routeDistance;
    }

    /**
     * query all the basic route vectors
     * from database
     * @return
     */
    public List<RouteVector> queryAllRouteVectors() {

        routeVectors = new ArrayList<RouteVector>();
        routeVectors.add(new RouteVector("A","B",5));
        routeVectors.add(new RouteVector("B","C",4));
        routeVectors.add(new RouteVector("C","D",8));
        routeVectors.add(new RouteVector("D","C",8));
        routeVectors.add(new RouteVector("D","E",6));
        routeVectors.add(new RouteVector("A","D",5));
        routeVectors.add(new RouteVector("E","B",3));
        routeVectors.add(new RouteVector("C","E",2));
        routeVectors.add(new RouteVector("A","E",7));
        for(RouteVector v : routeVectors) {

            mDao.insert(v);
        }

        List<RouteVector> vectors = mDao.query();
        if (vectors != null) {
            for(RouteVector v : vectors) {
                Log.e("TAG",v.toString());
            }
        }

        return routeVectors;
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


    public Set<String> filterInvalidNode(Set<String> all, List<RouteVector> vectors,
                                         Set<String>begins, Set<String>ends) {
        Set<String> result = new HashSet<String>();
        boolean isBegin = true;
        boolean isEnd = true;
        for (String str : all) {
            if(!existEnd(str, vectors)) {
                isBegin = false;
                begins.add(str);
            } else if(!existBegin(str,vectors)) {
                isEnd = false;
                ends.add(str);
            } else {
                result.add(str);
            }
        }
        if(isBegin == true && isEnd == true) {
            return result;
        } else {
            return filterInvalidNode(result, vectors, begins, ends);
        }
    }

    private boolean existBegin(String str, List<RouteVector> vectors) {
        for (RouteVector vector : vectors) {
            if(str.equals(vector.getStart())) {
                return true ;
            }
        }
        return false;
    }

    private boolean existEnd(String str, List<RouteVector> vectors) {
        for (RouteVector vector : vectors) {
            if(str.equals(vector.getEnd())) {
                return true;
            }
        }
        return false;
    }

    public List<RouteVector> deleteValidVactor(Set<String> begins, Set<String> ends,
                                                   List<RouteVector> vectors ) {
        List<RouteVector> set = new ArrayList<RouteVector>();
        for (String str : begins) {
            for (RouteVector vector : vectors) {
                if (vector.getStart().equals(str)) {
                    set.add(vector);
                }
                if (vector.getEnd().equals(str)) {
                    set.add(vector);
                }
            }
        }
        return set;
    }

    public void getAllRoad(String start, String end) {

        isEndNodeCircle = circleNodes.contains(end);
        traversalNodes.add(start);
        if (traversalNodes.size() >1) {
            routeDistance = routeDistance + find(traversalNodes.get(traversalNodes.size()-2), start).getDistance();
        }

        List<RouteVector> tempRouteVectors = getVectorWithStartNode(start);


        for(int z = 0; z< tempRouteVectors.size(); z++) {
            RouteVector routeVector = tempRouteVectors.get(z);
            String startNode = routeVector.getStart();
            String endNode = routeVector.getEnd();
            long distance = routeVector.getDistance();

            if(startNode.equals(start)) {

                if(endNode.equals(end)) {

                    boolean f = false;
                    for(RouteVector v : circleList) {
                        if(routeVector.toString().equals(v.toString())){
                            f = true;
                        }
                    }
                    if(!f){
//                        routeDistance = routeDistance + distance;
                        resultSet.add(traversalNodes.toString().substring(0, traversalNodes.toString()
                                .lastIndexOf("]")) + ", " + end + (routeDistance+distance) +"]");
                        if(!traversalNodes.contains(end) && isEndNodeCircle) {
                            getAllRoad(endNode, end);
                        }
                    }
//                    routeDistance = routeDistance -distance;
                    continue;
                }
                if(!traversalNodes.contains(endNode)) {
                    getAllRoad(endNode, end);
                }else {
                    if (!circleNodes.contains(endNode)) {
                        continue;
                    } else {
                        boolean flag = false;
                        for (RouteVector v : circleList) {
                            if(routeVector.toString().equals(v.toString())) {
                                flag = true;
                            }
                        }

                        if(!flag ) {
                            circleList.add(new RouteVector(endNode, startNode, distance));
                            circleList.add(routeVector);
                            getAllRoad(endNode, end);
                        } else {
//                            routeDistance = routeDistance - distance;
                        }
                    }

                }
            }
        }

        if(traversalNodes.size()>1){
            String e = traversalNodes.get(traversalNodes.size()-1);
            String s = traversalNodes.get(traversalNodes.size()-2);
            RouteVector vector;
            vector = find(s, e);
            routeDistance = routeDistance - vector.getDistance();
        }
        traversalNodes.remove(traversalNodes.size()-1);
        circleList.clear();
    }

    private RouteVector find(String s, String e) {
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
}
