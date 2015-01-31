package com.rodrigosaito.mockwebserver.player;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Player implements MethodRule {

    private final MockWebServer server;
    private final TapeReader tapeReader;

    private Integer port;

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

    private void startServer() throws IOException {
        if (getPort() != null) {
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

    private void preparePlays(final List<String> tapeNames) {
        for (String tapeName : tapeNames) {
            Tape tape = tapeReader.read(tapeName);

            for (Interaction interaction : tape.getInteractions()) {
                Response response = interaction.getResponse();

                getServer().enqueue(
                        new MockResponse()
                                .setResponseCode(response.getStatus())
                                .setBody(response.getBody())
                );
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
                    List<String> plays = new ArrayList<String>();
                    for (Annotation annotation : method.getAnnotations()) {
                        if (annotation instanceof Play) {
                            for (String play : ((Play) annotation).value()) {
                                plays.add(play);
                            }
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
}
