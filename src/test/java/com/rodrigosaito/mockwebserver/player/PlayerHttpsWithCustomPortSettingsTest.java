package com.rodrigosaito.mockwebserver.player;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class PlayerHttpsWithCustomPortSettingsTest extends AbstractHttpTest {

    @Rule
    public Player player = new Player();

    public PlayerHttpsWithCustomPortSettingsTest() {
        player.setPort(9443);
        player.setHttps(true);
    }

    @Test
    @Play("simple_play")
    public void testWithCustomSettings() throws IOException {
        CloseableHttpResponse response = executeGet("https://localhost:9443/");

        String body = EntityUtils.toString(response.getEntity());
        int statusCode = response.getStatusLine().getStatusCode();

        assertEquals("Simple Tape", body);
        assertEquals(200, statusCode);
    }
}
