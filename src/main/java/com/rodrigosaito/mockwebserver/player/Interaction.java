package com.rodrigosaito.mockwebserver.player;

public class Interaction {

    private Request request;
    private Response response;

    public Request getRequest() {
        return request;
    }

    public void setRequest(final Request request) {
        this.request = request;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(final Response response) {
        this.response = response;
    }
}
