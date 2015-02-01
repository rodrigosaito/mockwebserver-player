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

public class PlayerWithRequestMatchingTest extends AbstractHttpTest {

    @Rule
    public Player player = new Player();

    @Test
    @Play("play_with_request_matching")
    public void test() throws IOException {
        URL url2 = player.getURL("/test_2");
        CloseableHttpResponse response2 = executeGet(url2.toString());
        assertEquals("Test 2", EntityUtils.toString(response2.getEntity()));

        URL url1 = player.getURL("/test_1");
        CloseableHttpResponse response1 = executeGet(url1.toString());
        assertEquals("Test 1", EntityUtils.toString(response1.getEntity()));
    }

}
