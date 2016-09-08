package scrappers;

import com.jaunt.Element;
import com.jaunt.UserAgent;

/**
 * Created by Paul on 9/8/2016.
 */
public class BusesFirstUrlScrapper implements Scrapper<String> {
    private final String url;
    private final UserAgent userAgent;

    public BusesFirstUrlScrapper(String url) throws Exception {
        if (url.contains("afisaje")) {
            url = url.toLowerCase();
        }
        this.url = url;
        userAgent = new UserAgent();
        userAgent.visit(this.url);
    }

    @Override
    public String fetch() throws Exception {
        Element body = userAgent.doc.findFirst("<frameset>").getElement(2);
        return body.getAt("src");
    }
}
