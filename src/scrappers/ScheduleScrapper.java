package scrappers;

import com.google.gson.Gson;
import com.jaunt.Element;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;
import models.Bus;
import models.Schedule;

import java.util.*;

/**
 * Created by Paul on 9/8/2016.
 */
public class ScheduleScrapper implements Scrapper<Schedule> {

    private final String url;
    private final UserAgent userAgent;

    public ScheduleScrapper(String url) throws Exception {
        this.url = url;
        userAgent = new UserAgent();
        userAgent.visit(this.url);
    }

    @Override
    public Schedule fetch() throws Exception {
        Element body = userAgent.doc.findFirst("<body>").getElement(0).findFirst("<div id=\"tabele\">");
        Element weekBody = body.getElement(0);
        Element weekendBody = body.getElement(1);

        return new Schedule(createSchedule(weekBody), createSchedule(weekendBody));
    }

    private TreeMap<String, List<String>> createSchedule(Element body) throws Exception {
        TreeMap<String, List<String>> map = new TreeMap<>();
        List<Element> childs = body.getChildElements();
        String lastHour = null;
        List<String> minutes = new ArrayList<>();
        for (Element child : childs) {
            if (child.getAt("id").equals("web_class_hours")) {
                String newLastHour = child.innerText().trim();
                if (lastHour != null && !lastHour.contains("Ora")) {
                    map.put(lastHour, minutes);
                    minutes = new ArrayList<>();
                }
                lastHour = newLastHour;
            }
            if (child.getAt("id").equals("web_class_minutes")) {
                List<Element> minutesElements = child.getChildElements();
                for (Element minute : minutesElements) {
                    minutes.add(minute.innerText().trim());
                }
            }
        }
        if (lastHour != null && !lastHour.contains("Ora")) {
            map.put(lastHour, minutes);
        }
        return map;
    }

    public static void main(String[] args) throws Exception {
        BusesScrapper busesScrapper = new BusesScrapper();
        List<Bus> buses = busesScrapper.fetch();
        for (Bus bus : buses) {

            String linkTourUrl = new BusesUrlListScrapper(bus.linkTour).fetch();
            String linkRetourUrl = new BusesUrlListScrapper(bus.linkRetour).fetch();
            new BusScrapper(linkTourUrl).fetch();
            new BusScrapper(linkRetourUrl).fetch();
            System.out.println();
        }
    }
}
