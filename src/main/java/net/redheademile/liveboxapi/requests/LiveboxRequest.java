package net.redheademile.liveboxapi.requests;

import com.google.gson.Gson;

import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;

public abstract class LiveboxRequest {
    private static final Gson gson = new Gson();

    private final String method;
    private final String service;
    protected final Map<String, Object> parameters = new HashMap<>();

    protected LiveboxRequest(String method, String service, Object... parameters) {
        this.method = method;
        this.service = service;

        if (parameters.length % 2 != 0)
            throw new IllegalArgumentException("Each parameter key must have a value like parameters = [key1, value1, key2, value2, ...]");

        for (int i = 0; i < parameters.length; i += 2) {
            Object key = parameters[i];
            if (!(key instanceof String keyStr)) throw new IllegalArgumentException("Key parameter must be string");

            this.parameters.put(keyStr, parameters[i + 1]);
        }
    }

    public HttpRequest.BodyPublisher asBodyPublisher() {
        return HttpRequest.BodyPublishers.ofString(gson.toJson(this));
    }
}
