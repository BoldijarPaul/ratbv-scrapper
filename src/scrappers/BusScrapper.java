package scrappers;

import com.jaunt.Element;
import com.jaunt.NotFound;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;
import models.Bus;

import java.util.List;

/**
 * Created by Paul on 9/7/2016.
 */
public class BusScrapper implements Scrapper<Object> {

    private final String busUrl;
    private final UserAgent userAgent;

    public BusScrapper(String busUrl) throws ResponseException {
        this.busUrl = busUrl;
        userAgent = new UserAgent();
        userAgent.visit(busUrl);
    }

    @Override
    public Object fetch() throws Exception {
        String url = getUrl();
        userAgent.visit(url);
        Element body = userAgent.doc.findFirst("<body>").findFirst("<div id=\"glue_file\">").findEach("<div>");
        for (Element element : body.getChildElements()) {
            String stopName = element.findEvery("<b>").getElement(0).innerText();
            System.out.println(stopName);
        }
        return null;
    }

    private String getUrl() throws NotFound {
        Element body = userAgent.doc.findFirst("<frameset>").getElement(1);
        return body.getAt("src");
    }

    static public void main(String[] args) throws Exception {
        BusesScrapper busesScrapper = new BusesScrapper();
        List<Bus> buses = busesScrapper.fetch();
        for (Bus bus : buses) {
            System.out.println("############ Bus " + bus.number + " tour");
            new BusScrapper(bus.linkTour).fetch();
            System.out.println("############ Bus " + bus.number + " retour");
            new BusScrapper(bus.linkRetour).fetch();
            System.out.println();
        }
    }
}
