package parsers;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;

import java.util.ArrayList;
import java.util.Map;

public interface ParserInterface {
    public static CloseableHttpClient x =null;
    public Map<String, String> getURLAndName(ArrayList<String> urls);
    public Map<String, String> getHeaders();

}
