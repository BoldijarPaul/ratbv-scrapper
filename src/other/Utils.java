package other;

/**
 * Created by Paul on 9/7/2016.
 */
public class Utils {

    public static String getBetweenStrings(String text, String textFrom, String textTo) {
        System.out.println(text + " " + textFrom + " " + textTo);
        String result = "";
        // Cut the beginning of the text to not occasionally meet a
        // 'textTo' value in it:
        result = text.substring(text.indexOf(textFrom) + textFrom.length(), text.length());
        // Cut the excessive ending of the text:
        result = result.substring(0, result.indexOf(textTo));
        return result;
    }
}
