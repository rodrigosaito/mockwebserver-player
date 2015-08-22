package com.rodrigosaito.mockwebserver.player;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Request {

    private static final Set<String> METHODS_WITH_BODY = new HashSet<String>();

    static {
        METHODS_WITH_BODY.add("POST");
        METHODS_WITH_BODY.add("PUT");
        METHODS_WITH_BODY.add("DELETE");
        METHODS_WITH_BODY.add("PATCH");
    }

    private String method;
    private String uri;
    private Map<String, String> headers;
    private String body;

    public String getMethod() {
        return method;
    }

    public void setMethod(final String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(final String uri) {
        this.uri = uri;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(final Map<String, String> headers) {
        this.headers = headers;
    }

    public boolean hasHeaders() {
        return getHeaders() != null && !getHeaders().isEmpty();
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public boolean hasBody() {
        return METHODS_WITH_BODY.contains(getMethod()) && getBody() != null && !getBody().isEmpty();
    }
}
