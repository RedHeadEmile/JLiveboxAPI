package net.redheademile.liveboxapi.requests;

public class CreateContextLiveboxRequest extends LiveboxRequest {
    public CreateContextLiveboxRequest(String username, String password) {
        super("createContext",
                "sah.Device.Information",
                "applicationName", "webui",
                "username", username,
                "password", password);
    }
}
