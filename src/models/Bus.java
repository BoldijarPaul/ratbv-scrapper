package models;

public class Bus {
    public String name;
    public String route;
    public String busType;
    public String number;
    public String linkTour;
    public String linkRetour;

    public Bus() {
        linkTour = "http://ratbv.ro/afisaje/" + number.toLowerCase() + "-dus.html";
        linkRetour = "http://ratbv.ro/afisaje/" + number.toLowerCase() + "-intors.html";
    }

    public Bus(String name, String route, String busType, String number) {
        this.name = name;
        this.route = route;
        this.busType = busType;
        this.number = number;
        linkTour = "http://ratbv.ro/afisaje/" + number.toLowerCase() + "-dus.html";
        linkRetour = "http://ratbv.ro/afisaje/" + number.toLowerCase() + "-intors.html";
    }
}
