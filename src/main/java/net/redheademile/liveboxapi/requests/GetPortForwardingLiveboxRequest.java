package net.redheademile.liveboxapi.requests;

public class GetPortForwardingLiveboxRequest extends LiveboxRequest {
    public GetPortForwardingLiveboxRequest() {
        super("getPortForwarding",
                "Firewall",
                "origin", "webui");
    }
}
