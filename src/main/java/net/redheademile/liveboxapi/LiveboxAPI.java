package net.redheademile.liveboxapi;

import com.google.gson.Gson;
import net.redheademile.liveboxapi.exceptions.LiveboxException;
import net.redheademile.liveboxapi.requests.*;
import net.redheademile.liveboxapi.responses.CreateContextLiveboxResponse;
import net.redheademile.liveboxapi.responses.PortForwardingLiveboxResponse;

import java.io.IOException;
import java.net.CookieManager;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LiveboxAPI {
    private final Gson gson = new Gson();

    private final HttpClient httpClient;

    private final URI endpoint;
    private final String endpointBasic;
    private final String endpointWithSlash;

    private final String user;
    private final String password;

    private String authToken;

    public  LiveboxAPI(String password) throws URISyntaxException {
        this("admin", password);
    }

    public LiveboxAPI(String user, String password) throws URISyntaxException {
        this("http://192.168.1.1", user, password);
    }

    public LiveboxAPI(String uiEndpoint, String user, String password) throws URISyntaxException {
        this.httpClient = HttpClient.newBuilder().cookieHandler(new CookieManager()).build();
        this.user = user;
        this.password = password;

        if (uiEndpoint.endsWith("/")) uiEndpoint = uiEndpoint.substring(0, uiEndpoint.length() - 1);
        this.endpoint = new URI(uiEndpoint + "/ws");
        this.endpointBasic = uiEndpoint;
        this.endpointWithSlash = uiEndpoint + '/';
    }

    protected <T> T executeRequest(LiveboxRequest liveboxRequest, Class<T> responseType) throws LiveboxException {
         HttpRequest httpRequest = HttpRequest.newBuilder()
                 .header("Accept", "*/*")
                 .header("Authorization", this.authToken != null ? "X-Sah " + this.authToken : "X-Sah-Login")
                 .header("Content-Type", "application/x-sah-ws-4-call+json")
                 .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/117.0")
                 .header("Origin", this.endpointBasic)
                 .header("Referer", this.endpointWithSlash)
                 .uri(this.endpoint)
                 .POST(liveboxRequest.asBodyPublisher())
                 .build();

         try
         {
             HttpResponse<String> httpResponse = this.httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
             if (httpResponse.statusCode() < 200 || httpResponse.statusCode() >= 300)
                 throw new LiveboxException("Invalid response code");

             if (responseType == null)
                 return null;
             return this.gson.fromJson(httpResponse.body(), responseType);
         }
         catch (IOException | InterruptedException e) {
             throw new LiveboxException(e);
         }
    }

    protected void ensureAuthentication() throws LiveboxException {
        if (this.authToken != null) return;

        CreateContextLiveboxResponse response = this.executeRequest(
                new CreateContextLiveboxRequest(this.user, this.password),
                CreateContextLiveboxResponse.class
        );

        this.authToken = response.data.contextID;
    }

    public PortForwardingLiveboxResponse getPortForwarding() throws LiveboxException {
        this.ensureAuthentication();

        PortForwardingLiveboxResponse response = this.executeRequest(
                new GetPortForwardingLiveboxRequest(),
                PortForwardingLiveboxResponse.class
        );

        return response;
    }

    public void setPortForwarding(SetPortForwardingLiveboxRequest portForwardingLiveboxRequest) throws LiveboxException {
        this.ensureAuthentication();

        this.executeRequest(portForwardingLiveboxRequest, null);
    }

    public void deletePortForwarding(String id, String destinationIPAddress, String origin) throws LiveboxException {
        this.ensureAuthentication();

        this.executeRequest(new DeletePortForwardingLiveboxRequest(id, destinationIPAddress, origin), null);
    }
}
