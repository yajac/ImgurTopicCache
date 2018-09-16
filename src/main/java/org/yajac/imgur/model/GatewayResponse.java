package org.yajac.imgur.model;

import java.util.HashMap;
import java.util.Map;

/**
 * POJO containing response object for API Gateway.
 */
public class GatewayResponse {

    private final String body;
    private final int statusCode;
    private Map<String, String> headers = new HashMap<>();


    /**
     * Cache Read Response
     * @param body
     * @param statusCode
     */
    public GatewayResponse(final String body, final int statusCode) {
        this.statusCode = statusCode;
        this.body = body;
        headers.put("Access-Control-Allow-Origin", "*");
    }

    public String getBody() {
        return body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
