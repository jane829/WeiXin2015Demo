package trains.thoughtworks.com.trains.utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.HandlerBase;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import trains.thoughtworks.com.trains.R;
import trains.thoughtworks.com.trains.entity.RouteVector;

/**
 * Created by jane on 15-5-22.
 */
public class XmlParser {

    public static List<RouteVector> parse(Context context) {
        XmlResourceParser xmlParser = context.getResources().getXml(R.xml.route_vector_file);
        return doParse(xmlParser);
    }

    private static List<RouteVector> doParse(XmlResourceParser xmlParser) {
        RouteVector routeVector = null;
        List<RouteVector> routeVectors = null;
        try {
            int eventType = xmlParser.getEventType();
            while ((eventType != XmlPullParser.END_DOCUMENT)) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        routeVectors = new ArrayList<RouteVector>();
                        break;
                    case XmlPullParser.START_TAG:
                        String tag = xmlParser.getName().trim();
                        if ("vector".equalsIgnoreCase(tag)) {
                            routeVector = new RouteVector();
                        } else if(routeVector != null) {
                            if("begin".equals(tag)) {
                                routeVector.setStart(xmlParser.nextText());
                            } else if("end".equals(tag)) {
                                routeVector.setEnd(xmlParser.nextText());
                            } else if("distance".equals(tag)) {
                                routeVector.setDistance(Long.parseLong(xmlParser.nextText()));
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (("vector".equals(xmlParser.getName()) && routeVector !=null)) {
                            routeVectors.add(routeVector);
                            routeVector = null;
                        }
                        break;
                }
                eventType = xmlParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            xmlParser.close();
        }
        return routeVectors;

    }
}
