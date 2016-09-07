package scrappers;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;

import models.Bus;

import com.google.gson.Gson;
import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.NotFound;
import com.jaunt.UserAgent;

public class BusesScrapper implements Scrapper<List<Bus>> {

    private UserAgent userAgent;

    public BusesScrapper() throws Exception {
        userAgent = new UserAgent(); // create new userAgent (headless browser)
        userAgent.visit("http://ratbv.ro/trasee-si-orare/"); // visit google

    }

    private List<String> getCategories(Element body) throws Exception {
        List<String> busTypes = new ArrayList<String>();
        Elements typesElements = body.getElement(0).findEvery("<span>");
        for (Element element : typesElements) {
            busTypes.add(element.innerText());
        }
        return busTypes;
    }

    @Override
    public List<Bus> fetch() throws Exception {
        List<Bus> buses = new ArrayList<>();
        Element body = userAgent.doc.findFirst("<body>")
                .findFirst("<div class=\"wrapper\">")
                .findFirst("<div class=\"container continut\">")
                .findFirst("<div class=\"box continut-pagina\">")
                .findFirst("<table>").findFirst("<tbody>");

        List<String> busTypes = getCategories(body);

        Elements trElements = body.findEach("<tr>");
        for (int i = 1; i < trElements.size(); i++) {
            Element element = trElements.getElement(i);
            Elements tdElements = element.findEach("<td>");
            if (tdElements.size() != busTypes.size()) {
                continue;
            }
            for (int j = 0; j < tdElements.size(); j++) {
                String busType = busTypes.get(j);
                Element tdElement = tdElements.getElement(j);
                Elements nameElement = tdElement.findEvery("<a>");
                Elements routeElement = tdElement
                        .findEvery("<span style=\"color: #2f4f4f;\">");
                if (nameElement.size() == 1 && routeElement.size() == 1) {
                    String name = nameElement.getElement(0).innerText();
                    String route = routeElement.getElement(0).innerText();
                    if (nameElement.getElement(0).hasAttribute("href")) {
                        String link = nameElement.getElement(0).getAt("href");
                        buses.add(new Bus(name, route, busType, link));
                    }
                }
            }
        }
        return buses;
    }

    public static void main(String[] args) throws Exception {
        List<Bus> buses = new BusesScrapper().fetch();
        System.out.println(buses.size());
        String json = new Gson().toJson(buses);
        System.out.println(json);

        StringSelection selection = new StringSelection(json);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }
}
