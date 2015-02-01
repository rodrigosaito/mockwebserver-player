package com.rodrigosaito.mockwebser.player;

import com.rodrigosaito.mockwebserver.player.Play;
import com.rodrigosaito.mockwebserver.player.Player;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class PlayerCustomSettingsTest extends AbstractHttpTest {

    @Rule
    public Player player = new Player();

    public PlayerCustomSettingsTest() {
        player.setPort(58880);

    }

    @Test
    @Play("simple_play")
    public void testWithCustomSettings() throws IOException {
        CloseableHttpResponse response = executeGet("http://localhost:58880/");

        String body = EntityUtils.toString(response.getEntity());
        int statusCode = response.getStatusLine().getStatusCode();

        assertEquals("Simple Tape", body);
        assertEquals(200, statusCode);
    }
}
