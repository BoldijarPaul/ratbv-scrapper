package scrappers;

public interface Scrapper<Type> {

    Type fetch() throws Exception;
}
