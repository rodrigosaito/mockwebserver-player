package com.rodrigosaito.mockwebserver.player;

import com.squareup.okhttp.mockwebserver.Dispatcher;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RequestMatchingDispatcher extends Dispatcher {

    private final Set<ConfiguredRequest> configuredRequests = new HashSet<ConfiguredRequest>();

    @Override
    public MockResponse dispatch(final RecordedRequest request) throws InterruptedException {
        return findResponse(request);
    }

    private MockResponse findResponse(final RecordedRequest recordedRequest) {
        for (ConfiguredRequest cr : configuredRequests) {
            if (cr.match(recordedRequest)) {
                return cr.getResponse();
            }
        }

        return new MockResponse().setResponseCode(404).setBody("No Response Found");
    }

    public void addRequest(final Request request, final MockResponse response) {
        configuredRequests.add(new ConfiguredRequest(request, response));
    }

    private static final class ConfiguredRequest {
        private final Request request;
        private final MockResponse response;

        public ConfiguredRequest(final Request request, final MockResponse response) {
            this.request = request;
            this.response = response;
        }

        public Request getRequest() {
            return request;
        }

        public MockResponse getResponse() {
            return response;
        }

        public boolean match(final RecordedRequest recordedRequest) {
            boolean methodMatches = recordedRequest.getMethod().equals(getRequest().getMethod());
            boolean pathMatches = recordedRequest.getPath().equals(getRequest().getUri());
            boolean headerMatches = true;
            boolean bodyMatches = true;

            if (getRequest().hasHeaders()) {
                for (Map.Entry<String, String> headers : getRequest().getHeaders().entrySet()) {
                    if (!headers.getValue().equals(recordedRequest.getHeader(headers.getKey()))) {
                        headerMatches = false;
                        break;
                    }
                }
            }

            if (getRequest().hasBody()) {
                bodyMatches = recordedRequest.getUtf8Body().equals(getRequest().getBody());
            }

            return methodMatches && pathMatches && headerMatches && bodyMatches;
        }
    }
}