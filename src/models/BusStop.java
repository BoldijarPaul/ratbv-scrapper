package models;

/**
 * Created by Paul on 9/7/2016.
 */
public class BusStop {
    public String name;
    public String url;
    public Schedule schedule;

    public BusStop(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public BusStop() {
    }
}
