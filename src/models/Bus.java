package models;

public class Bus {
    public String name;
    public String route;
    public String busType;
    public String link;

    public Bus(){

    }

    public Bus(String name, String route, String busType, String link) {
        this.name = name;
        this.route = route;
        this.busType = busType;
        this.link = link;
    }
}
