package scrappers;

import com.jaunt.Element;
import com.jaunt.NotFound;
import com.jaunt.UserAgent;

/**
 * Created by Paul on 9/8/2016.
 */
public class BusesUrlListScrapper implements Scrapper<String> {
    private final String url;
    private final UserAgent userAgent;

    public BusesUrlListScrapper(String url) throws Exception {
        this.url = url.toLowerCase();
        userAgent = new UserAgent();
        userAgent.visit(this.url);
    }

    @Override
    public String fetch() throws Exception {
        Element body = userAgent.doc.findFirst("<frameset>").getElement(1);
        return body.getAt("src");
    }
}
