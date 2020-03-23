package download;

import httpTools.Client;
import io.reactivex.annotations.NonNull;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import javax.swing.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class MultiThreadDownload implements Runnable {
    private HttpGet resources;
    private String path, fileName;
    MultiThreadDownload(@NonNull HttpGet resources,@NonNull String path, @NonNull String fileName) {
        this.resources = resources;
        this.path = path;
        this.fileName = fileName;
    }
    @Override
    public void run() {
        if (checkFileStorePath()) {

            CloseableHttpClient client = HttpClientBuilder.create().build();
            File outFile = new File(path + fileName);
            FileOutputStream out  = null;
            CloseableHttpResponse res = null;
            try {
                out  = new FileOutputStream(outFile);
                res = client.execute(resources);
                if(res.getCode()!=200) {
                    System.err.println(fileName+ " download failed");
                    return;
                }
                HttpEntity resData = res.getEntity();
                byte[] data = EntityUtils.toByteArray(resData);
                out.write(data);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("downloaded "+fileName);
        } else {
            System.err.println("创建目录失败");
        }
    }

    private boolean checkFileStorePath() {
        File f = new File(path);
        if (!f.exists()) {
            if (!f.mkdirs()) {
                return false;
            }
            return true;
        } else
            return true;
    }
}
