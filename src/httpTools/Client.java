package httpTools;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;

public class Client {
    private CloseableHttpClient client =null;

    public CloseableHttpClient getClient() {
        return client;
    }

    public void setClient(CloseableHttpClient client) {
        this.client = client;
    }
}
