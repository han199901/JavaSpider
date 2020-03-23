package parsers;

import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BiAnParser implements ParserInterface {
    private final static String showPath = "http://pic.netbian.com/tupian/";
    private final static String downLoadPath = "http://pic.netbian.com/downpic.php?";
    private Map<String, String> headers;
    public BiAnParser() {
        headers = new HashMap<>();
    }
    public  BiAnParser(Map<String, String> headers) {
        this.headers = headers;
    }
    @Override
    public Map<String, String> getURLAndName(ArrayList<String> urls) {
        Map<String, String> URLAndName = new HashMap<>();
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet("");
        for(String i : urls) {
            get.setUri(URI.create(i));
            CloseableHttpResponse res = null;
            try {
                res = client.execute(get);
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpEntity entity = res.getEntity();

            String html="";
            try {
                html = EntityUtils.toString(entity,"utf-8");
            } catch (Exception e) {
                System.out.println(e);
            }
            String idAndClass="";
            if(!html.equals(""))
                idAndClass = StringUtils.substringBetween(html, "ViewMore.php?", "&addclick=");
            String addr = downLoadPath + idAndClass;
            String fileName = idAndClass+".jpg";
            URLAndName.put(addr,fileName);
        }
        return URLAndName;
    }
    public static ArrayList<String> getURLsByID(int begin,int end) {
        ArrayList<String> urls = new ArrayList<>();
        for(int i=begin; i<end; i++) {
            String url = showPath + i +".html";
            urls.add(url);
        }
        return urls;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
