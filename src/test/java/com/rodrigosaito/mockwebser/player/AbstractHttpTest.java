package com.rodrigosaito.mockwebser.player;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class AbstractHttpTest {
    protected CloseableHttpResponse executeGet(final String url) throws IOException {
        HttpGet get = new HttpGet(url.toString());

        CloseableHttpClient client = HttpClients.createDefault();

        CloseableHttpResponse response = client.execute(get);

        return response;
    }
}
