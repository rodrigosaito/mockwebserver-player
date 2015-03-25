package com.rodrigosaito.mockwebserver.player;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerWithDelayTest extends AbstractHttpTest{

    @Rule
    public Player player = new Player();

    @Test
    @Play(value = "simple_play", delay = 2000)
    public void testPlayerWithDelay() throws IOException {
        URL url = player.getURL("/");

        Date start = new Date();
        CloseableHttpResponse response = executeGet(url.toString());

        String body = EntityUtils.toString(response.getEntity());
        int statusCode = response.getStatusLine().getStatusCode();

        long totalTime = new Date().getTime() - start.getTime();

        assertEquals("Simple Tape", body);
        assertEquals(200, statusCode);
        assertTrue(totalTime > 2000);
    }
}
