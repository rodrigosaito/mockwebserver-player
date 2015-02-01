package com.rodrigosaito.mockwebser.player;

import com.rodrigosaito.mockwebserver.player.Play;
import com.rodrigosaito.mockwebserver.player.Player;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class PlayerDefaultSettingsTest extends AbstractHttpTest {

    @Rule
    public Player player = new Player();

    @Test
    @Play("simple_play")
    public void testPlayerWithDefaultSettings() throws IOException {
        URL url = player.getURL("/");

        CloseableHttpResponse response = executeGet(url.toString());

        String body = EntityUtils.toString(response.getEntity());
        int statusCode = response.getStatusLine().getStatusCode();

        assertEquals("Simple Tape", body);
        assertEquals(200, statusCode);
    }
}
