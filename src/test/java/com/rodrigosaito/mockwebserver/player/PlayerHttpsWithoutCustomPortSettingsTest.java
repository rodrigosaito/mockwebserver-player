package com.rodrigosaito.mockwebserver.player;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class PlayerHttpsWithoutCustomPortSettingsTest extends AbstractHttpTest {

    @Rule
    public Player player = new Player();

    public PlayerHttpsWithoutCustomPortSettingsTest() {
        player.setHttps(true);
    }

    @Test
    @Play("simple_play")
    public void testWithCustomSettings() throws IOException {
        CloseableHttpResponse response = executeGet(player.getURL("/").toString());

        String body = EntityUtils.toString(response.getEntity());
        int statusCode = response.getStatusLine().getStatusCode();

        assertEquals("Simple Tape", body);
        assertEquals(200, statusCode);
    }
}
