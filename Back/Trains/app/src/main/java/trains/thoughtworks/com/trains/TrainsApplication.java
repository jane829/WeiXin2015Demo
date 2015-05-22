package trains.thoughtworks.com.trains;

import android.app.Application;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import trains.thoughtworks.com.trains.dao.RouteVectorDao;
import trains.thoughtworks.com.trains.entity.RouteVector;
import trains.thoughtworks.com.trains.utils.XmlParser;

/**
 * Created by jane on 15-5-22.
 */
public class TrainsApplication extends Application {
    private static final String PREF_NAME = "trainsPref";
    private static final String KEY_INSTALLED = "isFistInstalled";
    private List<RouteVector> routeVectors;

    @Override
    public void onCreate() {
        super.onCreate();

        // check whether the first time to install app,
        // if true, insert database;
        // else, query database and save data at local
        SharedPreferences pref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        boolean isFirstInstalled = pref.getBoolean(KEY_INSTALLED, true);
        if (isFirstInstalled) {
            pref.edit().putBoolean(KEY_INSTALLED, false).commit();
            routeVectors = XmlParser.parse(this);
            RouteVectorDao routeVectorDao = new RouteVectorDao(this);
            for (RouteVector vector : routeVectors) {
                routeVectorDao.insert(vector);
            }
        } else {
            routeVectors = new RouteVectorDao(this).query();
        }
    }

    public List<RouteVector> getRouteVectors() {
        if (routeVectors == null) {
            routeVectors = new ArrayList<RouteVector>();
        }
        return routeVectors;
    }
}
