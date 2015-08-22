package com.rodrigosaito.mockwebserver.player;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AbstractHttpTest {
    protected CloseableHttpResponse executeGet(final String url) throws IOException {
        return executeGet(url, new HashMap<String, String>());
    }

    protected CloseableHttpResponse executeGet(final String url, final Map<String, String> headers) throws IOException {
        HttpGet get = new HttpGet(url.toString());
        get.setHeaders(toHeaders(headers));

        CloseableHttpClient client = HttpClients.createDefault();

        CloseableHttpResponse response = client.execute(get);

        return response;
    }

    protected CloseableHttpResponse executePost(final String url, final String body) throws IOException {
        HttpPost post = new HttpPost(url);
        post.setEntity(new StringEntity(body));

        CloseableHttpClient client = HttpClients.createDefault();

        CloseableHttpResponse response = client.execute(post);

        return response;
    }

    private Header[] toHeaders(final Map<String, String> headers) {
        List<Header> headerList = new ArrayList<Header>();

        for (Map.Entry<String, String> h : headers.entrySet()) {
            BasicHeader header = new BasicHeader(h.getKey(), h.getValue());
            headerList.add(header);
        }

        return headerList.toArray(new Header[0]);
    }
}
