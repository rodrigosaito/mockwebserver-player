package com.rodrigosaito.mockwebserver.player;

import com.squareup.okhttp.internal.SslContextBuilder;
import com.squareup.okhttp.mockwebserver.Dispatcher;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player implements MethodRule {

    private final MockWebServer server;
    private final TapeReader tapeReader;

    private Integer port;
    private boolean https;

    public Player() {
        this.server = new MockWebServer();
        this.tapeReader = new TapeReader();
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(final Integer port) {
        this.port = port;
    }

    public boolean isHttps() {
        return https;
    }

    public void setHttps(boolean https) {
        this.https = https;
    }

    private void startServer() throws Exception {
        if (isHttps() && getPort() != null) {
            setSslSupport();
            server.play(getPort());
        } else if (isHttps()) {
            setSslSupport();
            server.play();
        } else if (getPort() != null) {
            server.play(getPort());
        } else {
            server.play();
        }
    }

    public MockWebServer getServer() {
        return server;
    }

    private void stopServer() throws IOException {
        if (getServer() != null) {
            getServer().shutdown();
        }
    }

    private void preparePlays(final List<Play> plays) {
        List<Tape> tapes = new ArrayList<Tape>();
        boolean requestMatching = false;

        for (Play play : plays) {
            for (String tapeName : play.value()) {
                Tape tape = !tapeName.isEmpty() ? tapeReader.read(tapeName) : new Tape();

                tape.setDelay(play.delay());

                requestMatching = requestMatching || tape.haRequestMatching();

                tapes.add(tape);
            }
        }

        RequestMatchingDispatcher requestMatchingDispatcher = new RequestMatchingDispatcher();

        if (requestMatching) {
            getServer().setDispatcher(requestMatchingDispatcher);
        }

        for (Tape tape : tapes) {
            for (Interaction interaction : tape.getInteractions()) {
                Response response = interaction.getResponse();

                if (requestMatching) {
                    requestMatchingDispatcher.addRequest(
                            interaction.getRequest().getUri(),
                            new MockResponse()
                                    .setResponseCode(response.getStatus())
                                    .setBody(response.getBody())
                                    .setBodyDelayTimeMs(tape.getDelay())
                    );

                } else {
                    getServer().enqueue(
                            new MockResponse()
                                    .setResponseCode(response.getStatus())
                                    .setBody(response.getBody())
                                    .setBodyDelayTimeMs(tape.getDelay())
                    );
                }
            }
        }
    }

    public URL getURL(final String path) {
        return getServer().getUrl(path);
    }

    @Override
    public Statement apply(final Statement base, final FrameworkMethod method, final Object target) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                startServer();

                try {
                    List<Play> plays = new ArrayList<Play>();
                    for (Annotation annotation : method.getAnnotations()) {
                        if (annotation instanceof Play) {
                            plays.add((Play) annotation);
                        }
                    }

                    preparePlays(plays);

                    base.evaluate();
                } finally {
                    stopServer();
                }
            }
        };
    }

    private static final class RequestMatchingDispatcher extends Dispatcher {

        private Map<String, MockResponse> requestResponses = new HashMap<String, MockResponse>();

        @Override
        public MockResponse dispatch(final RecordedRequest request) throws InterruptedException {
            return findResponse(request.getPath());
        }

        private MockResponse findResponse(final String path) {
            MockResponse response = requestResponses.get(path);

            if (response == null) {
                return new MockResponse().setResponseCode(404).setBody("No Response Found");
            }

            return response;
        }

        public void addRequest(final String path, final MockResponse response) {
            requestResponses.put(path, response);
        }
    }

    private void setSslSupport() {
        server.useHttps(SslContextBuilder.localhost().getSocketFactory(), false);
    }
}
