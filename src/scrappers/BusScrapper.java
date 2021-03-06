package scrappers;

import com.jaunt.Element;
import com.jaunt.UserAgent;
import models.Bus;
import models.BusStop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 9/7/2016.
 */
public class BusScrapper implements Scrapper<List<BusStop>> {

    private final String busUrl;
    private final UserAgent userAgent;

    public BusScrapper(String busUrl) throws Exception {
        this.busUrl = busUrl;
        userAgent = new UserAgent();
        userAgent.visit(busUrl);
    }

    @Override
    public List<BusStop> fetch() throws Exception {
        List<BusStop> busStops = new ArrayList<>();
        userAgent.visit(busUrl);
        Element body = userAgent.doc.findFirst("<body>").findFirst("<div id=\"glue_file\">").findEach("<div>");
        for (Element element : body.getChildElements()) {
            String stopName = element.findEvery("<b>").getElement(0).innerText();
            String stopUrl = element.findEvery("<a>").getElement(0).getAt("href");
            busStops.add(new BusStop(stopName, stopUrl));
        }
        if (busStops.size() > 0) {
            BusStop lastBusStop = busStops.get(busStops.size() - 1);
            lastBusStop.url = new BusesFirstUrlScrapper(lastBusStop.url).fetch();
        }
        return busStops;
    }

    static public void main(String[] args) throws Exception {
        List<Bus> buses = new ArrayList<>();
        Bus bus = new Bus("Linia x", "rut", "tip", "28B");
        bus.linkTour = "http://ratbv.ro/afisaje/28b-dus.html";
        bus.linkRetour = "http://ratbv.ro/afisaje/28b-intors.html";
        buses.add(bus);

        buses = new BusWithStopsScrapper(buses).fetch();

        for (BusStop busStop : bus.tourStops) {
            System.out.println("Getting schedules for bus " + bus.number + " bus stop " + busStop.name);
            busStop.schedule = new ScheduleScrapper(busStop.url).fetch();
        }
        for (BusStop busStop : bus.retourStops) {
            System.out.println("Getting schedules for bus " + bus.number + " bus stop " + busStop.name);
            busStop.schedule = new ScheduleScrapper(busStop.url).fetch();
        }
        /*
        BusesScrapper busesScrapper = new BusesScrapper();
        List<Bus> buses = busesScrapper.fetch();
        for (Bus bus : buses) {

            System.out.println("############ Bus " + bus.number + " tour");
            String linkTourUrl = new BusesUrlListScrapper(bus.linkTour).fetch();
            String linkRetourUrl = new BusesUrlListScrapper(bus.linkRetour).fetch();
            new BusScrapper(linkTourUrl).fetch();
            System.out.println("############ Bus " + bus.number + " retour");
            new BusScrapper(linkRetourUrl).fetch();
            System.out.println();
        }*/
    }
}
