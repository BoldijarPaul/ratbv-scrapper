package scrappers;

import models.Bus;
import models.BusStop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 9/8/2016.
 */
public class BusWithStopsScrapper implements Scrapper<List<Bus>> {

    private final List<Bus> buses;

    public BusWithStopsScrapper(List<Bus> buses) {
        this.buses = buses;
    }

    @Override
    public List<Bus> fetch() throws Exception {
        for (Bus bus : buses) {
            String linkTourUrl = new BusesUrlListScrapper(bus.linkTour).fetch();
            String linkRetourUrl = new BusesUrlListScrapper(bus.linkRetour).fetch();
            List<BusStop> tourStops = new BusScrapper(linkTourUrl).fetch();
            List<BusStop> retourStops = new BusScrapper(linkRetourUrl).fetch();
            bus.tourStops = tourStops;
            bus.retourStops = retourStops;
        }
        return buses;
    }
}
