import download.DownloadHelper;
import parsers.BiAnParser;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Map<String, String> headers = new HashMap<>();
        /*headers.put("Cookie","zkhanmluserid=345597;zkhanmlrnd=3FqUdHyyVdUm1gBn3yms");*/
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64 ) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36");
        DownloadHelper test = new DownloadHelper(new BiAnParser(headers),"D://Download/test/",BiAnParser.getURLsByID(700,711));
        test.setThreadNum(8);
        test.autoDownload();
    }
}
