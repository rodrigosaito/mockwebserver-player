package com.rodrigosaito.mockwebserver.player;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class PlayerWithRequestMatchingTest extends AbstractHttpTest {

    @Rule
    public Player player = new Player();

    @Test
    @Play("play_with_request_matching")
    public void testURLMatching() throws IOException {
        URL url2 = player.getURL("/test_2");
        CloseableHttpResponse response2 = executeGet(url2.toString());
        assertEquals("Test 2", EntityUtils.toString(response2.getEntity()));

        URL url1 = player.getURL("/test_1");
        CloseableHttpResponse response1 = executeGet(url1.toString());
        assertEquals("Test 1", EntityUtils.toString(response1.getEntity()));
    }

    @Test
    @Play("play_with_request_header_matching")
    public void testHeaderMatchingWhenWithHeader() throws IOException {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");

        URL url = player.getURL("/some_path");
        CloseableHttpResponse response = executeGet(url.toString(), headers);
        assertEquals("Some response", EntityUtils.toString(response.getEntity()));
    }

    @Test
    @Play("play_with_request_header_matching")
    public void testHeaderMatchingWhenWithoutHeader() throws IOException {
        URL url = player.getURL("/some_path");
        CloseableHttpResponse response = executeGet(url.toString());
        assertEquals(404, response.getStatusLine().getStatusCode());
    }

    @Test
    @Play("play_with_request_body_matching")
    public void testBodyMathingWhenBodyMatches() throws IOException {
        URL url = player.getURL("/some_path");
        CloseableHttpResponse response = executePost(url.toString(), "custom=body");
        assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Test
    @Play("play_with_request_body_matching")
    public void testBodyMathingWhenBodyIsDifferent() throws IOException {
        URL url = player.getURL("/some_path");
        CloseableHttpResponse response = executePost(url.toString(), "different=body");
        assertEquals(404, response.getStatusLine().getStatusCode());
    }
}
