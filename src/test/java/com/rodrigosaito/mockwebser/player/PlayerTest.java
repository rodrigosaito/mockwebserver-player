package com.rodrigosaito.mockwebser.player;

import com.rodrigosaito.mockwebserver.player.Play;
import com.rodrigosaito.mockwebserver.player.Player;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class PlayerTest {

    @Rule
    public Player player = new Player();

    @Test
    @Play("simple_play")
    public void testPlayer() throws IOException {
        URL url = player.getURL("/");

        CloseableHttpResponse response = executeGet(url.toString());

        String body = EntityUtils.toString(response.getEntity());
        int statusCode = response.getStatusLine().getStatusCode();

        assertEquals("Simple Tape", body);
        assertEquals(200, statusCode);
    }

    private CloseableHttpResponse executeGet(final String url) throws IOException {
        HttpGet get = new HttpGet(url.toString());

        CloseableHttpClient client = HttpClients.createDefault();

        CloseableHttpResponse response = client.execute(get);

        return response;
    }

}
