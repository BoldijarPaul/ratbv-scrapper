package generator;

import com.google.gson.Gson;
import models.Bus;
import models.BusStop;
import models.Schedule;
import scrappers.*;

import java.io.*;
import java.util.List;

/**
 * Created by Paul on 9/8/2016.
 */
public class ApiGenerator {

    private Gson gson = new Gson();

    private void write(String filename, Object object) throws Exception {
        File file = new File(filename);
        file.getParentFile().mkdirs();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(gson.toJson(object));
        writer.close();
    }

    public void generateBuses() throws Exception {
        System.out.println("Getting the busses");
        List<Bus> buses = new BusesScrapper().fetch();

        System.out.println("Saving the bus stops");
        buses = new BusWithStopsScrapper(buses).fetch();
        for (Bus bus : buses) {
            for (BusStop busStop : bus.tourStops) {
                System.out.println("Getting schedules for bus " + bus.number + " bus stop " + busStop.name);
                busStop.schedule = new ScheduleScrapper(busStop.url).fetch();
            }
            for (BusStop busStop : bus.retourStops) {
                System.out.println("Getting schedules for bus " + bus.number + " bus stop " + busStop.name);
                busStop.schedule = new ScheduleScrapper(busStop.url).fetch();
            }
        }
        write("api\\buses.json", buses);
    }

    public void generateIndividualBuses() throws Exception {
        List<Bus> buses = new BusesScrapper().fetch();
        write("api\\files", buses);
    }

    static public void main(String[] args) throws Exception {
        ApiGenerator apiGenerator = new ApiGenerator();
        apiGenerator.generateBuses();
    }
}
