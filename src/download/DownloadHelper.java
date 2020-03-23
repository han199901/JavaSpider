package download;

import httpTools.Client;
import io.reactivex.annotations.NonNull;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import parsers.ParserInterface;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DownloadHelper {
    private int threadNum = 1;
    private Map<String,String> url;
    private String downloadPath;
    private Map<String,String> headers;
    public DownloadHelper(@NonNull ParserInterface parser, @NonNull String downloadPath, @NonNull ArrayList<String> urls) {
        this.url = parser.getURLAndName(urls);
        this.downloadPath = downloadPath;
        this.headers = parser.getHeaders();
    }
    public void autoDownload() {
        ExecutorService es = Executors.newFixedThreadPool(threadNum);
        CloseableHttpClient client = HttpClientBuilder.create().build();
        Header httpHead;
        /*for(Map.Entry<String, String> j : headers.entrySet())*/
        Header a;
        for(Map.Entry<String,String> entry:url.entrySet()) {
            System.out.println("ready to download " + entry.getKey());
            HttpGet get = new HttpGet(entry.getKey());
            for(Map.Entry<String, String> j : headers.entrySet())
                get.setHeader(j.getKey(),j.getValue());
            es.execute(new MultiThreadDownload(get, downloadPath, entry.getValue()));
        }
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        es.shutdown();
    }

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }

    public Map<String, String> getUrl() {
        return url;
    }

    public void setUrl(ArrayList<String> urls, ParserInterface parser) {
        this.url = parser.getURLAndName(urls);
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }
}
